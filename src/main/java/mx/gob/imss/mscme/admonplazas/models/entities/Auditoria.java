package mx.gob.imss.mscme.admonplazas.models.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "CMET_AUDITORIA")
public class Auditoria  extends AuditoriaBase implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 7684283627375436248L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_AUDITORIA", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USUARIO")
    private Usuario idUsuario;

    @Size(max = 255)
    @NotNull
    @Column(name = "REF_EVENTO")
    private String refEvento;

    @Column(name = "ID_TIPO_MOVIMIENTO_BITACORA")
    private Long idTipoMovimientoBitacora;

    @Column(name = "ID_MODULO")
    private Long idModulo;


}
