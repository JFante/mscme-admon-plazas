package mx.gob.imss.mscme.admonplazas.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import mx.gob.imss.mscme.admonplazas.models.request.PlazaRequest;
import mx.gob.imss.mscme.admonplazas.models.request.PlazasFiltroRequest;
import mx.gob.imss.mscme.admonplazas.models.response.DetallePlazaDTO;
import mx.gob.imss.mscme.admonplazas.models.response.PlazaValidacionResponse;
import mx.gob.imss.mscme.admonplazas.models.response.RespuestaGenerica;

public interface AdministracionPlazasService {

	RespuestaGenerica<Page<DetallePlazaDTO>> busquedaPlazasFiltro(PlazasFiltroRequest filtro, Pageable pageable);

	RespuestaGenerica<DetallePlazaDTO> buscarDetallePlaza(Long idPlaza);

	RespuestaGenerica<DetallePlazaDTO> crearPlaza(PlazaRequest plazaRequest, String token);

	RespuestaGenerica<DetallePlazaDTO> actualizarPlaza(PlazaRequest plazaRequest, String token);

	RespuestaGenerica<Void> eliminarPlaza(Long idPlaza, String token);

	RespuestaGenerica<Void> actualizarEstatusPlaza(Long idPlaza, Long idEstatus, String desObservaciones, String token);

	RespuestaGenerica<PlazaValidacionResponse> validarPlazasOcupadas(Long idConvocatoria);

}
