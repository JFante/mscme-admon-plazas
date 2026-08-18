package mx.gob.imss.mscme.admonplazas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import mx.gob.imss.mscme.admonplazas.models.entities.ControlCargaPlaza;

public interface ControlCargaPlazaRepository extends JpaRepository<ControlCargaPlaza, Long> {

    @Query("""
            SELECT CASE WHEN COUNT(cc) > 0 THEN TRUE ELSE FALSE END
            FROM ControlCargaPlaza cc
            WHERE cc.idConvocatoria.idConvocatoria = :idConvocatoria
            AND cc.stpFinCarga IS NULL
            AND cc.indActivo = :indActivo
            """)
    boolean existeCargaEnProceso(@Param("idConvocatoria") Long idConvocatoria, @Param("indActivo") Long indActivo);

}
