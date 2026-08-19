package mx.gob.imss.mscme.admonplazas.models.response;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ControlCargaPlazaDTO implements Serializable {

    private static final long serialVersionUID = -5926431765480996830L;
	private Long id;
    private Long idConvocatoria;
    private Long idEstatusCarga;
    private String desEstatusCarga;
    private String nomArchivo;
    private Long numTotalRegistros;
    private Long numRegistrosValidos;
    private Long numRegistrosRechazados;
    private Long numPlazasOfertadas;
    private Long numPlazasConCredito;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime stpInicioCarga;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime stpFinCarga;
    
    private String refMensajeResultado;

}
