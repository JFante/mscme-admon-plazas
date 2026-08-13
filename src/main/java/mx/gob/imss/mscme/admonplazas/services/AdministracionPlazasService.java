package mx.gob.imss.mscme.admonplazas.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import mx.gob.imss.mscme.admonplazas.models.response.plaza.DetallePlazaDTO;

public interface AdministracionPlazasService {

	Page<DetallePlazaDTO> busquedaPlazasFiltro(Long cveOoad, Integer numPlaza, Pageable pageable);

}
