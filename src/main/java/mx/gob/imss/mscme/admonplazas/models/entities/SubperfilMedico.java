package mx.gob.imss.mscme.admonplazas.models.entities;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * Mapeo de la tabla de catálogo CMEC_SUBPERFIL_MEDICO.
 * Representa los subperfiles de médicos.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "CMEC_SUBPERFIL_MEDICO")
public class SubperfilMedico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_SUBPERFIL")
    private Long idSubperfil;

    @Column(name = "DES_SUBPERFIL", nullable = false)
    private String desSubperfil;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PERFIL")
    private PerfilMedico perfil;

    @Column(name = "IND_ACTIVO")
    private Long indActivo;



    @Column(name = "ID_USUARIO_ALTA")
    private Long idUsuarioAlta;

    @Column(name = "STP_ALTA_REGISTRO")
    private Timestamp stpAltaRegistro;

    @Column(name = "ID_USUARIO_MODIFICA")
    private Long idUsuarioModifica;

    @Column(name = "STP_MODIFICA_REGISTRO")
    private Timestamp stpModificaRegistro;

    @Column(name = "ID_USUARIO_BAJA")
    private Long idUsuarioBaja;

    @Column(name = "STP_BAJA_REGISTRO")
    private Timestamp stpBajaRegistro;
}
