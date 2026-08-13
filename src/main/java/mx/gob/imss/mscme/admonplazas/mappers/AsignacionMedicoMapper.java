package mx.gob.imss.mscme.admonplazas.mappers;

import org.mapstruct.*;

import mx.gob.imss.mscme.admonplazas.models.dto.plaza.AsignacionMedicoDto;
import mx.gob.imss.mscme.admonplazas.models.entities.AsignacionMedico;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {
                TipoAsignacionMapper.class,
                MotivoRechazoMapper.class,
                SustitucionMapper.class,
                PlazaLayoutMapper.class
        }
)
public interface AsignacionMedicoMapper {
    AsignacionMedico toEntity(AsignacionMedicoDto asignacionMedicoDto);

    AsignacionMedicoDto toDto(AsignacionMedico asignacionMedico);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    AsignacionMedico partialUpdate(AsignacionMedicoDto asignacionMedicoDto, @MappingTarget AsignacionMedico asignacionMedico);
}