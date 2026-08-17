package mx.gob.imss.mscme.admonplazas.models.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlazaValidacionResponse {

    private Boolean puedeIniciarProceso;
    private Boolean existenPlazasOcupadas;
    private Long totalPlazasOcupadas;
}
