package mx.gob.imss.mscme.admonplazas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.imss.mscme.admonplazas.models.entities.PlazaLayout;

public interface PlazaLayoutRepository extends JpaRepository<PlazaLayout, Long>, JpaSpecificationExecutor<PlazaLayout> {

	long countByIdConvocatoriaAndEstatusPlaza_IdEstatusPlazaAndIndActivo(Long idConvocatoria, Long idEstatusPlaza,
			Long indActivo);

	boolean existsByIdConvocatoriaAndCveOoadAndNumPlazaAndIndActivo(Long idConvocatoria, Long cveOoad,
			Integer numPlaza, Long indActivo);

	Optional<PlazaLayout> findByIdPlazaAndIndActivo(Long idPlaza, Long indActivo);

	@Query("""
			SELECT COALESCE(MAX(pl.numPlaza), 0)
			FROM PlazaLayout pl
			WHERE pl.idConvocatoria = :idConvocatoria
			AND pl.indActivo = 1
			""")
	Integer obtenerMaximoNumPlazaActivoPorConvocatoria(@Param("idConvocatoria") Long idConvocatoria);

	@Query("""
			SELECT CASE WHEN COUNT(pl) > 0 THEN TRUE ELSE FALSE END
			FROM PlazaLayout pl
			WHERE pl.idConvocatoria = :idConvocatoria
			AND pl.estatusPlaza.idEstatusPlaza = :idEstatusPlaza
			AND pl.indActivo = 1
			""")
	boolean existenPlazasAsignadas(@Param("idConvocatoria") Long idConvocatoria,@Param("idEstatusPlaza") Long idEstatusPlaza);

	@Modifying
	@Transactional
	@Query(value = """
			UPDATE CMEC_PLAZA_LAYOUT
			SET IND_ACTIVO = 0,
			    ID_USUARIO_BAJA = :idUsuarioAdmon,
			    STP_BAJA_REGISTRO = SYSDATE
			WHERE ID_CONVOCATORIA = :idConvocatoria
			""", nativeQuery = true)
	void borrarPlazasPorConvocatoria(@Param("idConvocatoria") Long idConvocatoria,
			@Param("idUsuarioAdmon") Long idUsuarioAdmon);

	@Modifying
	@Transactional
	@Query(value = """
			DELETE FROM CMEC_PLAZA_LAYOUT
			WHERE ID_CONVOCATORIA = :idConvocatoria
			AND IND_ACTIVO = 1
			""", nativeQuery = true)
	void borrarFisicamentePlazasPorConvocatoria(@Param("idConvocatoria") Long idConvocatoria);

}
