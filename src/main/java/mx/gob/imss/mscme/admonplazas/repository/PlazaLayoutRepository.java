package mx.gob.imss.mscme.admonplazas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import mx.gob.imss.mscme.admonplazas.models.entities.PlazaLayout;

public interface PlazaLayoutRepository extends JpaRepository<PlazaLayout, Long>, JpaSpecificationExecutor<PlazaLayout> {

	long countByIdConvocatoriaAndEstatusPlaza_IdEstatusPlazaAndIndActivo(Long idConvocatoria, Long idEstatusPlaza,
			Long indActivo);

	boolean existsByIdConvocatoriaAndCveOoadAndNumPlazaAndIndActivo(Long idConvocatoria, Long cveOoad,
			Integer numPlaza, Long indActivo);

}
