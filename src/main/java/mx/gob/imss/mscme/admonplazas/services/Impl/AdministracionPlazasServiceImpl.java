package mx.gob.imss.mscme.admonplazas.services.Impl;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mx.gob.imss.mscme.admonplazas.enums.EstatusPlazaEnum;
import mx.gob.imss.mscme.admonplazas.mappers.PlazaLayoutMapper;
import mx.gob.imss.mscme.admonplazas.models.entities.BitacoraAdmonPlaza;
import mx.gob.imss.mscme.admonplazas.models.request.PlazaRequest;
import mx.gob.imss.mscme.admonplazas.models.entities.EstatusPlaza;
import mx.gob.imss.mscme.admonplazas.models.entities.PlazaLayout;
import mx.gob.imss.mscme.admonplazas.models.entities.Usuario;
import mx.gob.imss.mscme.admonplazas.models.response.DetallePlazaDTO;
import mx.gob.imss.mscme.admonplazas.models.response.PlazaValidacionResponse;
import mx.gob.imss.mscme.admonplazas.models.response.RespuestaGenerica;
import mx.gob.imss.mscme.admonplazas.repository.BitacoraAdmonPlazaRepository;
import mx.gob.imss.mscme.admonplazas.repository.EstatusPlazaRepository;
import mx.gob.imss.mscme.admonplazas.repository.PlazaLayoutRepository;
import mx.gob.imss.mscme.admonplazas.repository.specification.PlazaLayoutSpecification;
import mx.gob.imss.mscme.admonplazas.services.AdministracionPlazasService;
import mx.gob.imss.mscme.admonplazas.services.UsuarioService;
import mx.gob.imss.mscme.admonplazas.utils.Constantes;
import mx.gob.imss.mscme.admonplazas.utils.Mensajes;

@Service
@Log4j2
@RequiredArgsConstructor
public class AdministracionPlazasServiceImpl implements AdministracionPlazasService {

	private static final Long ID_MOVIMIENTO_CAMBIO_ESTATUS = 5L;

	private final PlazaLayoutRepository plazaLayoutRepository;
	private final EstatusPlazaRepository estatusPlazaRepository;
	private final PlazaLayoutSpecification plazaLayoutSpecification;
	private final PlazaLayoutMapper plazaLayoutMapper;
	private final UsuarioService usuarioService;
	private final BitacoraAdmonPlazaRepository bitacoraAdmonPlazaRepository;

	@Override
	public RespuestaGenerica<Page<DetallePlazaDTO>> busquedaPlazasFiltro(Long cveOoad, Integer numPlaza, Pageable pageable) {
		return busquedaPlazasFiltro(cveOoad, numPlaza, null, pageable);
	}

	@Override
	public RespuestaGenerica<Page<DetallePlazaDTO>> busquedaPlazasFiltro(Long cveOoad, Integer numPlaza,
			String origenPlaza, Pageable pageable) {
		Page<DetallePlazaDTO> resultado = plazaLayoutRepository
				.findAll(plazaLayoutSpecification.generarSpecificationCveOoadNumPlazaOrigen(cveOoad, numPlaza, origenPlaza),
						pageable)
				.map(plazaLayoutMapper::toDetallePlazaDTO);
		return new RespuestaGenerica<>(true, Mensajes.MSG_EXITO.getMensaje(), resultado);
	}

	@Override
	public RespuestaGenerica<DetallePlazaDTO> buscarDetallePlaza(Long idPlaza) {
		DetallePlazaDTO detalle = plazaLayoutRepository.findById(idPlaza)
				.map(plazaLayoutMapper::toDetallePlazaDTO)
				.orElseThrow(() -> new IllegalStateException("No se encontró la plaza con id " + idPlaza));
		return new RespuestaGenerica<>(true, Mensajes.MSG_EXITO.getMensaje(), detalle);
	}

	@Override
	public RespuestaGenerica<DetallePlazaDTO> crearPlaza(PlazaRequest plazaRequest, String token) {
		validarDatosMinimos(plazaRequest);
		validarEstatusAlta(plazaRequest.getIdEstatusPlaza());

		if (plazaLayoutRepository.existsByIdConvocatoriaAndCveOoadAndNumPlazaAndIndActivo(
				plazaRequest.getIdConvocatoria(), plazaRequest.getCveOoad(), plazaRequest.getNumPlaza(),
				Constantes.ESTADO_ACTIVO)) {
			throw new IllegalArgumentException("La plaza ya existe, por favor verifica tu información.");
		}

		Usuario usuarioAdmon = usuarioService.obtenerUsuarioToken(token);
		PlazaLayout plazaLayout = new PlazaLayout();
		aplicarDatosPlaza(plazaLayout, plazaRequest);
		plazaLayout.setOrigenPlaza(plazaRequest.getOrigenPlaza() != null && !plazaRequest.getOrigenPlaza().isBlank()
				? plazaRequest.getOrigenPlaza().trim().toUpperCase()
				: "MANUAL");
		plazaLayout.setIndActivo(Constantes.ESTADO_ACTIVO);
		plazaLayout.setIdUsuarioAlta(usuarioAdmon.getIdUsuario());
		plazaLayout.setStpAltaRegistro(LocalDateTime.now());

		PlazaLayout guardada = plazaLayoutRepository.save(plazaLayout);
		return new RespuestaGenerica<>(true, "Plaza registrada con éxito.", plazaLayoutMapper.toDetallePlazaDTO(guardada));
	}

	@Override
	public RespuestaGenerica<DetallePlazaDTO> actualizarPlaza(Long idPlaza, PlazaRequest plazaRequest, String token) {
		PlazaLayout plazaLayout = plazaLayoutRepository.findById(idPlaza)
				.orElseThrow(() -> new IllegalStateException("No se encontró la plaza con id " + idPlaza));

		if (plazaRequest.getIdEstatusPlaza() != null) {
			validarEstatusAlta(plazaRequest.getIdEstatusPlaza());
		}

		Usuario usuarioAdmon = usuarioService.obtenerUsuarioToken(token);
		aplicarDatosPlaza(plazaLayout, plazaRequest);
		plazaLayout.setIdUsuarioModifica(usuarioAdmon.getIdUsuario());
		plazaLayout.setStpModificaRegistro(LocalDateTime.now());

		PlazaLayout actualizada = plazaLayoutRepository.save(plazaLayout);
		return new RespuestaGenerica<>(true, Mensajes.MSG_ACTUALIZADO.getMensaje(),
				plazaLayoutMapper.toDetallePlazaDTO(actualizada));
	}

	@Override
	public RespuestaGenerica<Void> eliminarPlaza(Long idPlaza, String token) {
		PlazaLayout plazaLayout = plazaLayoutRepository.findById(idPlaza)
				.orElseThrow(() -> new IllegalStateException("No se encontró la plaza con id " + idPlaza));

		Usuario usuarioAdmon = usuarioService.obtenerUsuarioToken(token);

		plazaLayout.setIndActivo(Constantes.ESTADO_NO_ACTIVO);
		plazaLayout.setIdUsuarioBaja(usuarioAdmon.getIdUsuario());
		plazaLayout.setStpBajaRegistro(LocalDateTime.now());
		plazaLayoutRepository.save(plazaLayout);

		return new RespuestaGenerica<>(true, Mensajes.MSG_ELIMINADO.getMensaje(), null);
	}

	@Override
	@Transactional
	public RespuestaGenerica<Void> actualizarEstatusPlaza(Long idPlaza, Long idEstatus, String desObservaciones, String token) {
		validarObservacionesCambioEstatus(desObservaciones);

		PlazaLayout plazaLayout = plazaLayoutRepository.findById(idPlaza)
				.orElseThrow(() -> new IllegalStateException("No se encontró la plaza con id " + idPlaza));
		Long idEstatusAnterior = plazaLayout.getEstatusPlaza() != null
				? plazaLayout.getEstatusPlaza().getIdEstatusPlaza()
				: null;

		EstatusPlaza estatusPlaza = estatusPlazaRepository.findById(idEstatus)
				.orElseThrow(() -> new IllegalStateException("No se encontró el estatus con id " + idEstatus));
		Long idUsuario = null;
		LocalDateTime ahora = LocalDateTime.now();

		if (token != null && !token.isBlank()) {
			Usuario usuarioAdmon = usuarioService.obtenerUsuarioToken(token);
			idUsuario = usuarioAdmon.getIdUsuario();
		}

		plazaLayout.setEstatusPlaza(estatusPlaza);
		plazaLayout.setDesObservaciones(desObservaciones.trim());
		if (idUsuario != null) {
			plazaLayout.setIdUsuarioModifica(idUsuario);
		}
		plazaLayout.setStpModificaRegistro(ahora);

		PlazaLayout actualizada = plazaLayoutRepository.save(plazaLayout);
		registrarBitacoraCambioEstatus(actualizada, idEstatusAnterior, idEstatus, desObservaciones.trim(), idUsuario, ahora);

		return new RespuestaGenerica<>(true, Mensajes.MSG_ACTUALIZADO.getMensaje(), null);
	}

	@Override
	public RespuestaGenerica<PlazaValidacionResponse> validarPlazasOcupadas(Long idConvocatoria) {
		if (idConvocatoria == null) {
			throw new IllegalArgumentException("El idConvocatoria es obligatorio.");
		}

		long totalOcupadas = plazaLayoutRepository.countByIdConvocatoriaAndEstatusPlaza_IdEstatusPlazaAndIndActivo(
				idConvocatoria, EstatusPlazaEnum.OCUPADA.getId(), Constantes.ESTADO_ACTIVO);
		boolean existenOcupadas = totalOcupadas > 0;
		PlazaValidacionResponse response = new PlazaValidacionResponse(!existenOcupadas, existenOcupadas, totalOcupadas);

		return new RespuestaGenerica<>(true, "Validación realizada correctamente.", response);
	}

	private void validarDatosMinimos(PlazaRequest plazaRequest) {
		if (plazaRequest == null) {
			throw new IllegalArgumentException("La información de la plaza es obligatoria.");
		}
		if (plazaRequest.getIdConvocatoria() == null || plazaRequest.getCveOoad() == null
				|| plazaRequest.getNumPlaza() == null || plazaRequest.getIdEstatusPlaza() == null) {
			throw new IllegalArgumentException("idConvocatoria, cveOoad, numPlaza e idEstatusPlaza son obligatorios.");
		}
	}

	private void validarEstatusAlta(Long idEstatusPlaza) {
		if (!EstatusPlazaEnum.VACANTE.getId().equals(idEstatusPlaza)
				&& !EstatusPlazaEnum.ETIQUETADA.getId().equals(idEstatusPlaza)) {
			throw new IllegalArgumentException("Solo se permite registrar o editar plazas con estatus Vacante o Etiquetada.");
		}
	}

	private void validarObservacionesCambioEstatus(String desObservaciones) {
		if (desObservaciones == null || desObservaciones.isBlank()) {
			throw new IllegalArgumentException("Las observaciones son obligatorias para cambiar el estatus de la plaza.");
		}
		if (desObservaciones.trim().length() > 500) {
			throw new IllegalArgumentException("Las observaciones no deben exceder 500 caracteres.");
		}
	}

	private void registrarBitacoraCambioEstatus(PlazaLayout plazaLayout, Long idEstatusAnterior, Long idEstatusNuevo,
			String desObservaciones, Long idUsuario, LocalDateTime fechaMovimiento) {
		BitacoraAdmonPlaza bitacora = new BitacoraAdmonPlaza();
		bitacora.setIdPlazaLayout(plazaLayout.getIdPlaza());
		bitacora.setIdTipoMovimientoBitacora(ID_MOVIMIENTO_CAMBIO_ESTATUS);
		bitacora.setIdEstatusAnterior(idEstatusAnterior);
		bitacora.setIdEstatusNuevo(idEstatusNuevo);
		bitacora.setDesObservaciones(desObservaciones);
		bitacora.setDesValorAnterior("ID_ESTATUS_PLAZA=" + idEstatusAnterior);
		bitacora.setDesValorNuevo("ID_ESTATUS_PLAZA=" + idEstatusNuevo);
		bitacora.setIndActivo(Constantes.ESTADO_ACTIVO);
		bitacora.setIdUsuarioAlta(idUsuario);
		bitacora.setStpAltaRegistro(fechaMovimiento);
		bitacoraAdmonPlazaRepository.save(bitacora);
	}

	private void aplicarDatosPlaza(PlazaLayout plazaLayout, PlazaRequest request) {
		plazaLayout.setNumPlaza(request.getNumPlaza());
		plazaLayout.setCveOoad(request.getCveOoad());
		plazaLayout.setDescOoad(request.getDescOoad());
		plazaLayout.setCveZona(request.getCveZona());
		plazaLayout.setDescZona(request.getDescZona());
		plazaLayout.setClasificacion(request.getClasificacion());
		plazaLayout.setCveUnidad(request.getCveUnidad());
		plazaLayout.setDescUnidad(request.getDescUnidad());
		plazaLayout.setCveDepartamento(request.getCveDepartamento());
		plazaLayout.setDescDepartamento(request.getDescDepartamento());
		plazaLayout.setCvePuesto(request.getCvePuesto());
		plazaLayout.setDescPuesto(request.getDescPuesto());
		plazaLayout.setCveCategoria(request.getCveCategoria());
		plazaLayout.setDescCategoria(request.getDescCategoria());
		plazaLayout.setCveAreaResponsabilidad(request.getCveAreaResponsabilidad());
		plazaLayout.setDescAreaResponsabilidad(request.getDescAreaResponsabilidad());
		plazaLayout.setCveTurno(request.getCveTurno());
		plazaLayout.setDescTurno(request.getDescTurno());
		plazaLayout.setCveHorario(request.getCveHorario());
		plazaLayout.setDescHorario(request.getDescHorario());
		plazaLayout.setCveTipoPlaza(request.getCveTipoPlaza());
		plazaLayout.setDescTipoPlaza(request.getDescTipoPlaza());
		plazaLayout.setCveMarcaOcupacion(request.getCveMarcaOcupacion());
		plazaLayout.setDescMarcaOcupacion(request.getDescMarcaOcupacion());
		plazaLayout.setDesRegimen(request.getDesRegimen());
		plazaLayout.setRefDireccionUnidad(request.getRefDireccionUnidad());
		plazaLayout.setIndHospitalNuevo(request.getIndHospitalNuevo());
		plazaLayout.setRefSueldoMensualBruto(request.getRefSueldoMensualBruto());
		plazaLayout.setRefSueldoMensualNeto(request.getRefSueldoMensualNeto());
		plazaLayout.setIndAccesoCredito(request.getIndAccesoCredito());
		plazaLayout.setRefCredHipotecarioImporte(request.getRefCredHipotecarioImporte());
		plazaLayout.setRefCredAutomotrizImporte(request.getRefCredAutomotrizImporte());
		plazaLayout.setRefCredHipotecarioQuincenal(request.getRefCredHipotecarioQuincenal());
		plazaLayout.setRefCredAutomotrizQuincenal(request.getRefCredAutomotrizQuincenal());
		plazaLayout.setRefBonoDificilCobertura(request.getRefBonoDificilCobertura());
		plazaLayout.setRefAltoCostoVida(request.getRefAltoCostoVida());
		plazaLayout.setIdConvocatoria(request.getIdConvocatoria());
		plazaLayout.setDesObservaciones(request.getDesObservaciones());

		if (request.getOrigenPlaza() != null && !request.getOrigenPlaza().isBlank()) {
			plazaLayout.setOrigenPlaza(request.getOrigenPlaza().trim().toUpperCase());
		}
		if (request.getIdEstatusPlaza() != null) {
			EstatusPlaza estatusPlaza = estatusPlazaRepository.findById(request.getIdEstatusPlaza())
					.orElseThrow(() -> new IllegalStateException(
							"No se encontró el estatus con id " + request.getIdEstatusPlaza()));
			plazaLayout.setEstatusPlaza(estatusPlaza);
		}
	}

}
