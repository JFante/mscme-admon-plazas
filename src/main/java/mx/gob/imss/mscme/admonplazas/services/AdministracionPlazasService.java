package mx.gob.imss.mscme.admonplazas.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import mx.gob.imss.mscme.admonplazas.models.response.DetallePlazaDTO;
import mx.gob.imss.mscme.admonplazas.models.response.RespuestaGenerica;

public interface AdministracionPlazasService {

	RespuestaGenerica<Page<DetallePlazaDTO>> busquedaPlazasFiltro(Long cveOoad, Integer numPlaza, Pageable pageable);

	RespuestaGenerica<DetallePlazaDTO> buscarDetallePlaza(Long idPlaza);

	RespuestaGenerica<Void> eliminarPlaza(Long idPlaza, String token);

}
