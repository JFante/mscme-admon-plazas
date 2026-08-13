package mx.gob.imss.mscme.admonplazas.mappers;

import org.mapstruct.*;

import mx.gob.imss.mscme.admonplazas.models.dto.plaza.TipoAsignacionDto;
import mx.gob.imss.mscme.admonplazas.models.entities.TipoAsignacion;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TipoAsignacionMapper {
    TipoAsignacion toEntity(TipoAsignacionDto tipoAsignacionDto);

    TipoAsignacionDto toDto(TipoAsignacion tipoAsignacion);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    TipoAsignacion partialUpdate(TipoAsignacionDto tipoAsignacionDto, @MappingTarget TipoAsignacion tipoAsignacion);
}