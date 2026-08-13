package mx.gob.imss.mscme.admonplazas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.gob.imss.mscme.admonplazas.models.entities.Auditoria;

public interface AuditoriaRepository extends JpaRepository<Auditoria, Long> {
}