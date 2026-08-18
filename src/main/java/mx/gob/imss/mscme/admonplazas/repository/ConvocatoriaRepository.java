package mx.gob.imss.mscme.admonplazas.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import mx.gob.imss.mscme.admonplazas.models.dto.LabelValueDto;
import mx.gob.imss.mscme.admonplazas.models.entities.Convocatoria;
import mx.gob.imss.mscme.admonplazas.utils.Constantes;

public interface ConvocatoriaRepository extends JpaRepository<Convocatoria, Long> {

    @Query("""
            SELECT new mx.gob.imss.mscme.admonplazas.models.dto.LabelValueDto(
                c.idConvocatoria,
                c.descripcion
            )
            FROM Convocatoria c
            WHERE c.indActivo = 1
            ORDER BY c.descripcion
            """)
    List<LabelValueDto> findActivasLabelValue();

    List<Convocatoria> findByIndActivo(Integer indActivo);

    Optional<Convocatoria> findFirstByIndActivo(Integer indActivo);

    @Query(value = """
                    select IND_CONCLUIDO from CMET_MESA_CONVOCATORIA
            where IND_CONCLUIDO = 1
            and IND_ACTIVO = 1
            and ID_CONVOCATORIA = :idConvocatoria
                                    """, nativeQuery = true)
    Integer validarConvocatoria(@Param("idConvocatoria") Long idConvocatoria);
    
	Optional<Convocatoria> findFirstByIndActivoAndFechaInicioLessThanEqualAndFechaFinGreaterThanEqualOrderByFechaInicioDesc(Integer indActivo, LocalDate fechaInicio, LocalDate fechaFin);

	default Optional<Convocatoria> findConvocatoriaActivaPorPeriodoActual() {
		LocalDate fechaActual = LocalDate.now();
		return findFirstByIndActivoAndFechaInicioLessThanEqualAndFechaFinGreaterThanEqualOrderByFechaInicioDesc(
				Constantes.ESTADO_ACTIVO_INTEGER, fechaActual, fechaActual);
    }

}
