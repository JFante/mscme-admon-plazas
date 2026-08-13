package mx.gob.imss.mscme.admonplazas.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * Mapeo de la tabla de catálogo CMEC_DOCUMENTO_VERIFICACION.
 * Representa los tipos de documentos de verificación.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "CMEC_DOCUMENTO_VERIFICACION")
public class DocumentoVerificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DOCUMENTO_VERIFICACION")
    private Long idDocumentoVerificacion;

    @Column(name = "DES_DOCUMENTO_VERIFICACION", nullable = false)
    private String desDocumentoVerificacion;

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
