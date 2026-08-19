
package mx.gob.imss.mscme.admonplazas.mappers;

import java.math.BigDecimal;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import mx.gob.imss.mscme.admonplazas.models.entities.PlazaLayout;
import mx.gob.imss.mscme.admonplazas.models.response.DetallePlazaDTO;
import mx.gob.imss.mscme.admonplazas.models.response.PlazaLayoutCargaDTO;
@Mapper(
	    componentModel = MappingConstants.ComponentModel.SPRING,
	    unmappedTargetPolicy = ReportingPolicy.IGNORE
	)
public interface PlazaLayoutMapper {

	   PlazaLayoutMapper INSTANCE = Mappers.getMapper(PlazaLayoutMapper.class);

	    @Mappings({
	        @Mapping(target = "idPlaza", source = "idPlaza"),
	        @Mapping(target = "cveOoad", expression = "java(formatIntegerToTwoDigits(entity.getCveOoad()))"),
	        @Mapping(target = "cvePuesto", source = "cvePuesto"),
	        @Mapping(target = "cveUnidad", source = "cveUnidad"),
	        @Mapping(target = "refAltoCostoVida", source = "refAltoCostoVida"),
	        @Mapping(target = "especialidad", source = "descAreaResponsabilidad"),
	        @Mapping(target = "descCategoria", source = "descCategoria"),
	        @Mapping(target = "descRegimen", source = "desRegimen"),
	        @Mapping(target = "descTurno", source = "descTurno"),
	        @Mapping(target = "descTipoPlaza", source = "descTipoPlaza"),
	        @Mapping(target = "descMarcaOcupacion", source = "descMarcaOcupacion"),
	        @Mapping(target = "umf", source = "descUnidad"),
	        @Mapping(target = "indHospitalNuevo", source = "indHospitalNuevo"),
	        @Mapping(target = "ubicacion", source = "descOoad"),
	        @Mapping(target = "descZona", source = "descZona"),
	        @Mapping(target = "direccion", source = "refDireccionUnidad"),
	        @Mapping(target = "refSueldoMensualBruto", source = "refSueldoMensualBruto"),
	        @Mapping(target = "refSueldoMensualNeto", source = "refSueldoMensualNeto"),
	        @Mapping(target = "descHorario", source = "descHorario"),
	        @Mapping(target = "numPlaza", expression = "java(entity.getNumPlaza() != null ? entity.getNumPlaza().toString() : null)"),
	        @Mapping(target = "clasificacion", source = "clasificacion"),
	        @Mapping(target = "descOoad", source = "descOoad"),
	        @Mapping(target = "cveZona", source = "cveZona"),

	        // Créditos
	        @Mapping(target = "creditos", expression = "java(contarCreditos(entity))"),

	        @Mapping(target = "refBonoDificilCobertura", source = "refBonoDificilCobertura"),
	        @Mapping(target = "indAccesoCredito", source = "indAccesoCredito"),
	        @Mapping(target = "refCredHipotecarioImporte", source = "refCredHipotecarioImporte"),
	        @Mapping(target = "descuentoQuincenalCreditoHipotecario", source = "refCredHipotecarioQuincenal"),
	        @Mapping(target = "refCredAutomotrizImporte", source = "refCredAutomotrizImporte"),
	        @Mapping(target = "descuentoQuincenalCreditoAutomotriz", source = "refCredAutomotrizQuincenal"),

	        @Mapping(target = "idEstatusPlaza", source = "estatusPlaza.idEstatusPlaza"),
	        @Mapping(target = "estatusPlaza", source = "estatusPlaza.desEstatusPlaza"),
	        @Mapping(target = "idConvocatoria", source = "idConvocatoria"),
	        @Mapping(target = "origenPlaza", source = "origenPlaza"),
	        @Mapping(target = "desObservaciones", source = "desObservaciones")
	    })
	    DetallePlazaDTO toDetallePlazaDTO(PlazaLayout entity);

	    PlazaLayoutCargaDTO toPlazaLayoutCargaDTO(PlazaLayout entity);

	    List<PlazaLayoutCargaDTO> toPlazaLayoutCargaDTOList(List<PlazaLayout> entities);

	    // =========================
	    // Métodos auxiliares
	    // =========================

	    default String formatIntegerToTwoDigits(Long number) {
	        if (number == null) return null;
	        return String.format("%02d", number);
	    }

	    default int contarCreditos(PlazaLayout entity) {
	        int count = 0;

	        if (entity.getRefCredHipotecarioImporte() != null
	                && entity.getRefCredHipotecarioImporte().compareTo(BigDecimal.ZERO) > 0) {
	            count++;
	        }

	        if (entity.getRefCredAutomotrizImporte() != null
	                && entity.getRefCredAutomotrizImporte().compareTo(BigDecimal.ZERO) > 0) {
	            count++;
	        }

	        return count;
	    }
}
