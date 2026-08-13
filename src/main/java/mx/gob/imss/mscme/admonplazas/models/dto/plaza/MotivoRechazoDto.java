package mx.gob.imss.mscme.admonplazas.models.dto.plaza;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link mx.gob.imss.mscme.admonplazas.models.entities.MotivoRechazo}
 */
@Value
public class MotivoRechazoDto implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -9014177334564745917L;
	Long id;
    String desMotivo;
}