package mx.gob.imss.mscme.admonplazas.models.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "CMET_BITACORA_ADMON_PLAZA")
public class BitacoraAdmonPlaza extends AuditoriaBase implements Serializable {

    private static final long serialVersionUID = -3542258774208801017L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_BITACORA_ADMON_PLAZA")
    private Long idBitacoraAdmonPlaza;

    @Column(name = "ID_PLAZA_LAYOUT", nullable = false)
    private Long idPlazaLayout;

    @Column(name = "ID_TIPO_MOVIMIENTO_BITACORA", nullable = false)
    private Long idTipoMovimientoBitacora;

    @Column(name = "ID_ESTATUS_ANTERIOR")
    private Long idEstatusAnterior;

    @Column(name = "ID_ESTATUS_NUEVO")
    private Long idEstatusNuevo;

    @Column(name = "DES_OBSERVACIONES", length = 500)
    private String desObservaciones;

    @Lob
    @Column(name = "DES_VALOR_ANTERIOR")
    private String desValorAnterior;

    @Lob
    @Column(name = "DES_VALOR_NUEVO")
    private String desValorNuevo;
}
