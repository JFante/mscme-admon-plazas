package mx.gob.imss.mscme.admonplazas.models.request.plaza;

import java.io.Serializable;

import lombok.Data;

@Data
public class AsignacionCedulaExternoRequest implements Serializable{

    /**
	 * 
	 */
    private static final long serialVersionUID = 2645226178631663550L;
	private String fecha;
    private String folio;
    private String desconcentrada;
    private String candidato;
    private String egresado;
    private String modalidad;
    private String especialidad;
    private String plaza;
    private String contratacion;
    private String marca;
    private String descripcion;
    private String categoria;
    private String unidad;
    private String area;
    private String zona;
    private String turno;
    private String mesa;

}
