package mx.gob.imss.mscme.admonplazas.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import feign.Param;
import mx.gob.imss.mscme.admonplazas.models.entities.Usuario;
import mx.gob.imss.mscme.admonplazas.models.response.CitaMedico;
import mx.gob.imss.mscme.admonplazas.models.response.DocumentosAdicionales;
import mx.gob.imss.mscme.admonplazas.models.response.Medicos;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	// Puedes agregar metodos de busqueda personalizados si los necesitas
	Optional<Usuario> findByRefEmailAndIndActivo(String email, Long indActivo);

	Optional<Usuario> findByRefCurpAndRefEmailAndIndActivo(String curp, String email, Long indActivo);

	Optional<Usuario> findByIdUsuarioAndIndActivo(Long idUsuario, Long indActivo);

	@Query(value = """
						WITH usuarios AS (
			SELECT
				p.ID_PARTICIPACION AS idParticipacion,
				cu.NOM_NOMBRE AS nombre,
				cu.NOM_APELLIDO_PATERNO AS apellidoPaterno,
				cu.NOM_APELLIDO_MATERNO AS apellidoMaterno,
				cu.REF_CURP AS curp,
				cu.REF_EMAIL AS email,
				IAM.REF_PUNTAJE_CONTRATACION AS promedio,
				p.DES_FOLIO_ME AS folioMe,
				ce.DES_ESPECIALIDAD AS especialidad,
				cu.ID_PERFIL AS idPerfil,
				CASE
					-- Validacion anterior basada en IDs.
					-- Se conserva para posible rollback.
					-- Reemplazada por validacion basada en IND_PERFIL_INTERNO.
					--
					-- WHEN cu.ID_PERFIL = 2
					WHEN EXISTS (SELECT 1 FROM CMEC_PERFIL_MEDICO PM WHERE PM.ID_PERFIL = cu.ID_PERFIL AND PM.IND_PERFIL_INTERNO = 1)
					AND IAM.REF_TIPO_CONTRATACION = 'BECADOS' THEN 'BECADO'
					-- Validacion anterior basada en IDs.
					-- Se conserva para posible rollback.
					-- Reemplazada por validacion basada en IND_PERFIL_INTERNO.
					--
					-- WHEN cu.ID_PERFIL = 2 THEN 'RESIDENTE'
					WHEN EXISTS (SELECT 1 FROM CMEC_PERFIL_MEDICO PM WHERE PM.ID_PERFIL = cu.ID_PERFIL AND PM.IND_PERFIL_INTERNO = 1) THEN 'RESIDENTE'
					-- Validacion anterior basada en IDs.
					-- Se conserva para posible rollback.
					-- Reemplazada por validacion basada en IND_PERFIL_INTERNO.
					--
					-- WHEN cu.ID_PERFIL IN (3, 6) THEN 'EXTERNO'
					WHEN EXISTS (SELECT 1 FROM CMEC_PERFIL_MEDICO PM WHERE PM.ID_PERFIL = cu.ID_PERFIL AND PM.IND_PERFIL_INTERNO = 0) THEN 'EXTERNO'
					ELSE 'OTRO'
				END AS tipoContratacion,
				cc.ID_TIPO_CONVOCATORIA AS idTipoConvocatoria,
				IAM.REF_LUGAR AS lugar,
				IAM.REF_SEDE AS sede
			FROM
				CMET_PARTICIPACION_CONV p
			INNER JOIN CMET_USUARIO cu
			        ON
				cu.ID_USUARIO = p.ID_USUARIO
			LEFT JOIN CMET_INFO_AREA_MEDICA IAM
			        ON
				IAM.REF_CURP = cu.REF_CURP
			INNER JOIN CMET_RESULTADO_VERIFICACION crv
			        ON
				crv.ID_PARTICIPACION = p.ID_PARTICIPACION
			INNER JOIN CMEC_CONVOCATORIA cc
			        ON
				cc.ID_CONVOCATORIA = p.ID_CONVOCATORIA
			INNER JOIN CMET_ESPECIALIDAD_DOCUMENTO ced ON ced.ID_PARTICIPACION = p.ID_PARTICIPACION
				AND ced.IND_PRIORIDAD = 1
			INNER JOIN CMEC_ESPECIALIDAD ce
			                ON
				ce.CVE_ESPECIALIDAD = ced.CVE_ESPECIALIDAD

			WHERE
				crv.ID_ESTATUS_VERIFICACION = 3
				AND cu.IND_ACTIVO = 1
				AND p.ID_CONVOCATORIA = :idConvocatoria
				AND ce.ID_ESPECIALIDAD = :idEspecialidad
				AND (
			            (:idTipoMedico = 1
					-- Validacion anterior basada en IDs.
					-- Se conserva para posible rollback.
					-- Reemplazada por validacion basada en IND_PERFIL_INTERNO.
					--
					-- AND cu.ID_PERFIL = 2
					AND EXISTS (SELECT 1 FROM CMEC_PERFIL_MEDICO PM WHERE PM.ID_PERFIL = cu.ID_PERFIL AND PM.IND_PERFIL_INTERNO = 1)
					AND IAM.REF_TIPO_CONTRATACION = 'BECADOS')
				OR (:idTipoMedico = 2
					-- Validacion anterior basada en IDs.
					-- Se conserva para posible rollback.
					-- Reemplazada por validacion basada en IND_PERFIL_INTERNO.
					--
					-- AND cu.ID_PERFIL = 2
					AND EXISTS (SELECT 1 FROM CMEC_PERFIL_MEDICO PM WHERE PM.ID_PERFIL = cu.ID_PERFIL AND PM.IND_PERFIL_INTERNO = 1)
					AND IAM.REF_TIPO_CONTRATACION IS NULL)
				OR (:idTipoMedico = 3
					-- Validacion anterior basada en IDs.
					-- Se conserva para posible rollback.
					-- Reemplazada por validacion basada en IND_PERFIL_INTERNO.
					--
					-- AND cu.ID_PERFIL IN (3, 6))
					AND EXISTS (SELECT 1 FROM CMEC_PERFIL_MEDICO PM WHERE PM.ID_PERFIL = cu.ID_PERFIL AND PM.IND_PERFIL_INTERNO = 0))
			      )

			)
			SELECT
				u.*
			FROM
				usuarios u
			WHERE
				NOT EXISTS (
				SELECT
					1
				FROM
					CMET_CITA_MEDICO ci
				INNER JOIN CMET_CONTROL_ENVIO_CITAS ccec
			        ON
					ccec.ID_CONTROL_ENVIO = ci.ID_CONTROL_ENVIO
				INNER JOIN CMET_MESA_DETALLE cm
			        ON
					cm.ID_MESA_DETALLE = ci.ID_MESA_DETALLE
				WHERE
					ci.ID_PARTICIPACION = u.idParticipacion
					AND ccec.ID_CONVOCATORIA = :idConvocatoria
					AND ccec.ID_TIPO_MEDICO = :idTipoMedico
					AND cm.ID_ESPECIALIDAD = :idEspecialidad
					AND cm.IND_ACTIVO = 1 
			)
			ORDER BY
				CASE
					WHEN :idTipoMedico IN (1, 2) THEN u.promedio
					ELSE NULL
				END DESC NULLS LAST,
				u.folioMe ASC FETCH FIRST :limite ROWS ONLY
												""", nativeQuery = true)
	List<Medicos> getParticipacionMedicoPorConvocatoria(@Param("idConvocatoria") Long idConvocatoria,
			@Param("idTipoMedico") Long idTipoMedico, @Param("idEspecialidad") Long idEspecialidad,
			@Param("limite") Long limite);

	@Query(value = """
								WITH citas_unicas AS (
			    SELECT
			        ci.ID_CITA_MEDICO AS idCitaMedica,
			        ci.ID_PARTICIPACION,
			        TO_CHAR(cm.FEC_ATENCION,'DD/MM/YYYY') AS fechaCita,
			        ct.HORA_INICIO AS hora,
			        ci.NUM_CONSECUTIVO_PRIORIDAD AS turno,
			        cm.NUM_MESA AS mesa,
			        ROW_NUMBER() OVER (
			            PARTITION BY ci.ID_PARTICIPACION
			            ORDER BY cm.FEC_ATENCION, ct.HORA_INICIO
			        ) AS rn
			    FROM CMET_CITA_MEDICO ci
			    INNER JOIN CMET_CONTROL_ENVIO_CITAS ccec
			        ON ccec.ID_CONTROL_ENVIO = ci.ID_CONTROL_ENVIO
			    INNER JOIN CMET_MESA_DETALLE cm
			        ON cm.ID_MESA_DETALLE = ci.ID_MESA_DETALLE
			    INNER JOIN CMEC_TURNO ct
			        ON ct.ID_TURNO = cm.ID_TURNO
			    WHERE ci.ID_CONTROL_ENVIO = :idControlEnvio AND cm.IND_ACTIVO = 1 AND ccec.IND_ACTIVO = 1 AND  ci.IND_ACTIVO = 1

			),

			medicos AS (
			    SELECT DISTINCT
			        p.ID_PARTICIPACION        AS idParticipacion,
			        cu.NOM_NOMBRE             AS nombre,
			        cu.NOM_APELLIDO_PATERNO   AS apellidoPaterno,
			        cu.NOM_APELLIDO_MATERNO   AS apellidoMaterno,
			        NVL(cu.CVE_MATRICULA,'')  AS matricula,
			        cu.ID_PERFIL              AS idPerfil,
			        NVL(cu.REF_EMAIL,'')      AS email,
			        NVL(cu.REF_CURP,'')       AS curp,
			        IAM.REF_PUNTAJE_CONTRATACION          AS promedio,
			        p.DES_FOLIO_ME            AS folioMe,
			        (
			            SELECT ce.DES_ESPECIALIDAD
			            FROM CMET_ESPECIALIDAD_DOCUMENTO ced
			            INNER JOIN CMEC_ESPECIALIDAD ce
			                ON ce.CVE_ESPECIALIDAD = ced.CVE_ESPECIALIDAD
			            WHERE ced.ID_PARTICIPACION = p.ID_PARTICIPACION
			              AND ced.IND_PRIORIDAD = 1
			            FETCH FIRST 1 ROW ONLY
			        ) AS especialidad,
			        CASE
			            -- Validacion anterior basada en IDs.
			            -- Se conserva para posible rollback.
			            -- Reemplazada por validacion basada en IND_PERFIL_INTERNO.
			            --
			            -- WHEN cu.ID_PERFIL = 2 AND IAM.REF_TIPO_CONTRATACION = 'BECADOS' THEN 'BECADO'
			            WHEN EXISTS (SELECT 1 FROM CMEC_PERFIL_MEDICO PM WHERE PM.ID_PERFIL = cu.ID_PERFIL AND PM.IND_PERFIL_INTERNO = 1) AND IAM.REF_TIPO_CONTRATACION = 'BECADOS' THEN 'BECADO'
			            -- Validacion anterior basada en IDs.
			            -- Se conserva para posible rollback.
			            -- Reemplazada por validacion basada en IND_PERFIL_INTERNO.
			            --
			            -- WHEN cu.ID_PERFIL = 2 THEN 'RESIDENTE'
			            WHEN EXISTS (SELECT 1 FROM CMEC_PERFIL_MEDICO PM WHERE PM.ID_PERFIL = cu.ID_PERFIL AND PM.IND_PERFIL_INTERNO = 1) THEN 'RESIDENTE'
			            -- Validacion anterior basada en IDs.
			            -- Se conserva para posible rollback.
			            -- Reemplazada por validacion basada en IND_PERFIL_INTERNO.
			            --
			            -- WHEN cu.ID_PERFIL IN (3, 6) THEN 'EXTERNO'
			            WHEN EXISTS (SELECT 1 FROM CMEC_PERFIL_MEDICO PM WHERE PM.ID_PERFIL = cu.ID_PERFIL AND PM.IND_PERFIL_INTERNO = 0) THEN 'EXTERNO'
			            ELSE 'OTRO'
			        END AS tipoContratacion,
			        cc.ID_TIPO_CONVOCATORIA   AS idTipoConvocatoria,
			        EXTRACT(YEAR FROM cc.FEC_INICIO) AS fechaConvocatoria,
			        NVL(IAM.REF_LUGAR,'')     AS lugar,
			        NVL(IAM.REF_SEDE,'')      AS sede,
			        NVL(cc.REF_URL_TABLERO_OFERTA,'')       AS url
			    FROM CMET_CITA_MEDICO ccm
			    INNER JOIN CMET_CONTROL_ENVIO_CITAS ccec
			        ON ccec.ID_CONTROL_ENVIO = ccm.ID_CONTROL_ENVIO
			    INNER JOIN CMET_PARTICIPACION_CONV p
			        ON p.ID_PARTICIPACION = ccm.ID_PARTICIPACION
			    INNER JOIN CMET_USUARIO cu
			        ON cu.ID_USUARIO = p.ID_USUARIO
			    LEFT JOIN CMET_INFO_AREA_MEDICA IAM
			        ON IAM.REF_CURP = cu.REF_CURP
			    INNER JOIN CMEC_CONVOCATORIA cc
			        ON cc.ID_CONVOCATORIA = p.ID_CONVOCATORIA
			    WHERE ccec.ID_CONTROL_ENVIO = :idControlEnvio
			      AND cu.IND_ACTIVO = 1
			      AND (ccm.IND_ENVIADO is null OR ccm.IND_ENVIADO = 0)
			      AND (
			            (:idTipoMedico = 1
					-- Validacion anterior basada en IDs.
					-- Se conserva para posible rollback.
					-- Reemplazada por validacion basada en IND_PERFIL_INTERNO.
					--
					-- AND cu.ID_PERFIL = 2
					AND EXISTS (SELECT 1 FROM CMEC_PERFIL_MEDICO PM WHERE PM.ID_PERFIL = cu.ID_PERFIL AND PM.IND_PERFIL_INTERNO = 1)
					AND IAM.REF_TIPO_CONTRATACION = 'BECADOS')
				OR (:idTipoMedico = 2
					-- Validacion anterior basada en IDs.
					-- Se conserva para posible rollback.
					-- Reemplazada por validacion basada en IND_PERFIL_INTERNO.
					--
					-- AND cu.ID_PERFIL = 2
					AND EXISTS (SELECT 1 FROM CMEC_PERFIL_MEDICO PM WHERE PM.ID_PERFIL = cu.ID_PERFIL AND PM.IND_PERFIL_INTERNO = 1)
					AND (IAM.REF_TIPO_CONTRATACION <> 'BECADOS'
						OR IAM.REF_TIPO_CONTRATACION IS NULL))
				OR (:idTipoMedico = 3
					-- Validacion anterior basada en IDs.
					-- Se conserva para posible rollback.
					-- Reemplazada por validacion basada en IND_PERFIL_INTERNO.
					--
					-- AND cu.ID_PERFIL IN (3, 6))
					AND EXISTS (SELECT 1 FROM CMEC_PERFIL_MEDICO PM WHERE PM.ID_PERFIL = cu.ID_PERFIL AND PM.IND_PERFIL_INTERNO = 0))
			      )

			)

			SELECT
			    m.*,
			    c.idCitaMedica,
			    c.fechaCita,
			  substr(lpad(to_char(c.hora), 4, '0'), 1, 2) ||':'||substr(lpad(to_char(c.hora), 4, '0'),
			     3,
			     2)  || ' hrs' AS hora,
			    c.turno,
			    c.mesa
			FROM medicos m
			LEFT JOIN citas_unicas c
			    ON c.ID_PARTICIPACION = m.idParticipacion
			   AND c.rn = 1


														""", countQuery = """
			SELECT COUNT(DISTINCT p.ID_PARTICIPACION)
			FROM CMET_CITA_MEDICO ccm
			INNER JOIN CMET_CONTROL_ENVIO_CITAS ccec
			    ON ccec.ID_CONTROL_ENVIO = ccm.ID_CONTROL_ENVIO
			INNER JOIN CMET_PARTICIPACION_CONV p
			    ON p.ID_PARTICIPACION = ccm.ID_PARTICIPACION
			INNER JOIN CMET_USUARIO cu
			    ON cu.ID_USUARIO = p.ID_USUARIO
			LEFT JOIN CMET_INFO_AREA_MEDICA IAM
			    ON IAM.REF_CURP = cu.REF_CURP
			WHERE ccec.ID_CONTROL_ENVIO = :idControlEnvio
			  AND cu.IND_ACTIVO = 1
			  AND (ccm.IND_ENVIADO IS NULL OR ccm.IND_ENVIADO = 0)
			  AND (
			        -- Validacion anterior basada en IDs.
			        -- Se conserva para posible rollback.
			        -- Reemplazada por validacion basada en IND_PERFIL_INTERNO.
			        --
			        -- (:idTipoMedico = 1 AND cu.ID_PERFIL = 2 AND IAM.REF_TIPO_CONTRATACION = 'BECADOS')
			        (:idTipoMedico = 1 AND EXISTS (SELECT 1 FROM CMEC_PERFIL_MEDICO PM WHERE PM.ID_PERFIL = cu.ID_PERFIL AND PM.IND_PERFIL_INTERNO = 1) AND IAM.REF_TIPO_CONTRATACION = 'BECADOS')
			     -- Validacion anterior basada en IDs.
			     -- Se conserva para posible rollback.
			     -- Reemplazada por validacion basada en IND_PERFIL_INTERNO.
			     --
			     -- OR (:idTipoMedico = 2 AND cu.ID_PERFIL = 2 AND (IAM.REF_TIPO_CONTRATACION <> 'BECADOS' OR IAM.REF_TIPO_CONTRATACION IS NULL))
			     OR (:idTipoMedico = 2 AND EXISTS (SELECT 1 FROM CMEC_PERFIL_MEDICO PM WHERE PM.ID_PERFIL = cu.ID_PERFIL AND PM.IND_PERFIL_INTERNO = 1) AND (IAM.REF_TIPO_CONTRATACION <> 'BECADOS' OR IAM.REF_TIPO_CONTRATACION IS NULL))
			     -- Validacion anterior basada en IDs.
			     -- Se conserva para posible rollback.
			     -- Reemplazada por validacion basada en IND_PERFIL_INTERNO.
			     --
			     -- OR (:idTipoMedico = 3 AND cu.ID_PERFIL IN (3, 6))
			     OR (:idTipoMedico = 3 AND EXISTS (SELECT 1 FROM CMEC_PERFIL_MEDICO PM WHERE PM.ID_PERFIL = cu.ID_PERFIL AND PM.IND_PERFIL_INTERNO = 0))
			  )
			""", nativeQuery = true)
	Page<CitaMedico> buscarMedicosPorControlEnvio(@Param("idControlEnvio") Long idControlEnvio,
			@Param("idTipoMedico") Long idTipoMedico, Pageable pageable);

	@Query(value = """
						SELECT
				cdc.ID_PARTICIPACION AS id_Participacion,
				cdc.REF_CORREO_ADICIONAL AS emailAdicional
			FROM
				CMET_DATO_CONTACTO cdc
			WHERE
				cdc.ID_PARTICIPACION = :idParticipacion AND cdc.IND_ACTIVO = 1
						""", nativeQuery = true)
	List<DocumentosAdicionales> buscarEmailAdicionalesByParticipacion(@Param("idParticipacion") Long idParticipacion);

	@Query(value = """
					WITH citas_unicas AS (
			SELECT
							ci.ID_CITA_MEDICO AS idCitaMedica,
							ci.ID_PARTICIPACION,
							TO_CHAR(cm.FEC_ATENCION, 'DD/MM/YYYY') AS fechaCita,
							ct.HORA_INICIO AS hora,
							ci.NUM_CONSECUTIVO_PRIORIDAD AS turno,
							cm.NUM_MESA AS mesa,
							ROW_NUMBER() OVER (
							    PARTITION BY ci.ID_PARTICIPACION
			ORDER BY
				cm.FEC_ATENCION,
				ct.HORA_INICIO
							) AS rn
			FROM
				CMET_CITA_MEDICO ci
			INNER JOIN CMET_CONTROL_ENVIO_CITAS ccec
							ON
				ccec.ID_CONTROL_ENVIO = ci.ID_CONTROL_ENVIO
			INNER JOIN CMET_MESA_DETALLE cm
							ON
				cm.ID_MESA_DETALLE = ci.ID_MESA_DETALLE
			INNER JOIN CMEC_TURNO ct
							ON
				ct.ID_TURNO = cm.ID_TURNO
			WHERE
				ccec.ID_TIPO_MEDICO = :idTipoMedico
				AND ccec.ID_CONVOCATORIA = :idConvocatoria
				AND cm.IND_ACTIVO = 1
				AND ccec.IND_ACTIVO = 1
				AND ci.IND_ACTIVO = 1
				AND (ci.IND_ENVIADO = 0
					OR ci.IND_ENVIADO IS NULL )

							),

							medicos AS (
			SELECT
				DISTINCT
							p.ID_PARTICIPACION AS idParticipacion,
							cu.NOM_NOMBRE AS nombre,
							cu.NOM_APELLIDO_PATERNO AS apellidoPaterno,
							cu.NOM_APELLIDO_MATERNO AS apellidoMaterno,
							NVL(cu.CVE_MATRICULA, '') AS matricula,
							cu.ID_PERFIL AS idPerfil,
							NVL(cu.REF_EMAIL, '') AS email,
							NVL(cu.REF_CURP, '') AS curp,
							IAM.REF_PUNTAJE_CONTRATACION AS promedio,
							p.DES_FOLIO_ME AS folioMe,
							(
				SELECT
					ce.DES_ESPECIALIDAD
				FROM
					CMET_ESPECIALIDAD_DOCUMENTO ced
				INNER JOIN CMEC_ESPECIALIDAD ce
							        ON
					ce.CVE_ESPECIALIDAD = ced.CVE_ESPECIALIDAD
				WHERE
					ced.ID_PARTICIPACION = p.ID_PARTICIPACION
					AND ced.IND_PRIORIDAD = 1
							    FETCH FIRST 1 ROW ONLY
							) AS especialidad,
							CASE
							    -- Validacion anterior basada en IDs.
							    -- Se conserva para posible rollback.
							    -- Reemplazada por validacion basada en IND_PERFIL_INTERNO.
							    --
							    -- WHEN cu.ID_PERFIL = 2
							    WHEN EXISTS (SELECT 1 FROM CMEC_PERFIL_MEDICO PM WHERE PM.ID_PERFIL = cu.ID_PERFIL AND PM.IND_PERFIL_INTERNO = 1)
					AND IAM.REF_TIPO_CONTRATACION = 'BECADOS' THEN 'BECADO'
					-- Validacion anterior basada en IDs.
					-- Se conserva para posible rollback.
					-- Reemplazada por validacion basada en IND_PERFIL_INTERNO.
					--
					-- WHEN cu.ID_PERFIL = 2 THEN 'RESIDENTE'
					WHEN EXISTS (SELECT 1 FROM CMEC_PERFIL_MEDICO PM WHERE PM.ID_PERFIL = cu.ID_PERFIL AND PM.IND_PERFIL_INTERNO = 1) THEN 'RESIDENTE'
					-- Validacion anterior basada en IDs.
					-- Se conserva para posible rollback.
					-- Reemplazada por validacion basada en IND_PERFIL_INTERNO.
					--
					-- WHEN cu.ID_PERFIL IN (3, 6) THEN 'EXTERNO'
					WHEN EXISTS (SELECT 1 FROM CMEC_PERFIL_MEDICO PM WHERE PM.ID_PERFIL = cu.ID_PERFIL AND PM.IND_PERFIL_INTERNO = 0) THEN 'EXTERNO'
					ELSE 'OTRO'
				END AS tipoContratacion,
							cc.ID_TIPO_CONVOCATORIA AS idTipoConvocatoria,
							EXTRACT(YEAR FROM cc.FEC_INICIO) AS fechaConvocatoria,
							NVL(IAM.REF_LUGAR, '') AS lugar,
							NVL(IAM.REF_SEDE, '') AS sede,
							NVL(cc.REF_URL_TABLERO_OFERTA, '') AS url
			FROM
				CMET_CITA_MEDICO ccm
			INNER JOIN CMET_CONTROL_ENVIO_CITAS ccec
							ON
				ccec.ID_CONTROL_ENVIO = ccm.ID_CONTROL_ENVIO
			INNER JOIN CMET_PARTICIPACION_CONV p
							ON
				p.ID_PARTICIPACION = ccm.ID_PARTICIPACION
			INNER JOIN CMET_USUARIO cu
							ON
				cu.ID_USUARIO = p.ID_USUARIO
			LEFT JOIN CMET_INFO_AREA_MEDICA IAM
							ON
				IAM.REF_CURP = cu.REF_CURP
			INNER JOIN CMEC_CONVOCATORIA cc
							ON
				cc.ID_CONVOCATORIA = p.ID_CONVOCATORIA
			WHERE
				ccec.ID_CONVOCATORIA = :idConvocatoria
				AND cu.IND_ACTIVO = 1 AND ccec.IND_ACTIVO = 1 AND ccm.IND_ACTIVO = 1
				AND (ccm.IND_ENVIADO IS NULL
					OR ccm.IND_ENVIADO = 0)
				AND (
							    (:idTipoMedico = 1
					-- Validacion anterior basada en IDs.
					-- Se conserva para posible rollback.
					-- Reemplazada por validacion basada en IND_PERFIL_INTERNO.
					--
					-- AND cu.ID_PERFIL = 2
					AND EXISTS (SELECT 1 FROM CMEC_PERFIL_MEDICO PM WHERE PM.ID_PERFIL = cu.ID_PERFIL AND PM.IND_PERFIL_INTERNO = 1)
					AND IAM.REF_TIPO_CONTRATACION = 'BECADOS')
				OR (:idTipoMedico = 2
					-- Validacion anterior basada en IDs.
					-- Se conserva para posible rollback.
					-- Reemplazada por validacion basada en IND_PERFIL_INTERNO.
					--
					-- AND cu.ID_PERFIL = 2
					AND EXISTS (SELECT 1 FROM CMEC_PERFIL_MEDICO PM WHERE PM.ID_PERFIL = cu.ID_PERFIL AND PM.IND_PERFIL_INTERNO = 1)
					AND (IAM.REF_TIPO_CONTRATACION <> 'BECADOS'
						OR IAM.REF_TIPO_CONTRATACION IS NULL))
				OR (:idTipoMedico = 3
					-- Validacion anterior basada en IDs.
					-- Se conserva para posible rollback.
					-- Reemplazada por validacion basada en IND_PERFIL_INTERNO.
					--
					-- AND cu.ID_PERFIL IN (3, 6))
					AND EXISTS (SELECT 1 FROM CMEC_PERFIL_MEDICO PM WHERE PM.ID_PERFIL = cu.ID_PERFIL AND PM.IND_PERFIL_INTERNO = 0))
							)

							)

							SELECT
							m.*,
							c.idCitaMedica,
							c.fechaCita,
							substr(lpad(to_char(c.hora), 4, '0'), 1, 2) || ':' || substr(lpad(to_char(c.hora), 4, '0'),
							3,
							2) || ' hrs' AS hora,
							c.turno,
							c.mesa
			FROM
				medicos m
			LEFT JOIN citas_unicas c
							ON
				c.ID_PARTICIPACION = m.idParticipacion
				AND c.rn = 1

				""", countQuery = """
			 SELECT COUNT(DISTINCT p.ID_PARTICIPACION)
			FROM CMET_CITA_MEDICO ccm
			INNER JOIN CMET_CONTROL_ENVIO_CITAS ccec
			    ON ccec.ID_CONTROL_ENVIO = ccm.ID_CONTROL_ENVIO
			INNER JOIN CMET_PARTICIPACION_CONV p
			    ON p.ID_PARTICIPACION = ccm.ID_PARTICIPACION
			INNER JOIN CMET_USUARIO cu
			    ON cu.ID_USUARIO = p.ID_USUARIO
			LEFT JOIN CMET_INFO_AREA_MEDICA IAM
			    ON IAM.REF_CURP = cu.REF_CURP
			WHERE ccec.ID_CONVOCATORIA = :idConvocatoria
			  AND ccec.ID_TIPO_MEDICO = :idTipoMedico
			  AND ccec.IND_ACTIVO = 1
			  AND ccm.IND_ACTIVO = 1
			  AND cu.IND_ACTIVO = 1
			  AND (ccm.IND_ENVIADO IS NULL OR ccm.IND_ENVIADO = 0)
			  AND (
			        -- Validacion anterior basada en IDs.
			        -- Se conserva para posible rollback.
			        -- Reemplazada por validacion basada en IND_PERFIL_INTERNO.
			        --
			        -- (:idTipoMedico = 1 AND cu.ID_PERFIL = 2 AND IAM.REF_TIPO_CONTRATACION = 'BECADOS')
			        (:idTipoMedico = 1 AND EXISTS (SELECT 1 FROM CMEC_PERFIL_MEDICO PM WHERE PM.ID_PERFIL = cu.ID_PERFIL AND PM.IND_PERFIL_INTERNO = 1) AND IAM.REF_TIPO_CONTRATACION = 'BECADOS')
			     -- Validacion anterior basada en IDs.
			     -- Se conserva para posible rollback.
			     -- Reemplazada por validacion basada en IND_PERFIL_INTERNO.
			     --
			     -- OR (:idTipoMedico = 2 AND cu.ID_PERFIL = 2 AND (IAM.REF_TIPO_CONTRATACION <> 'BECADOS' OR IAM.REF_TIPO_CONTRATACION IS NULL))
			     OR (:idTipoMedico = 2 AND EXISTS (SELECT 1 FROM CMEC_PERFIL_MEDICO PM WHERE PM.ID_PERFIL = cu.ID_PERFIL AND PM.IND_PERFIL_INTERNO = 1) AND (IAM.REF_TIPO_CONTRATACION <> 'BECADOS' OR IAM.REF_TIPO_CONTRATACION IS NULL))
			     -- Validacion anterior basada en IDs.
			     -- Se conserva para posible rollback.
			     -- Reemplazada por validacion basada en IND_PERFIL_INTERNO.
			     --
			     -- OR (:idTipoMedico = 3 AND cu.ID_PERFIL IN (3, 6))
			     OR (:idTipoMedico = 3 AND EXISTS (SELECT 1 FROM CMEC_PERFIL_MEDICO PM WHERE PM.ID_PERFIL = cu.ID_PERFIL AND PM.IND_PERFIL_INTERNO = 0))
			  )
			  """, nativeQuery = true)
	Page<CitaMedico> buscarMedicosPorControlNoEnvio(@Param("idConvocatoria") Long idConvocatoria,
			@Param("idTipoMedico") Long idTipoMedico, Pageable pageable);

	/****************************************************/
	@Query(value = """
						WITH citas_unicas AS (
			SELECT
			ci.ID_CITA_MEDICO AS idCitaMedica,
			ci.ID_PARTICIPACION,
			TO_CHAR(cm.FEC_ATENCION,'DD/MM/YYYY') AS fechaCita,
			ct.HORA_INICIO AS hora,
			ci.NUM_CONSECUTIVO_PRIORIDAD AS turno,
			cm.NUM_MESA AS mesa,
			ROW_NUMBER() OVER (
			    PARTITION BY ci.ID_PARTICIPACION
			    ORDER BY cm.FEC_ATENCION, ct.HORA_INICIO
			) AS rn
			FROM CMET_CITA_MEDICO ci
			INNER JOIN CMET_CONTROL_ENVIO_CITAS ccec
			ON ccec.ID_CONTROL_ENVIO = ci.ID_CONTROL_ENVIO
			INNER JOIN CMET_MESA_DETALLE cm
			ON cm.ID_MESA_DETALLE = ci.ID_MESA_DETALLE
			INNER JOIN CMEC_TURNO ct
			ON ct.ID_TURNO = cm.ID_TURNO
			WHERE ci.ID_CONTROL_ENVIO = :idControlEnvio AND cm.IND_ACTIVO = 1 AND ccec.IND_ACTIVO = 1 AND  ci.IND_ACTIVO = 1

			),

			medicos AS (
			SELECT DISTINCT
			p.ID_PARTICIPACION        AS idParticipacion,
			cu.NOM_NOMBRE             AS nombre,
			cu.NOM_APELLIDO_PATERNO   AS apellidoPaterno,
			cu.NOM_APELLIDO_MATERNO   AS apellidoMaterno,
			NVL(cu.CVE_MATRICULA,'')  AS matricula,
			cu.ID_PERFIL              AS idPerfil,
			NVL(cu.REF_EMAIL,'')      AS email,
			NVL(cu.REF_CURP,'')       AS curp,
			IAM.REF_PUNTAJE_CONTRATACION          AS promedio,
			p.DES_FOLIO_ME            AS folioMe,
			(
			    SELECT ce.DES_ESPECIALIDAD
			    FROM CMET_ESPECIALIDAD_DOCUMENTO ced
			    INNER JOIN CMEC_ESPECIALIDAD ce
			        ON ce.CVE_ESPECIALIDAD = ced.CVE_ESPECIALIDAD
			    WHERE ced.ID_PARTICIPACION = p.ID_PARTICIPACION
			      AND ced.IND_PRIORIDAD = 1
			    FETCH FIRST 1 ROW ONLY
			) AS especialidad,
			CASE
			    -- Validacion anterior basada en IDs.
			    -- Se conserva para posible rollback.
			    -- Reemplazada por validacion basada en IND_PERFIL_INTERNO.
			    --
			    -- WHEN cu.ID_PERFIL = 2 AND IAM.REF_TIPO_CONTRATACION = 'BECADOS' THEN 'BECADO'
			    WHEN EXISTS (SELECT 1 FROM CMEC_PERFIL_MEDICO PM WHERE PM.ID_PERFIL = cu.ID_PERFIL AND PM.IND_PERFIL_INTERNO = 1) AND IAM.REF_TIPO_CONTRATACION = 'BECADOS' THEN 'BECADO'
			    -- Validacion anterior basada en IDs.
			    -- Se conserva para posible rollback.
			    -- Reemplazada por validacion basada en IND_PERFIL_INTERNO.
			    --
			    -- WHEN cu.ID_PERFIL = 2 THEN 'RESIDENTE'
			    WHEN EXISTS (SELECT 1 FROM CMEC_PERFIL_MEDICO PM WHERE PM.ID_PERFIL = cu.ID_PERFIL AND PM.IND_PERFIL_INTERNO = 1) THEN 'RESIDENTE'
			    -- Validacion anterior basada en IDs.
			    -- Se conserva para posible rollback.
			    -- Reemplazada por validacion basada en IND_PERFIL_INTERNO.
			    --
			    -- WHEN cu.ID_PERFIL IN (3, 6) THEN 'EXTERNO'
			    WHEN EXISTS (SELECT 1 FROM CMEC_PERFIL_MEDICO PM WHERE PM.ID_PERFIL = cu.ID_PERFIL AND PM.IND_PERFIL_INTERNO = 0) THEN 'EXTERNO'
			    ELSE 'OTRO'
			END AS tipoContratacion,
			cc.ID_TIPO_CONVOCATORIA   AS idTipoConvocatoria,
			EXTRACT(YEAR FROM cc.FEC_INICIO) AS fechaConvocatoria,
			NVL(IAM.REF_LUGAR,'')     AS lugar,
			NVL(IAM.REF_SEDE,'')      AS sede,
			NVL(cc.REF_URL_TABLERO_OFERTA,'')       AS url
			FROM CMET_CITA_MEDICO ccm
			INNER JOIN CMET_CONTROL_ENVIO_CITAS ccec
			ON ccec.ID_CONTROL_ENVIO = ccm.ID_CONTROL_ENVIO
			INNER JOIN CMET_PARTICIPACION_CONV p
			ON p.ID_PARTICIPACION = ccm.ID_PARTICIPACION
			INNER JOIN CMET_USUARIO cu
			ON cu.ID_USUARIO = p.ID_USUARIO
			LEFT JOIN CMET_INFO_AREA_MEDICA IAM
			ON IAM.REF_CURP = cu.REF_CURP
			INNER JOIN CMEC_CONVOCATORIA cc
			ON cc.ID_CONVOCATORIA = p.ID_CONVOCATORIA
			WHERE ccec.ID_CONTROL_ENVIO = :idControlEnvio
			AND cu.IND_ACTIVO = 1
			AND (ccm.IND_ENVIADO is null OR ccm.IND_ENVIADO = 0)
			AND (
			    (:idTipoMedico = 1
			-- Validacion anterior basada en IDs.
			-- Se conserva para posible rollback.
			-- Reemplazada por validacion basada en IND_PERFIL_INTERNO.
			--
			-- AND cu.ID_PERFIL = 2
			AND EXISTS (SELECT 1 FROM CMEC_PERFIL_MEDICO PM WHERE PM.ID_PERFIL = cu.ID_PERFIL AND PM.IND_PERFIL_INTERNO = 1)
			AND IAM.REF_TIPO_CONTRATACION = 'BECADOS')
			OR (:idTipoMedico = 2
			-- Validacion anterior basada en IDs.
			-- Se conserva para posible rollback.
			-- Reemplazada por validacion basada en IND_PERFIL_INTERNO.
			--
			-- AND cu.ID_PERFIL = 2
			AND EXISTS (SELECT 1 FROM CMEC_PERFIL_MEDICO PM WHERE PM.ID_PERFIL = cu.ID_PERFIL AND PM.IND_PERFIL_INTERNO = 1)
			AND (IAM.REF_TIPO_CONTRATACION <> 'BECADOS'
				OR IAM.REF_TIPO_CONTRATACION IS NULL))
			OR (:idTipoMedico = 3
			-- Validacion anterior basada en IDs.
			-- Se conserva para posible rollback.
			-- Reemplazada por validacion basada en IND_PERFIL_INTERNO.
			--
			-- AND cu.ID_PERFIL IN (3, 6))
			AND EXISTS (SELECT 1 FROM CMEC_PERFIL_MEDICO PM WHERE PM.ID_PERFIL = cu.ID_PERFIL AND PM.IND_PERFIL_INTERNO = 0))
			)

			)

			SELECT
			c.idCitaMedica
			FROM medicos m
			LEFT JOIN citas_unicas c
			ON c.ID_PARTICIPACION = m.idParticipacion
			AND c.rn = 1


												""", nativeQuery = true)
	List<Long> obtenerIdsCitasPendientes(@Param("idControlEnvio") Long idControlEnvio,
			@Param("idTipoMedico") Long idTipoMedico);

	@Query(value = """
							WITH citas_unicas AS (
			SELECT
							ci.ID_CITA_MEDICO AS idCitaMedica,
							ci.ID_PARTICIPACION,
							TO_CHAR(cm.FEC_ATENCION, 'DD/MM/YYYY') AS fechaCita,
							ct.HORA_INICIO AS hora,
							ci.NUM_CONSECUTIVO_PRIORIDAD AS turno,
							cm.NUM_MESA AS mesa,
							ROW_NUMBER() OVER (
							    PARTITION BY ci.ID_PARTICIPACION
			ORDER BY
				cm.FEC_ATENCION,
				ct.HORA_INICIO
							) AS rn
			FROM
				CMET_CITA_MEDICO ci
			INNER JOIN CMET_CONTROL_ENVIO_CITAS ccec
							ON
				ccec.ID_CONTROL_ENVIO = ci.ID_CONTROL_ENVIO
			INNER JOIN CMET_MESA_DETALLE cm
							ON
				cm.ID_MESA_DETALLE = ci.ID_MESA_DETALLE
			INNER JOIN CMEC_TURNO ct
							ON
				ct.ID_TURNO = cm.ID_TURNO
			WHERE
				ccec.ID_TIPO_MEDICO = :idTipoMedico
				AND ccec.ID_CONVOCATORIA = :idConvocatoria
				AND cm.IND_ACTIVO = 1
				AND ccec.IND_ACTIVO = 1
				AND ci.IND_ACTIVO = 1
				AND (ci.IND_ENVIADO = 0
					OR ci.IND_ENVIADO IS NULL )

							),

							medicos AS (
			SELECT
				DISTINCT
							p.ID_PARTICIPACION AS idParticipacion,
							cu.NOM_NOMBRE AS nombre,
							cu.NOM_APELLIDO_PATERNO AS apellidoPaterno,
							cu.NOM_APELLIDO_MATERNO AS apellidoMaterno,
							NVL(cu.CVE_MATRICULA, '') AS matricula,
							cu.ID_PERFIL AS idPerfil,
							NVL(cu.REF_EMAIL, '') AS email,
							NVL(cu.REF_CURP, '') AS curp,
							IAM.REF_PUNTAJE_CONTRATACION AS promedio,
							p.DES_FOLIO_ME AS folioMe,
							(
				SELECT
					ce.DES_ESPECIALIDAD
				FROM
					CMET_ESPECIALIDAD_DOCUMENTO ced
				INNER JOIN CMEC_ESPECIALIDAD ce
							        ON
					ce.CVE_ESPECIALIDAD = ced.CVE_ESPECIALIDAD
				WHERE
					ced.ID_PARTICIPACION = p.ID_PARTICIPACION
					AND ced.IND_PRIORIDAD = 1
							    FETCH FIRST 1 ROW ONLY
							) AS especialidad,
							CASE
							    -- Validacion anterior basada en IDs.
							    -- Se conserva para posible rollback.
							    -- Reemplazada por validacion basada en IND_PERFIL_INTERNO.
							    --
							    -- WHEN cu.ID_PERFIL = 2
							    WHEN EXISTS (SELECT 1 FROM CMEC_PERFIL_MEDICO PM WHERE PM.ID_PERFIL = cu.ID_PERFIL AND PM.IND_PERFIL_INTERNO = 1)
					AND IAM.REF_TIPO_CONTRATACION = 'BECADOS' THEN 'BECADO'
					-- Validacion anterior basada en IDs.
					-- Se conserva para posible rollback.
					-- Reemplazada por validacion basada en IND_PERFIL_INTERNO.
					--
					-- WHEN cu.ID_PERFIL = 2 THEN 'RESIDENTE'
					WHEN EXISTS (SELECT 1 FROM CMEC_PERFIL_MEDICO PM WHERE PM.ID_PERFIL = cu.ID_PERFIL AND PM.IND_PERFIL_INTERNO = 1) THEN 'RESIDENTE'
					-- Validacion anterior basada en IDs.
					-- Se conserva para posible rollback.
					-- Reemplazada por validacion basada en IND_PERFIL_INTERNO.
					--
					-- WHEN cu.ID_PERFIL IN (3, 6) THEN 'EXTERNO'
					WHEN EXISTS (SELECT 1 FROM CMEC_PERFIL_MEDICO PM WHERE PM.ID_PERFIL = cu.ID_PERFIL AND PM.IND_PERFIL_INTERNO = 0) THEN 'EXTERNO'
					ELSE 'OTRO'
				END AS tipoContratacion,
							cc.ID_TIPO_CONVOCATORIA AS idTipoConvocatoria,
							EXTRACT(YEAR FROM cc.FEC_INICIO) AS fechaConvocatoria,
							NVL(IAM.REF_LUGAR, '') AS lugar,
							NVL(IAM.REF_SEDE, '') AS sede,
							NVL(cc.REF_URL_TABLERO_OFERTA, '') AS url
			FROM
				CMET_CITA_MEDICO ccm
			INNER JOIN CMET_CONTROL_ENVIO_CITAS ccec
							ON
				ccec.ID_CONTROL_ENVIO = ccm.ID_CONTROL_ENVIO
			INNER JOIN CMET_PARTICIPACION_CONV p
							ON
				p.ID_PARTICIPACION = ccm.ID_PARTICIPACION
			INNER JOIN CMET_USUARIO cu
							ON
				cu.ID_USUARIO = p.ID_USUARIO
			LEFT JOIN CMET_INFO_AREA_MEDICA IAM
							ON
				IAM.REF_CURP = cu.REF_CURP
			INNER JOIN CMEC_CONVOCATORIA cc
							ON
				cc.ID_CONVOCATORIA = p.ID_CONVOCATORIA
			WHERE
				ccec.ID_CONVOCATORIA = :idConvocatoria
				AND cu.IND_ACTIVO = 1 AND ccec.IND_ACTIVO = 1 AND ccm.IND_ACTIVO = 1
				AND (ccm.IND_ENVIADO IS NULL
					OR ccm.IND_ENVIADO = 0)
				AND (
							    (:idTipoMedico = 1
					-- Validacion anterior basada en IDs.
					-- Se conserva para posible rollback.
					-- Reemplazada por validacion basada en IND_PERFIL_INTERNO.
					--
					-- AND cu.ID_PERFIL = 2
					AND EXISTS (SELECT 1 FROM CMEC_PERFIL_MEDICO PM WHERE PM.ID_PERFIL = cu.ID_PERFIL AND PM.IND_PERFIL_INTERNO = 1)
					AND IAM.REF_TIPO_CONTRATACION = 'BECADOS')
				OR (:idTipoMedico = 2
					-- Validacion anterior basada en IDs.
					-- Se conserva para posible rollback.
					-- Reemplazada por validacion basada en IND_PERFIL_INTERNO.
					--
					-- AND cu.ID_PERFIL = 2
					AND EXISTS (SELECT 1 FROM CMEC_PERFIL_MEDICO PM WHERE PM.ID_PERFIL = cu.ID_PERFIL AND PM.IND_PERFIL_INTERNO = 1)
					AND (IAM.REF_TIPO_CONTRATACION <> 'BECADOS'
						OR IAM.REF_TIPO_CONTRATACION IS NULL))
				OR (:idTipoMedico = 3
					-- Validacion anterior basada en IDs.
					-- Se conserva para posible rollback.
					-- Reemplazada por validacion basada en IND_PERFIL_INTERNO.
					--
					-- AND cu.ID_PERFIL IN (3, 6))
					AND EXISTS (SELECT 1 FROM CMEC_PERFIL_MEDICO PM WHERE PM.ID_PERFIL = cu.ID_PERFIL AND PM.IND_PERFIL_INTERNO = 0))
							)

							)

							SELECT
							c.idCitaMedica

			FROM
				medicos m
			LEFT JOIN citas_unicas c
							ON
				c.ID_PARTICIPACION = m.idParticipacion
				AND c.rn = 1

				""", countQuery = """
			 SELECT COUNT(DISTINCT p.ID_PARTICIPACION)
			FROM CMET_CITA_MEDICO ccm
			INNER JOIN CMET_CONTROL_ENVIO_CITAS ccec
			    ON ccec.ID_CONTROL_ENVIO = ccm.ID_CONTROL_ENVIO
			INNER JOIN CMET_PARTICIPACION_CONV p
			    ON p.ID_PARTICIPACION = ccm.ID_PARTICIPACION
			INNER JOIN CMET_USUARIO cu
			    ON cu.ID_USUARIO = p.ID_USUARIO
			LEFT JOIN CMET_INFO_AREA_MEDICA IAM
			    ON IAM.REF_CURP = cu.REF_CURP
			WHERE ccec.ID_CONVOCATORIA = :idConvocatoria
			  AND ccec.ID_TIPO_MEDICO = :idTipoMedico
			  AND ccec.IND_ACTIVO = 1
			  AND ccm.IND_ACTIVO = 1
			  AND cu.IND_ACTIVO = 1
			  AND (ccm.IND_ENVIADO IS NULL OR ccm.IND_ENVIADO = 0)
			  AND (
			        -- Validacion anterior basada en IDs.
			        -- Se conserva para posible rollback.
			        -- Reemplazada por validacion basada en IND_PERFIL_INTERNO.
			        --
			        -- (:idTipoMedico = 1 AND cu.ID_PERFIL = 2 AND IAM.REF_TIPO_CONTRATACION = 'BECADOS')
			        (:idTipoMedico = 1 AND EXISTS (SELECT 1 FROM CMEC_PERFIL_MEDICO PM WHERE PM.ID_PERFIL = cu.ID_PERFIL AND PM.IND_PERFIL_INTERNO = 1) AND IAM.REF_TIPO_CONTRATACION = 'BECADOS')
			     -- Validacion anterior basada en IDs.
			     -- Se conserva para posible rollback.
			     -- Reemplazada por validacion basada en IND_PERFIL_INTERNO.
			     --
			     -- OR (:idTipoMedico = 2 AND cu.ID_PERFIL = 2 AND (IAM.REF_TIPO_CONTRATACION <> 'BECADOS' OR IAM.REF_TIPO_CONTRATACION IS NULL))
			     OR (:idTipoMedico = 2 AND EXISTS (SELECT 1 FROM CMEC_PERFIL_MEDICO PM WHERE PM.ID_PERFIL = cu.ID_PERFIL AND PM.IND_PERFIL_INTERNO = 1) AND (IAM.REF_TIPO_CONTRATACION <> 'BECADOS' OR IAM.REF_TIPO_CONTRATACION IS NULL))
			     -- Validacion anterior basada en IDs.
			     -- Se conserva para posible rollback.
			     -- Reemplazada por validacion basada en IND_PERFIL_INTERNO.
			     --
			     -- OR (:idTipoMedico = 3 AND cu.ID_PERFIL IN (3, 6))
			     OR (:idTipoMedico = 3 AND EXISTS (SELECT 1 FROM CMEC_PERFIL_MEDICO PM WHERE PM.ID_PERFIL = cu.ID_PERFIL AND PM.IND_PERFIL_INTERNO = 0))
			  )
			""", nativeQuery = true)
	List<Long> obtenerIdsCitasPendientesNoCita(@Param("idConvocatoria") Long idConvocatoria,
			@Param("idTipoMedico") Long idTipoMedico);

	@Query(value = """
			WITH citas_unicas AS (
			    SELECT
			        ci.ID_CITA_MEDICO AS idCitaMedica,
			        ci.ID_PARTICIPACION,
			        TO_CHAR(cm.FEC_ATENCION,'DD/MM/YYYY') AS fechaCita,
			        ct.HORA_INICIO AS hora,
			        ci.NUM_CONSECUTIVO_PRIORIDAD AS turno,
			        cm.NUM_MESA AS mesa,
			        ROW_NUMBER() OVER (PARTITION BY ci.ID_PARTICIPACION ORDER BY cm.FEC_ATENCION, ct.HORA_INICIO) AS rn
			    FROM CMET_CITA_MEDICO ci
			    JOIN CMET_CONTROL_ENVIO_CITAS ccec ON ccec.ID_CONTROL_ENVIO = ci.ID_CONTROL_ENVIO AND ccec.IND_ACTIVO = 1
			    JOIN CMET_MESA_DETALLE cm ON cm.ID_MESA_DETALLE = ci.ID_MESA_DETALLE AND cm.IND_ACTIVO = 1
			    JOIN CMEC_TURNO ct ON ct.ID_TURNO = cm.ID_TURNO
			    WHERE ci.ID_CITA_MEDICO IN (:idsCitaMedica) AND ci.IND_ACTIVO = 1
			),
			medicos AS (
			    SELECT DISTINCT
			        p.ID_PARTICIPACION AS idParticipacion,
			        cu.NOM_NOMBRE AS nombre,
			        cu.NOM_APELLIDO_PATERNO AS apellidoPaterno,
			        cu.NOM_APELLIDO_MATERNO AS apellidoMaterno,
			        NVL(cu.CVE_MATRICULA,'') AS matricula,
			        cu.ID_PERFIL AS idPerfil,
			        NVL(cu.REF_EMAIL,'') AS email,
			        NVL(cu.REF_CURP,'') AS curp,
			        IAM.REF_PUNTAJE_CONTRATACION AS promedio,
			        p.DES_FOLIO_ME AS folioMe,
			        (
			            SELECT ce.DES_ESPECIALIDAD
			            FROM CMET_ESPECIALIDAD_DOCUMENTO ced
			            JOIN CMEC_ESPECIALIDAD ce ON ce.CVE_ESPECIALIDAD = ced.CVE_ESPECIALIDAD
			            WHERE ced.ID_PARTICIPACION = p.ID_PARTICIPACION
			              AND ced.IND_PRIORIDAD = 1
			            FETCH FIRST 1 ROW ONLY
			        ) AS especialidad,
			        CASE
			            -- Validacion anterior basada en IDs.
			            -- Se conserva para posible rollback.
			            -- Reemplazada por validacion basada en IND_PERFIL_INTERNO.
			            --
			            -- WHEN cu.ID_PERFIL = 2 AND IAM.REF_TIPO_CONTRATACION = 'BECADOS' THEN 'BECADO'
			            WHEN EXISTS (SELECT 1 FROM CMEC_PERFIL_MEDICO PM WHERE PM.ID_PERFIL = cu.ID_PERFIL AND PM.IND_PERFIL_INTERNO = 1) AND IAM.REF_TIPO_CONTRATACION = 'BECADOS' THEN 'BECADO'
			            -- Validacion anterior basada en IDs.
			            -- Se conserva para posible rollback.
			            -- Reemplazada por validacion basada en IND_PERFIL_INTERNO.
			            --
			            -- WHEN cu.ID_PERFIL = 2 THEN 'RESIDENTE'
			            WHEN EXISTS (SELECT 1 FROM CMEC_PERFIL_MEDICO PM WHERE PM.ID_PERFIL = cu.ID_PERFIL AND PM.IND_PERFIL_INTERNO = 1) THEN 'RESIDENTE'
			            -- Validacion anterior basada en IDs.
			            -- Se conserva para posible rollback.
			            -- Reemplazada por validacion basada en IND_PERFIL_INTERNO.
			            --
			            -- WHEN cu.ID_PERFIL IN (3,6) THEN 'EXTERNO'
			            WHEN EXISTS (SELECT 1 FROM CMEC_PERFIL_MEDICO PM WHERE PM.ID_PERFIL = cu.ID_PERFIL AND PM.IND_PERFIL_INTERNO = 0) THEN 'EXTERNO'
			            ELSE 'OTRO'
			        END AS tipoContratacion,
			        cc.ID_TIPO_CONVOCATORIA AS idTipoConvocatoria,
			        EXTRACT(YEAR FROM cc.FEC_INICIO) AS fechaConvocatoria,
			        NVL(IAM.REF_LUGAR,'') AS lugar,
			        NVL(IAM.REF_SEDE,'') AS sede,
			        NVL(cc.REF_URL_TABLERO_OFERTA,'') AS url
			    FROM CMET_PARTICIPACION_CONV p
			    JOIN CMET_USUARIO cu ON cu.ID_USUARIO = p.ID_USUARIO AND cu.IND_ACTIVO = 1
			    LEFT JOIN CMET_INFO_AREA_MEDICA IAM ON IAM.REF_CURP = cu.REF_CURP
			    JOIN CMEC_CONVOCATORIA cc ON cc.ID_CONVOCATORIA = p.ID_CONVOCATORIA
			    JOIN CMET_CITA_MEDICO ci ON ci.ID_PARTICIPACION = p.ID_PARTICIPACION AND ci.ID_CITA_MEDICO IN (:idsCitaMedica)
			)
			SELECT
			    m.*,
			    c.idCitaMedica,
			    c.fechaCita,
			    SUBSTR(LPAD(TO_CHAR(c.hora),4,'0'),1,2) || ':' || SUBSTR(LPAD(TO_CHAR(c.hora),4,'0'),3,2) || ' hrs' AS hora,
			    c.turno,
			    c.mesa
			FROM medicos m
			LEFT JOIN citas_unicas c ON c.ID_PARTICIPACION = m.idParticipacion AND c.rn = 1
			""", nativeQuery = true)
	List<CitaMedico> obtenerMedicosPorIds(@Param("idsCitaMedica") List<Long> idsCitaMedica);
}
