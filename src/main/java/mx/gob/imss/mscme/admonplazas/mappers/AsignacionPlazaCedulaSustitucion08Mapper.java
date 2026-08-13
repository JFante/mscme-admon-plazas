/**
 * 
 */
package mx.gob.imss.mscme.admonplazas.mappers;

import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import mx.gob.imss.mscme.admonplazas.models.entities.AsignacionMedico;
import mx.gob.imss.mscme.admonplazas.models.entities.Sustitucion;
import mx.gob.imss.mscme.admonplazas.models.request.plaza.AsignacionCedulaSustitucionO8Request;
import mx.gob.imss.mscme.admonplazas.models.response.plaza.MedicoAspiranteDatosGeneralesCedulaProjection;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface AsignacionPlazaCedulaSustitucion08Mapper {

	@Mappings({

			@Mapping(target = "folio", source = "datosGenerales.matriculaFolio", qualifiedByName = "folioFormato"),
			@Mapping(target = "desconcentrada", expression = "java(buildDesconcentrada(asignacionMedico))"),
			@Mapping(target = "candidato", source = "datosGenerales.nombreCompleto"),
			@Mapping(target = "folioEgresado", source = "datosGenerales.folio"),
			// para el interno
			@Mapping(target = "matricula", source = "datosGenerales.matricula"),
			@Mapping(target = "egresado", constant = ""),
			
			// para el interno falta revisar
			@Mapping(target = "caracter", source = "datosGenerales.tipoUsuario"),
			
			@Mapping(target = "especialidad", source = "asignacionMedico.idSustitucion.desEspecialidad"),
			@Mapping(target = "localidad", source = "asignacionMedico.idSustitucion.desZona"),
			@Mapping(target = "mesa", source = "datosGenerales.numMesa", defaultValue = "")		

		
			
			
			
	})
	AsignacionCedulaSustitucionO8Request toRequest(MedicoAspiranteDatosGeneralesCedulaProjection datosGenerales,
			AsignacionMedico asignacionMedico);

	// =====================================================
	// ================== Helpers ==========================
	// =====================================================

	default String buildDesconcentrada(AsignacionMedico asignacionMedico) {
		if (asignacionMedico == null || asignacionMedico.getIdSustitucion() == null) {
			return "";
		}

		Sustitucion s = asignacionMedico.getIdSustitucion();

		if (StringUtils.isBlank(s.getCveOoad()) || StringUtils.isBlank(s.getDesOoad())) {
			return "";
		}

		return String.format("%02d", Integer.parseInt(s.getCveOoad())) + " - " + s.getDesOoad().trim();
	}

	@Named("folioFormato")
	default String buildFolio(String matriculaFolio) {
	    return "CNMBT/" + StringUtils.defaultString(matriculaFolio).trim();
	}


}
