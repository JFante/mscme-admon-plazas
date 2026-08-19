package mx.gob.imss.mscme.admonplazas.services.Impl;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mx.gob.imss.mscme.admonplazas.enums.EstatusCargaEnum;
import mx.gob.imss.mscme.admonplazas.enums.EstatusPlazaEnum;
import mx.gob.imss.mscme.admonplazas.enums.ModuloEnum;
import mx.gob.imss.mscme.admonplazas.enums.TipoMovimientoMovimientoEnum;
import mx.gob.imss.mscme.admonplazas.mappers.ControlCargaPlazaMapper;
import mx.gob.imss.mscme.admonplazas.mappers.PlazaLayoutMapper;
import mx.gob.imss.mscme.admonplazas.models.entities.ControlCargaPlaza;
import mx.gob.imss.mscme.admonplazas.models.entities.Convocatoria;
import mx.gob.imss.mscme.admonplazas.models.entities.EstatusCarga;
import mx.gob.imss.mscme.admonplazas.models.entities.EstatusPlaza;
import mx.gob.imss.mscme.admonplazas.models.entities.PlazaLayout;
import mx.gob.imss.mscme.admonplazas.models.entities.Usuario;
import mx.gob.imss.mscme.admonplazas.models.response.ControlCargaPlazaDTO;
import mx.gob.imss.mscme.admonplazas.models.response.PlazaLayoutCargaDTO;
import mx.gob.imss.mscme.admonplazas.models.response.RespuestaGenerica;
import mx.gob.imss.mscme.admonplazas.repository.ControlCargaPlazaRepository;
import mx.gob.imss.mscme.admonplazas.repository.ConvocatoriaRepository;
import mx.gob.imss.mscme.admonplazas.repository.EstatusCargaRepository;
import mx.gob.imss.mscme.admonplazas.repository.PlazaLayoutRepository;
import mx.gob.imss.mscme.admonplazas.services.BitacoraService;
import mx.gob.imss.mscme.admonplazas.services.CargaLayoutPlazaService;
import mx.gob.imss.mscme.admonplazas.services.PlazaExcelUtilService;
import mx.gob.imss.mscme.admonplazas.services.UsuarioService;
import mx.gob.imss.mscme.admonplazas.utils.ColumnasLayoutPlaza;
import mx.gob.imss.mscme.admonplazas.utils.Constantes;
import mx.gob.imss.mscme.admonplazas.utils.Mensajes;

@Service
@Log4j2
@RequiredArgsConstructor
public class CargaLayoutPlazaServiceImpl implements CargaLayoutPlazaService {

    private final ControlCargaPlazaRepository controlCargaPlazaRepository;
    private final EstatusCargaRepository estatusCargaRepository;
    private final ConvocatoriaRepository convocatoriaRepository;
    private final PlazaLayoutMapper plazaLayoutMapper;
    private final ControlCargaPlazaMapper controlCargaPlazaMapper;
    private final PlazaExcelUtilService plazaExcelUtilService;
    private final UsuarioService usuarioService;
    private final PlazaLayoutRepository plazaLayoutRepository;
    private final BitacoraService bitacoraService;

    @Override
    public RespuestaGenerica<List<PlazaLayoutCargaDTO>> cargarLayoutPlaza(Long idConvocatoria, MultipartFile archivo,String token) {
        log.info("cargarLayoutPlaza {}", idConvocatoria);
        var fechaHoraInicioProceso = LocalDateTime.now();
        var convocatoriaActiva = convocatoriaRepository.findById(idConvocatoria).orElseThrow(() -> new IllegalStateException(Mensajes.MSG_CONVOCATORIA_NO_ENCONTRADA.getMensaje()));
        var usuarioAdministrador = usuarioService.obtenerUsuarioToken(token);

        if (archivo == null || archivo.isEmpty()) {
            throw new IllegalArgumentException(Mensajes.MSG_ARCHIVO_OBLIGATORIO.getMensaje());
        }

        String nombreArchivo = StringUtils.getFilename(archivo.getOriginalFilename());
        String nombreArchivoLower = nombreArchivo == null ? "" : nombreArchivo.toLowerCase(Locale.ROOT);
        if (!nombreArchivoLower.endsWith(Constantes.EXTENSION_XLSX) && !nombreArchivoLower.endsWith(Constantes.EXTENSION_XLS)) {
            throw new IllegalArgumentException(Mensajes.MSG_ARCHIVO_FORMATO_INVALIDO.getMensaje());
        }

        if (controlCargaPlazaRepository.existeCargaEnProceso(idConvocatoria, Constantes.ESTADO_ACTIVO)) {
            throw new IllegalStateException(Mensajes.MSG_CARGA_EN_PROCESO.getMensaje());
        }
        
        ControlCargaPlaza controlCarga = this.guardarInicioControlCarga(convocatoriaActiva, archivo,fechaHoraInicioProceso, usuarioAdministrador);
        List<PlazaLayout> plazasNuevas;
        try {
        	plazasNuevas = this.generarPlazasEntidades(archivo, idConvocatoria, usuarioAdministrador);
        	plazasNuevas = plazaLayoutRepository.saveAll(plazasNuevas);
        } catch (RuntimeException ex) {
            this.guardarFinControlCarga(controlCarga,EstatusCargaEnum.INTERRUMPIDO, List.of(), ex.getMessage(), usuarioAdministrador);
            throw ex;
        }

        List<PlazaLayoutCargaDTO> respuesta = plazaLayoutMapper.toPlazaLayoutCargaDTOList(plazasNuevas);
        //se guarda el fin del proceso
        this.guardarFinControlCarga(controlCarga, EstatusCargaEnum.FINALIZADO, plazasNuevas,Mensajes.MSG_ARCHIVO_RECIBIDO.getMensaje(), usuarioAdministrador);

        // guardado de bitacora general
		this.bitacoraService.guardarBitacora(usuarioAdministrador, "Carga de layout de plaza",TipoMovimientoMovimientoEnum.CREACION.getId(), ModuloEnum.ADMINISTRACION_DE_PLAZAS.getId());

        return new RespuestaGenerica<>(true, Mensajes.MSG_ARCHIVO_RECIBIDO.getMensaje(), respuesta);
    }

    @Override
    public RespuestaGenerica<ControlCargaPlazaDTO> obtenerUltimaCargaPorConvocatoria(Long idConvocatoria) {
        log.info("obtenerUltimaCargaPorConvocatoria {}", idConvocatoria);
        List<ControlCargaPlaza> cargas = controlCargaPlazaRepository.findByIdConvocatoriaOrderByFechaDesc(idConvocatoria);

        ControlCargaPlaza ultimaCarga = null;
        if (!CollectionUtils.isEmpty(cargas)) {
            ultimaCarga = cargas.getFirst();
        }

        ControlCargaPlazaDTO respuesta = controlCargaPlazaMapper.toControlCargaPlazaDTO(ultimaCarga);
        return new RespuestaGenerica<>(true, Mensajes.MSG_EXITO.getMensaje(), respuesta);
    }

    /***
     *
     * @param convocatoria
     * @param archivo
     * @param fechaHoraInicioProceso
     * @param usuarioAdmon
     * @return
     */
    private ControlCargaPlaza guardarInicioControlCarga(Convocatoria convocatoria, MultipartFile archivo, LocalDateTime fechaHoraInicioProceso, Usuario usuarioAdmon) {
        EstatusCarga estatusCarga = obtenerEstatusCarga(EstatusCargaEnum.EN_PROCESO);
        ControlCargaPlaza controlCarga = new ControlCargaPlaza();
        controlCarga.setIdConvocatoria(convocatoria);
        controlCarga.setIdEstatusCarga(estatusCarga);
        controlCarga.setNomArchivo(StringUtils.getFilename(archivo.getOriginalFilename()));
        controlCarga.setStpInicioCarga(fechaHoraInicioProceso);
        controlCarga.setIndActivo(Constantes.ESTADO_ACTIVO);
        controlCarga.setIdUsuarioAlta(usuarioAdmon.getIdUsuario());
        controlCarga.setStpAltaRegistro(fechaHoraInicioProceso);
        return controlCargaPlazaRepository.saveAndFlush(controlCarga);
    }

    /***
     * 
     * @param controlCarga
     * @param estatusFinal
     * @param plazas
     * @param mensajeResultado
     * @param usuarioAdmon
     */
    private void guardarFinControlCarga(ControlCargaPlaza controlCarga, EstatusCargaEnum estatusFinal,
            List<PlazaLayout> plazas, String mensajeResultado, Usuario usuarioAdmon) {
        long numPlazasConCredito = plazas.stream()
                .filter(plaza -> Constantes.ESTADO_ACTIVO_INTEGER.equals(plaza.getIndAccesoCredito()))
                .count();

        LocalDateTime fechaHoraFinProceso = LocalDateTime.now();
        controlCarga.setIdEstatusCarga(obtenerEstatusCarga(estatusFinal));
        controlCarga.setNumTotalRegistros((long) plazas.size());
        controlCarga.setNumRegistrosValidos((long) plazas.size());
        controlCarga.setNumRegistrosRechazados(0L);
        controlCarga.setNumPlazasOfertadas((long) plazas.size());
        controlCarga.setNumPlazasConCredito(numPlazasConCredito);
        controlCarga.setStpFinCarga(fechaHoraFinProceso);
        controlCarga.setRefMensajeResultado(mensajeResultado);
        controlCarga.setIdUsuarioModifica(usuarioAdmon.getIdUsuario());
        controlCarga.setStpModificaRegistro(fechaHoraFinProceso);
        controlCargaPlazaRepository.saveAndFlush(controlCarga);
    }

    private EstatusCarga obtenerEstatusCarga(EstatusCargaEnum estatusCargaEnum) {
        return estatusCargaRepository.findById(estatusCargaEnum.getId())
                .orElseThrow(() -> new IllegalStateException(Mensajes.MSG_ESTATUS_CARGA_NO_ENCONTRADO.getMensaje()));
    }

    /***
     *
     * @param archivo
     * @param idConvocatoria
     * @param usuarioAdmon
     * @return
     */
    private List<PlazaLayout> generarPlazasEntidades(MultipartFile archivo, Long idConvocatoria,
            Usuario usuarioAdmon) {
        try (InputStream inputStream = archivo.getInputStream();
                Workbook workbook = WorkbookFactory.create(inputStream)) {

            Sheet hoja = workbook.getSheetAt(0);
            Row filaEncabezado = this.encontrarFilaEncabezado(hoja);

            Map<Integer, String> encabezados = new HashMap<>();
            for (Cell celda : filaEncabezado) {
                String valor = plazaExcelUtilService.obtenerValorTexto(celda);
                int columnIndex = celda.getColumnIndex();
                if (StringUtils.hasText(valor) && columnIndex < ColumnasLayoutPlaza.ORDEN_COLUMNAS.length) {
                    encabezados.put(columnIndex, ColumnasLayoutPlaza.ORDEN_COLUMNAS[columnIndex]);
                }
            }

            List<PlazaLayout> plazas = new ArrayList<>();
            for (int numFila = filaEncabezado.getRowNum() + 1; numFila <= hoja.getLastRowNum(); numFila++) {
                Row fila = hoja.getRow(numFila);
                if (fila == null || plazaExcelUtilService.esFilaVacia(fila)) {
                    continue;
                }

                PlazaLayout plaza = new PlazaLayout();
                plaza.setIdConvocatoria(idConvocatoria);
                for (Cell celda : fila) {
                    String encabezado = encabezados.get(celda.getColumnIndex());
                    if (encabezado != null) {
                        plazaExcelUtilService.asignarValor(plaza, encabezado, celda);
                    }
                }

                EstatusPlaza estatusPlaza = new EstatusPlaza();
                estatusPlaza.setIdEstatusPlaza(EstatusPlazaEnum.ETIQUETADA.getId());
                plaza.setEstatusPlaza(estatusPlaza);

                plaza.setIndActivo(Constantes.ESTADO_ACTIVO);
                plaza.setIdUsuarioAlta(usuarioAdmon.getIdUsuario());
                plaza.setStpAltaRegistro(LocalDateTime.now());
                plaza.setOrigenPlaza("LAYOUT");

                plazas.add(plaza);
            }

            log.info("Se leyeron {} filas del archivo {}", plazas.size(), archivo.getOriginalFilename());
            return plazas;
        } catch (IOException ex) {
            log.error("Error al leer el archivo Excel {}", ex.getMessage(), ex);
            throw new IllegalArgumentException("No fue posible leer el archivo Excel proporcionado.");
        }
    }

    /***
     * Busca la fila que contiene los encabezados técnicos del layout (identificada por la
     * columna NUM_PLAZA), ya que las plantillas pueden incluir filas previas de descripción o
     * separación antes de dicha fila.
     *
     * @param hoja
     * @return
     */
    private Row encontrarFilaEncabezado(Sheet hoja) {
        for (int numFila = hoja.getFirstRowNum(); numFila <= hoja.getLastRowNum(); numFila++) {
            Row fila = hoja.getRow(numFila);
            if (fila == null) {
                continue;
            }
            for (Cell celda : fila) {
                String valor = plazaExcelUtilService.obtenerValorTexto(celda);
                if (valor != null && ColumnasLayoutPlaza.NUM_PLAZA.equalsIgnoreCase(valor.trim())) {
                    return fila;
                }
            }
        }
        throw new IllegalArgumentException(Mensajes.MSG_ARCHIVO_FORMATO_INVALIDO.getMensaje());
    }

}
