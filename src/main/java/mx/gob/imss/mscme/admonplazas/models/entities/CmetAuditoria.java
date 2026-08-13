package mx.gob.imss.mscme.admonplazas.models.entities;
import java.io.Serializable;
import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "CMET_AUDITORIA")
public class CmetAuditoria implements Serializable{
    

    private static final long serialVersionUID = 4039443856877551825L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_AUDITORIA")
    private Long idAuditoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USUARIO")
    private Usuario usuario;

    @Column(name = "REF_EVENTO", nullable = false)
    private String refEvento;

    @Column(name = "STP_EVENTO", insertable = false, updatable = false)
    private Timestamp stpEvento;

    @Column(name = "IND_ACTIVO")
    private Long indActivo;

    @Column(name = "ID_USUARIO_ALTA")
    private Long idUsuarioAlta;

    @Column(name = "STP_ALTA_REGISTRO")
    private Timestamp stpAltaRegistro;

    @Column(name = "ID_USUARIO_MODIFICA")
    private Long idUsuarioModifica;

    @Column(name = "STP_MODIFICA_REGISTRO", insertable = false, updatable = false)
    private Timestamp stpModificaRegistro;

    @Column(name = "ID_USUARIO_BAJA")
    private Long idUsuarioBaja;

    @Column(name = "STP_BAJA_REGISTRO", insertable = false, updatable = false)
    private Timestamp stpBajaRegistro;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TIPO_MOVIMIENTO_BITACORA")
    private TipoMovimientoBitacora tipoMovimientoBitacora;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_MODULO")
    private Modulo modulo;

}