package mx.gob.imss.mscme.admonplazas.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * Mapeo de la tabla de catálogo CMEC_PERFIL_MEDICO.
 * Representa los perfiles de médicos.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "CMEC_PERFIL_MEDICO")
public class PerfilMedico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PERFIL")
    private Long idPerfil;

    @Column(name = "DES_PERFIL", nullable = false)
    private String desPerfil;

    @Column(name = "IND_ACTIVO")
    private Long indActivo;

    @Column(name = "IND_PERFIL_INTERNO")
    private Long indPerfilInterno;

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
