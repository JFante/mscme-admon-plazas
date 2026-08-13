package mx.gob.imss.mscme.admonplazas.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "CMET_PARTICIPACION_CONV")
public class CmetParticipacionConvEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PARTICIPACION", nullable = false)
    private Long idParticipacion;

    @Column(name = "ID_USUARIO", nullable = false)
    private Long idUsuario;

    @Column(name = "ID_CONVOCATORIA", nullable = false)
    private Long idConvocatoria;

    @Column(name = "IND_ACTIVO")
    private Integer indActivo;

    @Column(name = "ID_USUARIO_ALTA")
    private Long idUsuarioAlta;

    @Column(name = "STP_ALTA_REGISTRO")
    private LocalDateTime stpAltaRegistro;

    @Column(name = "ID_USUARIO_MODIFICA")
    private Long idUsuarioModifica;

    @Column(name = "STP_MODIFICA_REGISTRO")
    private LocalDateTime stpModificaRegistro;

    @Column(name = "ID_USUARIO_BAJA")
    private Long idUsuarioBaja;

    @Column(name = "STP_BAJA_REGISTRO")
    private LocalDateTime stpBajaRegistro;

    @Column(name = "DES_FOLIO_ME", length = 20)
    private String desFolioMe;

}
