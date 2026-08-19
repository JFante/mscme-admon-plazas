package mx.gob.imss.mscme.admonplazas.services.Impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mx.gob.imss.mscme.admonplazas.enums.EstatusCargaEnum;
import mx.gob.imss.mscme.admonplazas.models.entities.ControlCargaPlaza;
import mx.gob.imss.mscme.admonplazas.models.entities.Convocatoria;
import mx.gob.imss.mscme.admonplazas.models.entities.EstatusCarga;
import mx.gob.imss.mscme.admonplazas.models.entities.PlazaLayout;
import mx.gob.imss.mscme.admonplazas.models.entities.Usuario;
import mx.gob.imss.mscme.admonplazas.repository.ControlCargaPlazaRepository;
import mx.gob.imss.mscme.admonplazas.repository.EstatusCargaRepository;
import mx.gob.imss.mscme.admonplazas.services.ControlCargaPlazaService;
import mx.gob.imss.mscme.admonplazas.utils.Constantes;
import mx.gob.imss.mscme.admonplazas.utils.Mensajes;

@Service
@Log4j2
@RequiredArgsConstructor
public class ControlCargaPlazaServiceImpl implements ControlCargaPlazaService {

    private final ControlCargaPlazaRepository controlCargaPlazaRepository;
    private final EstatusCargaRepository estatusCargaRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ControlCargaPlaza guardarInicioControlCarga(Convocatoria convocatoria, MultipartFile archivo,
            LocalDateTime fechaHoraInicioProceso, Usuario usuarioAdmon) {
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

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void guardarFinControlCarga(ControlCargaPlaza controlCarga, EstatusCargaEnum estatusFinal,
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

}
