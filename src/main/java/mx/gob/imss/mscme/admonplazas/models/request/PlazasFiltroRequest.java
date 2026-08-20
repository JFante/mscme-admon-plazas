package mx.gob.imss.mscme.admonplazas.models.request;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PlazasFiltroRequest {

    @Parameter(description = "Clave de OOAD (opcional)")
    private Long cveOoad;

    @Parameter(description = "Numero de plaza (opcional)")
    private Integer numPlaza;

    @Parameter(description = "Origen de plaza (opcional): MANUAL o LAYOUT")
    private String origenPlaza;

    @Parameter(description = "Id de convocatoria (opcional). Si no se envia, se calcula la convocatoria actual")
    private Long idConvocatoria;

    @Parameter(description = "Clave de zona (opcional)")
    private Integer cveZona;

    @Parameter(description = "Clave de categoria (opcional)")
    private String cveCategoria;

    @Parameter(description = "Clave de especialidad (opcional)")
    private String cveEspecialidad;

    @Parameter(description = "Clave de unidad (opcional)")
    private String cveUnidad;

}
