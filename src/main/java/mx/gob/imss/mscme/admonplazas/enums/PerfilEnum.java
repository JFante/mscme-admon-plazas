package mx.gob.imss.mscme.admonplazas.enums;

public enum PerfilEnum {

    ADMINISTRADOR(1L, "ADMINISTRADOR"),
    RESIDENTE_IMSS(2L, "RESIDENTE IMSS"),
    MEDICO_EXTERNO(3L, "MÉDICO EXTERNO"),
    VERIFICADOR_INTERNO(4L, "VERIFICADOR INTERNO"),
    VERIFICADOR_EXTERNO(5L, "VERIFICADOR EXTERNO"),
    MEDICO_EGRESADO_OTRA_INSTITUCION(6L, "MÉDICO EGRESADO OTRA INSTITUCIÓN");

    private final Long id;
    private final String descripcion;

    PerfilEnum(Long id, String descripcion) {
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
