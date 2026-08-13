/**
 * 
 */
package mx.gob.imss.mscme.admonplazas.enums;

/**
 * 
 */
public enum TipoMovimientoMovimientoEnum {

    CONSULTA(1L, "CONSULTA"),
    ACTUALIZACION(2L, "ACTUALIZACIÓN"),
    ELIMINACION(3L, "ELIMINACIÓN"),
    CREACION(4L, "CREACIÓN"),
    CAMBIO_DE_ESTATUS(5L, "CAMBIO DE ESTATUS"),
    RECEPCION(6L, "RECEPCIÓN");

	
	private final Long id;
	private final String descripcion;

	TipoMovimientoMovimientoEnum(Long id, String descripcion) {
		this.id = id;
		this.descripcion = descripcion;
	}

	public Long getId() {
		return id;
	}

	public String getDescripcion() {
		return descripcion;
	}
	
}
