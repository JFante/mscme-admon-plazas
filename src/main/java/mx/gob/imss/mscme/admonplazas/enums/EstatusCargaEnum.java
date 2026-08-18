package mx.gob.imss.mscme.admonplazas.enums;

public enum EstatusCargaEnum {

    EN_PROCESO(1L, "En proceso"),
    FINALIZADO(2L, "Finalizado"),
    INTERRUMPIDO(3L, "Interrumpido");

    private final Long id;
    private final String descripcion;

    EstatusCargaEnum(Long id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public Long getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public static EstatusCargaEnum fromId(Long id) {
        if (id == null) {
            return null;
        }
        for (EstatusCargaEnum e : values()) {
            if (e.id.equals(id)) {
                return e;
            }
        }
        return null;
    }

}
