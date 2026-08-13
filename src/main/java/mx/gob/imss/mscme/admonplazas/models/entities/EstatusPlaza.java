package mx.gob.imss.mscme.admonplazas.models.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "CMEC_ESTATUS_PLAZA")
@Getter
@Setter
@NoArgsConstructor
public class EstatusPlaza extends AuditoriaBase implements Serializable {


    private static final long serialVersionUID = -4684598016692924472L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ESTATUS_PLAZA")
    private Long idEstatusPlaza;

    @Column(name = "DES_ESTATUS_PLAZA")
    private String desEstatusPlaza;

}
