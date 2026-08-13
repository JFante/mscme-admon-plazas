package mx.gob.imss.mscme.admonplazas.models.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "CMET_ASIGNACION_MEDICO")
public class AsignacionMedico extends AuditoriaBase implements Serializable {
	
    private static final long serialVersionUID = -6893802264814670877L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ASIGNACION", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_PARTICIPACION", nullable = false)
    private CmetParticipacionConvEntity idParticipacion;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_TIPO_ASIGNACION", nullable = false)
    private TipoAsignacion idTipoAsignacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PLAZA_LAYOUT")
    private PlazaLayout idPlazaLayout;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_SUSTITUCION")
    private Sustitucion idSustitucion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_MOTIVO_RECHAZO")
    private MotivoRechazo idMotivoRechazo;

    @Column(name = "STP_ASIGNACION")
    private LocalDateTime stpAsignacion;


}