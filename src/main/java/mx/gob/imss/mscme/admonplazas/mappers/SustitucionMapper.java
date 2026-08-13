package mx.gob.imss.mscme.admonplazas.mappers;

import org.mapstruct.*;

import mx.gob.imss.mscme.admonplazas.models.dto.plaza.SustitucionDto;
import mx.gob.imss.mscme.admonplazas.models.entities.Sustitucion;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SustitucionMapper {
    Sustitucion toEntity(SustitucionDto sustitucionDto);

    SustitucionDto toDto(Sustitucion sustitucion);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Sustitucion partialUpdate(SustitucionDto sustitucionDto, @MappingTarget Sustitucion sustitucion);
}