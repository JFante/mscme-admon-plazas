package mx.gob.imss.mscme.admonplazas.enums;

public enum RegimenPlazaEnum {

    ORDINARIO(1L, "ORDINARIO"),
    COPLAMAR(2L, "COPLAMAR");

    private final Long id;
    private final String descripcion;

    RegimenPlazaEnum(Long id, String descripcion) {
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
