package mx.gob.imss.mscme.admonplazas.models.entities;


import jakarta.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "CMEC_PAIS")
public class Pais  implements Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 4312760998651506116L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PAIS")
    private Long idPais;
    
    @Column(name = "CVE_PAIS", nullable = false, length = 5)
    private String cvePais;
    
    @Column(name = "DES_PAIS", nullable = false, length = 100)
    private String desPais;

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