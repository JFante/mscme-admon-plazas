
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
	        @Mapping(target = "porcAltoCostoVida", source = "refAltoCostoVida"),
	        @Mapping(target = "especialidad", source = "descAreaResponsabilidad"),
	        @Mapping(target = "categoria", source = "descCategoria"),
	        @Mapping(target = "regimen", source = "desRegimen"),
	        @Mapping(target = "turno", source = "descTurno"),
	        @Mapping(target = "tipoPlaza", source = "descTipoPlaza"),
	        @Mapping(target = "marcaOcupacion", source = "descMarcaOcupacion"),
	        @Mapping(target = "umf", source = "descUnidad"),
	        @Mapping(target = "nuevoHospital", source = "indHospitalNuevo"),
	        @Mapping(target = "ubicacion", source = "descOoad"),
	        @Mapping(target = "zona", source = "descZona"),
	        @Mapping(target = "direccion", source = "refDireccionUnidad"),
	        @Mapping(target = "sueldoMensualBruto", source = "refSueldoMensualBruto"),
	        @Mapping(target = "sueldoMensualNeto", source = "refSueldoMensualNeto"),
	        @Mapping(target = "horario", source = "descHorario"),
	        @Mapping(target = "numPlaza", expression = "java(entity.getNumPlaza() != null ? entity.getNumPlaza().toString() : null)"),
	        @Mapping(target = "clasificacion", source = "clasificacion"),
	        @Mapping(target = "ooad", source = "descOoad"),
	        @Mapping(target = "cveZona", source = "cveZona"),

	        // Créditos
	        @Mapping(target = "creditos", expression = "java(contarCreditos(entity))"),

	        @Mapping(target = "bonoDificilCobertura", source = "refBonoDificilCobertura"),
	        @Mapping(target = "accesoCredito", expression = "java(entity.getIndAccesoCredito() != null && entity.getIndAccesoCredito().equals(1))"),
	        @Mapping(target = "creditoHipotecario", source = "refCredHipotecarioImporte"),
	        @Mapping(target = "descuentoQuincenalCreditoHipotecario", source = "refCredHipotecarioQuincenal"),
	        @Mapping(target = "creditoAutomotriz", source = "refCredAutomotrizImporte"),
	        @Mapping(target = "descuentoQuincenalCreditoAutomotriz", source = "refCredAutomotrizQuincenal"),

	        @Mapping(target = "idEstatusPlaza", source = "estatusPlaza.idEstatusPlaza"),
	        @Mapping(target = "estatusPlaza", source = "estatusPlaza.desEstatusPlaza"),
	        @Mapping(target = "idConvocatoria", source = "idConvocatoria"),
	        @Mapping(target = "origenPlaza", source = "origenPlaza"),
	        @Mapping(target = "observaciones", source = "desObservaciones")
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
