package mx.gob.imss.mscme.admonplazas.models.entities;

import java.io.Serial;
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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "CMET_CONTROL_CARGA_PLAZA")
public class ControlCargaPlaza extends AuditoriaBase implements Serializable {
    @Serial
    private static final long serialVersionUID = -1406538600574294500L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CONTROL_CARGA_PLAZA", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_CONVOCATORIA", nullable = false)
    private Convocatoria idConvocatoria;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_ESTATUS_CARGA", nullable = false)
    private EstatusCarga idEstatusCarga;

    @Column(name = "NOM_ARCHIVO")
    private String nomArchivo;

    @Column(name = "NUM_TOTAL_REGISTROS")
    private Long numTotalRegistros;

    @Column(name = "NUM_REGISTROS_VALIDOS")
    private Long numRegistrosValidos;

    @Column(name = "NUM_REGISTROS_RECHAZADOS")
    private Long numRegistrosRechazados;

    @Column(name = "NUM_PLAZAS_OFERTADAS")
    private Long numPlazasOfertadas;

    @Column(name = "NUM_PLAZAS_CON_CREDITO")
    private Long numPlazasConCredito;

    @Column(name = "STP_INICIO_CARGA")
    private LocalDateTime stpInicioCarga;

    @Column(name = "STP_FIN_CARGA")
    private LocalDateTime stpFinCarga;

    @Column(name = "REF_MENSAJE_RESULTADO")
    private String refMensajeResultado;

}