package mx.gob.imss.mscme.admonplazas.enums;

public enum TipoPlazaMedicoEnum {

	NO_NUEVO(0, "Hospital no nuevo"),
	NUEVO(1, "Hospital nuevo"),
	BIENESTAR(2, "IMSS-Bienestar");

	private final Integer id;
	private final String descripcion;

	TipoPlazaMedicoEnum(Integer id, String descripcion) {
		this.id = id;
		this.descripcion = descripcion;
	}

	public Integer getId() {
		return id;
	}

	public String getDescripcion() {
		return descripcion;
	}

}
