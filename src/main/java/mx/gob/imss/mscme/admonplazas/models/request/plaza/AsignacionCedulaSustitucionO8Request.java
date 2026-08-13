package mx.gob.imss.mscme.admonplazas.models.request.plaza;

import java.io.Serializable;

import lombok.Data;

@Data
public class AsignacionCedulaSustitucionO8Request  implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -857542051568350636L;
    private String folio;
    private String desconcentrada;
    private String candidato;
    private String folioEgresado;
    private String egresado;
    private String especialidad;
    private String localidad;
    private String mesa;
    
    //interno
    private String matricula;
    private String caracter;
    


    
}
