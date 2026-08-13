package mx.gob.imss.mscme.admonplazas.services.Impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mx.gob.imss.mscme.admonplazas.mappers.PlazaLayoutMapper;
import mx.gob.imss.mscme.admonplazas.models.response.plaza.DetallePlazaDTO;
import mx.gob.imss.mscme.admonplazas.repository.PlazaLayoutRepository;
import mx.gob.imss.mscme.admonplazas.repository.specification.PlazaLayoutSpecification;
import mx.gob.imss.mscme.admonplazas.services.AdministracionPlazasService;

@Service
@Log4j2
@RequiredArgsConstructor
public class AdministracionPlazasServiceImpl implements AdministracionPlazasService {

	private final PlazaLayoutRepository plazaLayoutRepository;
	private final PlazaLayoutSpecification plazaLayoutSpecification;
	private final PlazaLayoutMapper plazaLayoutMapper;

	@Override
	public Page<DetallePlazaDTO> busquedaPlazasFiltro(Long cveOoad, Integer numPlaza, Pageable pageable) {
		return plazaLayoutRepository
				.findAll(plazaLayoutSpecification.generarSpecificationCveOoadNumPlaza(cveOoad, numPlaza), pageable)
				.map(plazaLayoutMapper::toDetallePlazaDTO);
	}

}
