package mx.gob.imss.mscme.admonplazas.models.dto.plaza;

import java.io.Serializable;

import lombok.Value;

/**
 * DTO for {@link mx.gob.imss.mscme.admonplazas.models.entities.Sustitucion}
 */
@Value
public class SustitucionDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3101872414712767080L;
	private Long id;
	private String cveOoad;
	private String desOoad;
	private String cveZona;
	private String desZona;
	private String cveEspecialidad;
	private String desEspecialidad;
}