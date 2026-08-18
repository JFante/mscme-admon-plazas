package mx.gob.imss.mscme.admonplazas.models.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ActualizarEstatusPlazaRequest {

    private Long idPlaza;

    private Long idEstatus;

    private String desObservaciones;
}
