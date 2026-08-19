package mx.gob.imss.mscme.admonplazas.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import mx.gob.imss.mscme.admonplazas.models.entities.ControlCargaPlaza;
import mx.gob.imss.mscme.admonplazas.models.response.ControlCargaPlazaDTO;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ControlCargaPlazaMapper {

    ControlCargaPlazaMapper INSTANCE = Mappers.getMapper(ControlCargaPlazaMapper.class);

    @Mappings({
        @Mapping(target = "id", source = "id"),
        @Mapping(target = "idConvocatoria", source = "idConvocatoria.idConvocatoria"),
        @Mapping(target = "idEstatusCarga", source = "idEstatusCarga.id"),
        @Mapping(target = "desEstatusCarga", source = "idEstatusCarga.desEstatus"),
        @Mapping(target = "nomArchivo", source = "nomArchivo"),
        @Mapping(target = "numTotalRegistros", source = "numTotalRegistros"),
        @Mapping(target = "numRegistrosValidos", source = "numRegistrosValidos"),
        @Mapping(target = "numRegistrosRechazados", source = "numRegistrosRechazados"),
        @Mapping(target = "numPlazasOfertadas", source = "numPlazasOfertadas"),
        @Mapping(target = "numPlazasConCredito", source = "numPlazasConCredito"),
        @Mapping(target = "stpInicioCarga", source = "stpInicioCarga"),
        @Mapping(target = "stpFinCarga", source = "stpFinCarga"),
        @Mapping(target = "refMensajeResultado", source = "refMensajeResultado")
    })
    ControlCargaPlazaDTO toControlCargaPlazaDTO(ControlCargaPlaza entity);

}
