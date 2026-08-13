package mx.gob.imss.mscme.admonplazas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import mx.gob.imss.mscme.admonplazas.models.entities.PlazaLayout;

public interface PlazaLayoutRepository extends JpaRepository<PlazaLayout, Long>, JpaSpecificationExecutor<PlazaLayout> {


}
