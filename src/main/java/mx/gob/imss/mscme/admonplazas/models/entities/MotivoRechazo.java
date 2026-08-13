package mx.gob.imss.mscme.admonplazas.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "CMEC_MOTIVO_RECHAZO")
public class MotivoRechazo implements Serializable {
    private static final long serialVersionUID = 1513781451482191624L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_MOTIVO_RECHAZO", nullable = false)
    private Long id;

    @Column(name = "DES_MOTIVO", nullable = false, length = 100)
    private String desMotivo;


}