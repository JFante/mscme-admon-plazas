package mx.gob.imss.mscme.admonplazas.enums;

public enum EstatusPlazaEnum {
	
	
    VACANTE(1L, "Vacante"),
    OCUPADA(2L, "Ocupada"),
    ETIQUETADA(3L, "Etiquetada");

    private final Long id;
    private final String descripcion;

    EstatusPlazaEnum(Long id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public Long getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public static EstatusPlazaEnum fromId(Long id) {
        if (id == null) {
            return null;
        }
        for (EstatusPlazaEnum e : values()) {
            if (e.id.equals(id)) {
                return e;
            }
        }
        return null;
    }

}
