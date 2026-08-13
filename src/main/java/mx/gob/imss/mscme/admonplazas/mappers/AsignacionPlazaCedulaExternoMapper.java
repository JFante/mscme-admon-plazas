/**
 * 
 */
package mx.gob.imss.mscme.admonplazas.mappers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import mx.gob.imss.mscme.admonplazas.models.entities.AsignacionMedico;
import mx.gob.imss.mscme.admonplazas.models.entities.PlazaLayout;
import mx.gob.imss.mscme.admonplazas.models.request.plaza.AsignacionCedulaExternoRequest;
import mx.gob.imss.mscme.admonplazas.models.response.plaza.MedicoAspiranteDatosGeneralesCedulaProjection;

/**
 * 
 */
@Mapper(componentModel = "spring", imports = { LocalDateTime.class, DateTimeFormatter.class })
public interface AsignacionPlazaCedulaExternoMapper {

	@Mappings({

			@Mapping(target = "fecha", expression = "java(obtenerFechaAhora())"),
			@Mapping(target = "folio", source = "datosGenerales.matriculaFolio", qualifiedByName = "folioFormato"),
			@Mapping(target = "desconcentrada", expression = "java(buildOoad(asignacionMedico))"),
			@Mapping(target = "candidato", source = "datosGenerales.nombreCompleto"),
			@Mapping(target = "egresado", constant = ""),
			@Mapping(target = "modalidad", constant = "Externo"),

			// ===== PlazaLayout =====
			@Mapping(target = "especialidad", source = "asignacionMedico.idPlazaLayout.descAreaResponsabilidad"),
			@Mapping(target = "plaza", source = "asignacionMedico.idPlazaLayout.numPlaza"),
			@Mapping(target = "contratacion", source = "asignacionMedico.idPlazaLayout.descTipoPlaza"),
			@Mapping(target = "marca", source = "asignacionMedico.idPlazaLayout.cveMarcaOcupacion"),
			@Mapping(target = "descripcion", source = "asignacionMedico.idPlazaLayout.descMarcaOcupacion"),
			@Mapping(target = "categoria", source = "asignacionMedico.idPlazaLayout.descCategoria"),
			@Mapping(target = "unidad", source = "asignacionMedico.idPlazaLayout.descUnidad"),
			@Mapping(target = "area", source = "asignacionMedico.idPlazaLayout.cveAreaResponsabilidad"),
			@Mapping(target = "zona", source = "asignacionMedico.idPlazaLayout.descZona"),
			@Mapping(target = "turno", source = "asignacionMedico.idPlazaLayout.descTurno"),
			@Mapping(target = "mesa", source = "datosGenerales.numMesa", defaultValue = "")
			
	})
	AsignacionCedulaExternoRequest toRequest(MedicoAspiranteDatosGeneralesCedulaProjection datosGenerales,AsignacionMedico asignacionMedico);

	// =====================================================
	// ================== Helpers ==========================
	// =====================================================

	default String buildOoad(AsignacionMedico asignacionMedico) {
		if (asignacionMedico == null || asignacionMedico.getIdPlazaLayout() == null) {
			return "";
		}

		PlazaLayout p = asignacionMedico.getIdPlazaLayout();

		if (p.getCveOoad() == null || StringUtils.isBlank(p.getDescOoad())) {
			return "";
		}

		return String.format("%02d", p.getCveOoad()) + " - " + p.getDescOoad().trim();
	}

	// Fecha 
	default String obtenerFechaAhora() {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	}
	
	@Named("folioFormato")
	default String buildFolio(String matriculaFolio) {
	    return "CNMBT/" + StringUtils.defaultString(matriculaFolio).trim();
	}

}
