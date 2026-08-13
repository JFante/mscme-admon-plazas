package mx.gob.imss.mscme.admonplazas.enums;

public enum TipoAsignacionPlazaEnum {
	
	ORDINARIO(1L, "PLAZA ORDINARIA"),
    COPLAMAR(2L, "PLAZA COPLAMAR"),
    SUSTITUCION_08(3L, "SUSTITUCIÓN 08"),
    CAMBIO_DE_RAMA(4L, "CAMBIO DE RAMA"),
    RECHAZO_DE_OFERTA(5L, "RECHAZO DE OFERTA");

	private final Long codigo;
	private final String descripcion;

	TipoAsignacionPlazaEnum(Long codigo, String descripcion) {
		this.codigo = codigo;
		this.descripcion = descripcion;
	}

	public Long getCodigo() {
		return codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}


}
