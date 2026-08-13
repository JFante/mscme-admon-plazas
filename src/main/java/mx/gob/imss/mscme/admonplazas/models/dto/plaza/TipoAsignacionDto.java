package mx.gob.imss.mscme.admonplazas.models.dto.plaza;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.gob.imss.mscme.admonplazas.models.entities.TipoAsignacion;

import java.io.Serializable;

/**
 * DTO for {@link TipoAsignacion}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoAsignacionDto implements Serializable {
    private Long id;
    private String desTipoAsignacion;
}