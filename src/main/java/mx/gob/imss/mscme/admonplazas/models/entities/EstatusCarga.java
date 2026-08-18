package mx.gob.imss.mscme.admonplazas.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "CMEC_ESTATUS_CARGA")
public class EstatusCarga extends AuditoriaBase implements Serializable {
    @Serial
    private static final long serialVersionUID = -6909940919699126555L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ESTATUS_CARGA", nullable = false)
    private Long id;

    @Column(name = "DES_ESTATUS", nullable = false, length = 100)
    private String desEstatus;

}