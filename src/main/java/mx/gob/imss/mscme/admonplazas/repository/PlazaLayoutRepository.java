package mx.gob.imss.mscme.admonplazas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import mx.gob.imss.mscme.admonplazas.models.entities.PlazaLayout;
import mx.gob.imss.mscme.admonplazas.models.response.plaza.LabelValueProjection;
import mx.gob.imss.mscme.admonplazas.models.response.plaza.MedicoAspiranteDatosGeneralesProjection;

public interface PlazaLayoutRepository extends JpaRepository<PlazaLayout, Long>, JpaSpecificationExecutor<PlazaLayout> {

	String PLAZAS_POR_ESPECIALIDAD_REGLAS = """
		    FROM CMEC_PLAZA_LAYOUT p
		    WHERE p.IND_ACTIVO = 1
		      AND p.DES_REGIMEN = :regimen
		      AND (

		            (
		                :regimen = 'ORDINARIO'
		                AND :cveEspecialidad = '04'
		                AND (
		                        p.CVE_AREA_RESPONSABILIDAD = '04'
		                     OR (p.CVE_AREA_RESPONSABILIDAD IN ('50','62')
		                         AND p.CVE_CATEGORIA = '20360280')
		                    )
		            )

		         OR (
		                :regimen = 'ORDINARIO'
		                AND :cveEspecialidad IN ('22','A4')
		                AND (
		                        p.CVE_AREA_RESPONSABILIDAD = :cveEspecialidad
		                     OR (
		                            p.CVE_AREA_RESPONSABILIDAD = '62'
		                            AND p.CVE_CATEGORIA = '20360180'
		                        )
		                    )
		            )

		         OR (
		                :regimen = 'ORDINARIO'
		                AND :cveEspecialidad NOT IN ('04','22','A4')
		                AND p.CVE_AREA_RESPONSABILIDAD = :cveEspecialidad
		                AND p.CVE_CATEGORIA = '20360180'
		            )

		         OR (
		                :regimen = 'COPLAMAR'
		                AND p.CVE_AREA_RESPONSABILIDAD = :cveEspecialidad
		            )
		          )
		""";

	@Query(value = """
		SELECT
		    USU.ID_USUARIO AS idUsuario,
		    cf.REF_GUID_DOCUMENTO AS  refFotografia, 
		    TRIM(
		        USU.NOM_NOMBRE || ' ' ||
		        COALESCE(USU.NOM_APELLIDO_PATERNO, '') || ' ' ||
		        COALESCE(USU.NOM_APELLIDO_MATERNO, '')
		    ) AS nombreCompleto,
	    	USU.ID_PERFIL AS idPerfil,
		    CASE 
		        WHEN USU.CVE_MATRICULA IS NOT NULL THEN USU.CVE_MATRICULA
		        WHEN CPC.DES_FOLIO_ME IS NOT NULL THEN CPC.DES_FOLIO_ME
		        ELSE ''
		    END AS matriculaFolio,
		    USU.CVE_MATRICULA AS matricula,
		    CPC.DES_FOLIO_ME AS folio,
    		USU.REF_EMAIL AS correo,
    		CDC.REF_CORREO_ADICIONAL AS correoAdicional,
			CRV.ID_ESTATUS_VERIFICACION AS idEstatusValidacion,
			CEVG.DES_ESTATUS AS estatusValidacion,
			CS.DES_SEXO AS genero,
			USU.REF_CURP AS curp,
			USU.REF_NSS AS nss,
			USU.REF_RFC AS rfc,
			
			ESPECIALIDADES.especialidadesConcat AS especialidadesClaves,
			ESPECIALIDADES.especialidadesDesc   AS especialidades,
			CONV.ID_TIPO_CONVOCATORIA AS idTipoConvocatoria,
			TCONV.DES_TIPO_CONVOCATORIA AS tipoConvocatoria
		    
		    FROM CMET_USUARIO USU
		    JOIN CMET_PARTICIPACION_CONV CPC ON CPC.ID_USUARIO = USU.ID_USUARIO AND CPC.IND_ACTIVO = 1 AND CPC.STP_BAJA_REGISTRO IS NULL
		    JOIN CMEC_CONVOCATORIA CONV ON CPC.ID_CONVOCATORIA = CONV.ID_CONVOCATORIA
		    LEFT JOIN CMEC_TIPO_CONVOCATORIA TCONV ON TCONV.ID_TIPO_CONVOCATORIA = CONV.ID_TIPO_CONVOCATORIA AND TCONV.IND_ACTIVO = 1 
		    

		    LEFT JOIN CMEC_SEXO CS ON USU.ID_SEXO  = CS.ID_SEXO  
		    LEFT JOIN  CMET_DATO_CONTACTO CDC  ON CDC.ID_PARTICIPACION   = CPC.ID_PARTICIPACION   AND CDC.IND_ACTIVO = 1 
		    LEFT JOIN  CMET_FOTOGRAFIA cf   ON cf.ID_PARTICIPACION   = CPC.ID_PARTICIPACION   AND cf.IND_ACTIVO = 1 
		    LEFT JOIN CMET_RESULTADO_VERIFICACION CRV ON CPC.ID_PARTICIPACION =CRV.ID_PARTICIPACION AND CRV.IND_ACTIVO = 1 AND CRV.STP_BAJA_REGISTRO IS NULL
			LEFT JOIN CMEC_ESTATUS_VERIFICACION CEVG ON CRV.ID_ESTATUS_VERIFICACION = CEVG.ID_ESTATUS_VERIFICACION
				
			LEFT JOIN LATERAL (
			    SELECT
			        LISTAGG(
			            CED.CVE_ESPECIALIDAD || ' - ' || CED.DES_ESPECIALIDAD,
			            ' / '
			        ) WITHIN GROUP (
			            ORDER BY CED.ID_ESPECIALIDAD_DOCUMENTO
			        ) AS especialidadesConcat,
			        
			        LISTAGG(
			            CED.DES_ESPECIALIDAD,
			            ' / '
			        ) WITHIN GROUP (
			            ORDER BY CED.ID_ESPECIALIDAD_DOCUMENTO
			        ) AS especialidadesDesc
			    FROM CMET_ESPECIALIDAD_DOCUMENTO CED
			    JOIN CMET_ESPECIALIDAD_EVALUACION CEE ON CEE.ID_ESPECIALIDAD_DOCUMENTO  = CED.ID_ESPECIALIDAD_DOCUMENTO  AND CEE.IND_ACTIVO = 1 AND CEE.STP_BAJA_REGISTRO IS NULL
			    WHERE CED.ID_PARTICIPACION = CPC.ID_PARTICIPACION
			      AND CED.IND_ACTIVO = 1
			      AND CED.STP_BAJA_REGISTRO IS NULL
			      AND CEE.ID_ESTATUS_VERIFICACION=3
			) ESPECIALIDADES
			ON ESPECIALIDADES.especialidadesDesc IS NOT NULL
			
			
		    WHERE
		    USU.IND_ACTIVO = 1
		    AND USU.STP_BAJA_REGISTRO IS NULL
		    AND CONV.IND_ACTIVO = 1
		    AND (:matriculaFolio IS NULL OR USU.CVE_MATRICULA = :matriculaFolio OR CPC.DES_FOLIO_ME = :matriculaFolio)
		    
			""", nativeQuery = true)
	MedicoAspiranteDatosGeneralesProjection buscarMedicoMatriculaFolio(@Param("matriculaFolio") String matriculaFolio);
	
	
	@Query(value = """
			SELECT
			DISTINCT
			    p.CVE_OOAD       AS value,
			    p.DESC_OOAD      AS label
			FROM CMEC_PLAZA_LAYOUT p
			WHERE p.ind_activo = 1
			  AND p.CVE_AREA_RESPONSABILIDAD = :cveEspecialidad
			  AND p.DES_REGIMEN = :regimen
			  AND (:cveCategoria IS NULL OR p.CVE_CATEGORIA = :cveCategoria)
			ORDER BY p.CVE_OOAD ASC
			""", nativeQuery = true)
	List<LabelValueProjection> findPlazasActivasPorRegimenEspecialidadCategoria(@Param("regimen") String regimen,@Param("cveEspecialidad") String cveEspecialidad,@Param("cveCategoria") String cveCategoria);
	
	
	@Query(value = """
			 SELECT DISTINCT
			     p.CVE_OOAD  AS value,
			     p.DESC_OOAD AS label
			 FROM CMEC_PLAZA_LAYOUT p
			 WHERE p.IND_ACTIVO = 1
			   AND p.DES_REGIMEN = :regimen
			   AND (
			         p.CVE_AREA_RESPONSABILIDAD = :cveEspecialidad
			      OR (
		             :cveEspecialidad = '04'
			         AND p.CVE_AREA_RESPONSABILIDAD IN ('50','62')
			         AND p.CVE_CATEGORIA = '20360280'
			         )
			       )
			 ORDER BY p.CVE_OOAD ASC
			""", nativeQuery = true)
	List<LabelValueProjection> findPlazasActivasPorRegimenEspecialidadMedicinaFamiliar(@Param("regimen") String regimen,@Param("cveEspecialidad") String cveEspecialidad);
	
	
	
	@Query(value = """
        SELECT DISTINCT
            p.CVE_OOAD  AS value,
            p.DESC_OOAD AS label
        FROM CMEC_PLAZA_LAYOUT p
        WHERE p.ind_activo = 1
          AND p.DES_REGIMEN = :regimen
          AND (
                p.CVE_AREA_RESPONSABILIDAD = :cveEspecialidad
                OR (
                    :cveEspecialidad IN ('22','A4')
                    AND p.CVE_AREA_RESPONSABILIDAD = '62'
                    AND p.CVE_CATEGORIA = '20360180'
                )
          )
			""", nativeQuery = true)
	List<LabelValueProjection> findPlazasActivasPorRegimenOrdinarioCasoHematologia(@Param("regimen") String regimen,@Param("cveEspecialidad") String cveEspecialidad);
	
	@Query(value = """
			SELECT
			DISTINCT
			    p.CVE_UNIDAD       AS value,
			    p.DESC_UNIDAD      AS label
			FROM CMEC_PLAZA_LAYOUT p
			WHERE p.ind_activo = 1
			  AND p.CVE_AREA_RESPONSABILIDAD = :cveEspecialidad
			  AND p.CVE_OOAD = :cveOaad
			  AND p.DES_REGIMEN = :regimen
			ORDER BY p.DESC_UNIDAD
			""", nativeQuery = true)
	List<LabelValueProjection> findPlazasActivasPorRegimenOoad(@Param("regimen") String regimen,@Param("cveEspecialidad") String cveEspecialidad,@Param("cveOaad") String cveOaad);
	
	
	@Query(value = """
		    SELECT DISTINCT
		        p.CVE_UNIDAD  AS value,
		        p.DESC_UNIDAD AS label
		    FROM CMEC_PLAZA_LAYOUT p
		    WHERE p.IND_ACTIVO = 1
		      AND p.CVE_OOAD = :cveOaad
		      AND p.DES_REGIMEN = :regimen
		      AND (

		            (
		                :regimen = 'ORDINARIO'
		                AND :cveEspecialidad = '04'
		                AND (
		                        p.CVE_AREA_RESPONSABILIDAD = '04'
		                     OR (p.CVE_AREA_RESPONSABILIDAD IN ('50','62')
		                         AND p.CVE_CATEGORIA = '20360280')
		                    )
		            )

		         OR (
		                :regimen = 'ORDINARIO'
		                AND :cveEspecialidad IN ('22','A4')
		                AND (
		                        p.CVE_AREA_RESPONSABILIDAD = :cveEspecialidad
		                     OR (
		                            p.CVE_AREA_RESPONSABILIDAD = '62'
		                            AND p.CVE_CATEGORIA = '20360180'
		                        )
		                    )
		            )

		         OR (
		                :regimen = 'ORDINARIO'
		                AND :cveEspecialidad NOT IN ('04','22','A4')
		                AND p.CVE_AREA_RESPONSABILIDAD = :cveEspecialidad
		                AND p.CVE_CATEGORIA = '20360180'
		            )

		         OR (
		                :regimen = 'COPLAMAR'
		                AND p.CVE_AREA_RESPONSABILIDAD = :cveEspecialidad
		            )
		          )
		    ORDER BY p.DESC_UNIDAD
		""", nativeQuery = true)
	List<LabelValueProjection> findPlazasActivasPorRegimenOoadReglas(@Param("regimen") String regimen,
			@Param("cveEspecialidad") String cveEspecialidad, @Param("cveOaad") String cveOaad);

	@Query(value = """
		    SELECT DISTINCT
		        p.CVE_ZONA  AS value,
		        p.DESC_ZONA AS label
		    FROM CMEC_PLAZA_LAYOUT p
		    WHERE p.IND_ACTIVO = 1
			  AND p.CVE_ZONA IS NOT NULL
		      AND p.CVE_OOAD = :cveOaad
		      AND p.DES_REGIMEN = :regimen
		      AND (

		            (
		                :regimen = 'ORDINARIO'
		                AND :cveEspecialidad = '04'
		                AND (
		                        p.CVE_AREA_RESPONSABILIDAD = '04'
		                     OR (p.CVE_AREA_RESPONSABILIDAD IN ('50','62')
		                         AND p.CVE_CATEGORIA = '20360280')
		                    )
		            )

		         OR (
		                :regimen = 'ORDINARIO'
		                AND :cveEspecialidad IN ('22','A4')
		                AND (
		                        p.CVE_AREA_RESPONSABILIDAD = :cveEspecialidad
		                     OR (
		                            p.CVE_AREA_RESPONSABILIDAD = '62'
		                            AND p.CVE_CATEGORIA = '20360180'
		                        )
		                    )
		            )

		         OR (
		                :regimen = 'ORDINARIO'
		                AND :cveEspecialidad NOT IN ('04','22','A4')
		                AND p.CVE_AREA_RESPONSABILIDAD = :cveEspecialidad
		                AND p.CVE_CATEGORIA = '20360180'
		            )

		         OR (
		                :regimen = 'COPLAMAR'
		                AND p.CVE_AREA_RESPONSABILIDAD = :cveEspecialidad
		            )
		          )
		    ORDER BY p.DESC_ZONA
		""", nativeQuery = true)
	List<LabelValueProjection> findZonasPorRegimenOoadReglas(@Param("regimen") String regimen,
		@Param("cveEspecialidad") String cveEspecialidad, @Param("cveOaad") String cveOaad);
	
	@Query(value = """
		    SELECT DISTINCT
		        p.CVE_TURNO AS value,
		        p.DESC_TURNO AS label
		""" + PLAZAS_POR_ESPECIALIDAD_REGLAS + """
		      AND p.CVE_TURNO IS NOT NULL
		    ORDER BY p.DESC_TURNO
		""", nativeQuery = true)
	List<LabelValueProjection> findTurnosPorRegimenEspecialidadReglas(@Param("regimen") String regimen,
		@Param("cveEspecialidad") String cveEspecialidad);

	@Query(value = """
		    SELECT DISTINCT
		        p.CVE_MARCA_OCUPACIÓN AS value,
		        p.DESC_MARCA_OCUPACION AS label
		""" + PLAZAS_POR_ESPECIALIDAD_REGLAS + """
		      AND p.CVE_MARCA_OCUPACIÓN IS NOT NULL
		    ORDER BY p.DESC_MARCA_OCUPACION
		""", nativeQuery = true)
	List<LabelValueProjection> findMarcasOcupacionPorRegimenEspecialidadReglas(@Param("regimen") String regimen,
		@Param("cveEspecialidad") String cveEspecialidad);

	@Query(value = """
		    SELECT DISTINCT
		        p.CVE_HORARIO AS value,
		        p.DESC_HORARIO AS label
		""" + PLAZAS_POR_ESPECIALIDAD_REGLAS + """
		      AND p.CVE_TURNO = :cveTurno
		      AND p.CVE_HORARIO IS NOT NULL
		    ORDER BY p.DESC_HORARIO
		""", nativeQuery = true)
	List<LabelValueProjection> findHorariosPorRegimenEspecialidadTurnoReglas(@Param("regimen") String regimen,
		@Param("cveEspecialidad") String cveEspecialidad, @Param("cveTurno") String cveTurno);

}
