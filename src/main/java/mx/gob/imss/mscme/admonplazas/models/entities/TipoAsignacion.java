package mx.gob.imss.mscme.admonplazas.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "CMEC_TIPO_ASIGNACION")
public class TipoAsignacion implements Serializable {
    private static final long serialVersionUID = -254948815682792615L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TIPO_ASIGNACION", nullable = false)
    private Long id;

    @Column(name = "DES_TIPO_ASIGNACION", nullable = false, length = 50)
    private String desTipoAsignacion;


}