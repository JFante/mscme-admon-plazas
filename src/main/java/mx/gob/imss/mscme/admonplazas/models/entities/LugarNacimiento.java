package mx.gob.imss.mscme.admonplazas.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.sql.Timestamp;


@Entity
@Table(name = "CMEC_LUGAR_NACIMIENTO")
@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
public class LugarNacimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_LUGAR_NACIMIENTO")
    private Long idLugarNacimiento;

    @Column(name = "DES_LUGAR_NACIMIENTO", nullable = false, length = 50)
    private String desLugarNacimiento;

    @Column(name = "IND_ACTIVO", precision = 1)
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