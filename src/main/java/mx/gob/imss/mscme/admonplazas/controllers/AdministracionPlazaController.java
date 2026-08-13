package mx.gob.imss.mscme.admonplazas.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
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
import mx.gob.imss.mscme.admonplazas.models.response.DetallePlazaDTO;
import mx.gob.imss.mscme.admonplazas.models.response.RespuestaGenerica;
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
    public ResponseEntity<RespuestaGenerica<Page<DetallePlazaDTO>>> busquedaPlazasFiltro(
            @Parameter(description = "Clave de OOAD (opcional)") @RequestParam(required = false) Long cveOoad,
            @Parameter(description = "Numero de plaza (opcional)") @RequestParam(required = false) Integer numPlaza,
            @PageableDefault(size = 10, direction = Direction.ASC) final Pageable pageable) {
        return ResponseEntity.ok(administracionPlazasService.busquedaPlazasFiltro(cveOoad, numPlaza, pageable));
    }

    @Operation(summary = "Busqueda de detalle de plaza por id", description = "Obtiene el detalle de una plaza del layout a partir de su identificador (idPlaza).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta realizada correctamente"),
            @ApiResponse(responseCode = "500", description = "No se encontró la plaza con el id proporcionado") })
    @GetMapping("/buscarDetallePlaza/{idPlaza}")
    public ResponseEntity<RespuestaGenerica<DetallePlazaDTO>> buscarDetallePlaza(
            @Parameter(description = "Id de la plaza") @PathVariable Long idPlaza) {
        return ResponseEntity.ok(administracionPlazasService.buscarDetallePlaza(idPlaza));
    }

    @Operation(summary = "Baja lógica de plaza", description = "Realiza la baja lógica de una plaza del layout a partir de su identificador (idPlaza).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Baja realizada correctamente"),
            @ApiResponse(responseCode = "500", description = "No se encontró la plaza con el id proporcionado") })
    @DeleteMapping("/eliminarPlaza/{idPlaza}")
    public ResponseEntity<RespuestaGenerica<Void>> eliminarPlaza(
            @Parameter(description = "Id de la plaza") @PathVariable Long idPlaza,
            @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        return ResponseEntity.ok(administracionPlazasService.eliminarPlaza(idPlaza, token));
    }

}
