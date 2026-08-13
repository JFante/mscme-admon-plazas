package mx.gob.imss.mscme.admonplazas.models.entities;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections; 

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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

@Data
@Entity
@Table(name = "CMET_USUARIO")
public class Usuario implements UserDetails {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3042855657073320740L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USUARIO")
    private Long idUsuario;

    @Column(name = "REF_EMAIL", nullable = false, unique = true)
    private String refEmail;

    @Column(name = "REF_CONTRASENA_HASH", nullable = false)
    private String refContrasenaHash;

    @Column(name = "CVE_MATRICULA", unique = true)
    private String cveMatricula;

    @Column(name = "NOM_NOMBRE", nullable = false)
    private String nomNombre;

    @Column(name = "NOM_APELLIDO_PATERNO", nullable = false)
    private String nomApellidoPaterno;

    @Column(name = "NOM_APELLIDO_MATERNO")
    private String nomApellidoMaterno;

    @Column(name = "REF_CURP", unique = true)
    private String refCurp;

    @Column(name = "REF_RFC", unique = true)
    private String refRfc;

    @Column(name = "REF_NSS")
    private String refNss;

    @Column(name = "FEC_NACIMIENTO")
    private LocalDate fecNacimiento;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PAIS_NACIMIENTO")
    private Pais paisNacimiento;
    
    // Relación con la tabla CMEC_SEXO
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_SEXO")
    private Sexo sexo;

    // Relación con la tabla CMEC_LUGAR_NACIMIENTO
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_LUGAR_NACIMIENTO")
    private LugarNacimiento lugarNacimiento;

    // Relación con la tabla CMEC_ESTADO_CIVIL
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ESTADO_CIVIL")
    private EstadoCivil estadoCivil;

    @Column(name = "REF_PASAPORTE", unique = true)
    private String refPasaporte;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_PERFIL", nullable = false)
    private PerfilMedico perfil;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PAIS_EMISION_PASAPORTE")
    private Pais paisEmision;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_SUBPERFIL")
    private SubperfilMedico subperfil;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_DOCUMENTO")
    private DocumentoVerificacion documentoVerificacion;

    @Column(name = "IND_INFO_ASAMBLEA")
    private Long indInfoAsamblea;
    
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



/*     // Relaciones inversas
    @OneToMany(mappedBy = "usuario")
    private List<ParticipacionConvocatoria> participaciones;

    @OneToMany(mappedBy = "usuario")
    private List<PasswordResetToken> passwordResetTokens;

    @OneToMany(mappedBy = "usuario")
    private List<Auditoria> auditorias; */

    /* METODOS PARA USERDETAILS */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Asigna el rol del usuario como una autoridad.
        return Collections
                .singletonList(new SimpleGrantedAuthority("ROLE_" + this.perfil.getDesPerfil().toUpperCase()));
    }

    @Override
    public String getPassword() {
        return this.refContrasenaHash;
    }

    @Override
    public String getUsername() {
        return this.refEmail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getFullName() {
        String fullName = this.nomNombre + " " + this.nomApellidoPaterno;
        if (this.nomApellidoMaterno != null && !this.nomApellidoMaterno.isEmpty()) {
            fullName += " " + this.nomApellidoMaterno;
        }
        return fullName;
    }

}
