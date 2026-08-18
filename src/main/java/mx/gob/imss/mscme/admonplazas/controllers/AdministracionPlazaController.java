package mx.gob.imss.mscme.admonplazas.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
import mx.gob.imss.mscme.admonplazas.models.request.ActualizarEstatusPlazaRequest;
import mx.gob.imss.mscme.admonplazas.models.request.PlazaRequest;
import mx.gob.imss.mscme.admonplazas.models.response.DetallePlazaDTO;
import mx.gob.imss.mscme.admonplazas.models.response.PlazaValidacionResponse;
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
            @Parameter(description = "Origen de plaza (opcional): MANUAL o LAYOUT") @RequestParam(required = false) String origenPlaza,
            @PageableDefault(size = 10, direction = Direction.ASC) final Pageable pageable) {
        return ResponseEntity.ok(administracionPlazasService.busquedaPlazasFiltro(cveOoad, numPlaza, origenPlaza, pageable));
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

    @Operation(summary = "Alta manual de plaza", description = "Registra una plaza manual. Valida duplicidad por convocatoria, OOAD y numero de plaza. Solo permite estatus Vacante o Etiquetada.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plaza registrada correctamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud invalida o plaza duplicada") })
    @PostMapping("/registrarPlaza")
    public ResponseEntity<RespuestaGenerica<DetallePlazaDTO>> registrarPlaza(
            @RequestBody PlazaRequest plazaRequest,
            @RequestHeader("Authorization") String authorizationHeader) {
        return ResponseEntity.ok(administracionPlazasService.crearPlaza(plazaRequest, obtenerToken(authorizationHeader)));
    }

    @Operation(summary = "Edicion de plaza", description = "Actualiza la informacion general de una plaza existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plaza actualizada correctamente"),
            @ApiResponse(responseCode = "500", description = "No se encontró la plaza con el id proporcionado") })
    @PutMapping("/actualizarPlaza/{idPlaza}")
    public ResponseEntity<RespuestaGenerica<DetallePlazaDTO>> actualizarPlaza(
            @Parameter(description = "Id de la plaza") @PathVariable Long idPlaza,
            @RequestBody PlazaRequest plazaRequest,
            @RequestHeader("Authorization") String authorizationHeader) {
        return ResponseEntity.ok(
                administracionPlazasService.actualizarPlaza(idPlaza, plazaRequest, obtenerToken(authorizationHeader)));
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

    @Operation(summary = "Actualización de estatus de plaza", description = "Actualiza el estatus de una plaza del layout a partir de su identificador usando metodo PATCH.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Actualización realizada correctamente"),
            @ApiResponse(responseCode = "500", description = "No se encontró la plaza con el id proporcionado") })
    @PutMapping("/actualizarEstatusPlaza")
    public ResponseEntity<RespuestaGenerica<Void>> actualizarEstatusPlaza(
            @RequestBody ActualizarEstatusPlazaRequest request,
            @RequestHeader("Authorization") String authorizationHeader) {
        return ResponseEntity.ok(administracionPlazasService.actualizarEstatusPlaza(request.getIdPlaza(),
                request.getIdEstatus(), request.getDesObservaciones(), obtenerToken(authorizationHeader)));
    }

    @Operation(summary = "Validación de plazas ocupadas", description = "Valida por convocatoria si existen plazas activas en estatus Ocupada. Si existen, el frontend no debe permitir iniciar el proceso.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Validación realizada correctamente"),
            @ApiResponse(responseCode = "400", description = "idConvocatoria no informado") })
    @GetMapping("/plazaValidacion")
    public ResponseEntity<RespuestaGenerica<PlazaValidacionResponse>> validarPlazasOcupadas(
            @Parameter(description = "Id de convocatoria") @RequestParam Long idConvocatoria) {
        return ResponseEntity.ok(administracionPlazasService.validarPlazasOcupadas(idConvocatoria));
    }

    private String obtenerToken(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Token de autorización inválido.");
        }
        return authorizationHeader.substring(7);
    }

}
