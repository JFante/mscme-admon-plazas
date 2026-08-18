package mx.gob.imss.mscme.admonplazas.enums;

public enum ModuloEnum {

    REGISTRO(1L), 
    CONVOCATORIA(2L),
    ADMINISTRACION_DE_PLAZAS(11L);

    

    private final Long id;

    ModuloEnum(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    // Método para obtener el enum a partir del ID
    public static ModuloEnum fromId(Long id) {
        for (ModuloEnum modulo : values()) {
            if (modulo.getId().equals(id)) {
                return modulo;
            }
        }
        throw new IllegalArgumentException("ID de módulo inválido: " + id);
    }
}
