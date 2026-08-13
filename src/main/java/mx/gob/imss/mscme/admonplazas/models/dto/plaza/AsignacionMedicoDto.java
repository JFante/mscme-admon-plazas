package mx.gob.imss.mscme.admonplazas.models.dto.plaza;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.gob.imss.mscme.admonplazas.models.entities.AsignacionMedico;
import mx.gob.imss.mscme.admonplazas.models.response.plaza.DetallePlazaDTO;

/**
 * DTO for {@link AsignacionMedico}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AsignacionMedicoDto implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -8971724750219785622L;
    private Long id;
	private TipoAsignacionDto idTipoAsignacion;
    private DetallePlazaDTO idPlazaLayout;
    private MotivoRechazoDto idMotivoRechazo;
    private SustitucionDto idSustitucion;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDateTime stpAsignacion;
}