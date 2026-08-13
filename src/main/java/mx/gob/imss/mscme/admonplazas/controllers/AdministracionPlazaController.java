package mx.gob.imss.mscme.admonplazas.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import mx.gob.imss.mscme.admonplazas.models.entities.PlazaLayout;
import mx.gob.imss.mscme.admonplazas.services.AdministracionPlazasService;

@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Administración de plazas", description = "Endpoints para la búsqueda y administración de las plazas del layout")
@RestController
@RequestMapping("/v1/administracionPlazas")
@RequiredArgsConstructor
public class AdministracionPlazaController {

	private final AdministracionPlazasService administracionPlazasService;

    @GetMapping("/prueba")
    public String prueba() {
        return "hola";
    }

    @Operation(summary = "Busqueda de plazas por filtro", description = "Busqueda paginada de plazas del layout, filtrando de forma dinamica por clave de OOAD y/o numero de plaza; ambos filtros son opcionales.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busqueda realizada correctamente") })
    @GetMapping("/busquedaPlazasFiltro")
    public Page<PlazaLayout> busquedaPlazasFiltro(
            @Parameter(description = "Clave de OOAD (opcional)") @RequestParam(required = false) Long cveOoad,
            @Parameter(description = "Numero de plaza (opcional)") @RequestParam(required = false) Integer numPlaza,
            Pageable pageable) {
        return administracionPlazasService.busquedaPlazasFiltro(cveOoad, numPlaza, pageable);
    }

}
