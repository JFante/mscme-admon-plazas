package mx.gob.imss.mscme.admonplazas.services.Impl;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mx.gob.imss.mscme.admonplazas.mappers.PlazaLayoutMapper;
import mx.gob.imss.mscme.admonplazas.models.entities.PlazaLayout;
import mx.gob.imss.mscme.admonplazas.models.entities.Usuario;
import mx.gob.imss.mscme.admonplazas.models.response.DetallePlazaDTO;
import mx.gob.imss.mscme.admonplazas.models.response.RespuestaGenerica;
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

	private final PlazaLayoutRepository plazaLayoutRepository;
	private final PlazaLayoutSpecification plazaLayoutSpecification;
	private final PlazaLayoutMapper plazaLayoutMapper;
	private final UsuarioService usuarioService;

	@Override
	public RespuestaGenerica<Page<DetallePlazaDTO>> busquedaPlazasFiltro(Long cveOoad, Integer numPlaza, Pageable pageable) {
		Page<DetallePlazaDTO> resultado = plazaLayoutRepository
				.findAll(plazaLayoutSpecification.generarSpecificationCveOoadNumPlaza(cveOoad, numPlaza), pageable)
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

}
