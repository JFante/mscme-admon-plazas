package mx.gob.imss.mscme.admonplazas.mappers;

import org.mapstruct.*;

import mx.gob.imss.mscme.admonplazas.models.dto.plaza.MotivoRechazoDto;
import mx.gob.imss.mscme.admonplazas.models.entities.MotivoRechazo;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface MotivoRechazoMapper {
    MotivoRechazo toEntity(MotivoRechazoDto motivoRechazoDto);

    MotivoRechazoDto toDto(MotivoRechazo motivoRechazo);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    MotivoRechazo partialUpdate(MotivoRechazoDto motivoRechazoDto, @MappingTarget MotivoRechazo motivoRechazo);
}