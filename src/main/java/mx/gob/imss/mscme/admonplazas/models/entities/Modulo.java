package mx.gob.imss.mscme.admonplazas.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "CMEC_MODULO")
public class Modulo implements Serializable {
    private static final long serialVersionUID = 5289370833810458401L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_MODULO", nullable = false)
    private Long idModulo;

    @Column(name = "DES_MODULO", nullable = false, length = 200)
    private String desModulo;

    @ColumnDefault("1")
    @Column(name = "IND_ACTIVO")
    private Boolean indActivo;

}