package mx.gob.imss.mscme.admonplazas.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mx.gob.imss.mscme.admonplazas.models.response.ControlCargaPlazaDTO;
import mx.gob.imss.mscme.admonplazas.models.response.PlazaLayoutCargaDTO;
import mx.gob.imss.mscme.admonplazas.models.response.RespuestaGenerica;
import mx.gob.imss.mscme.admonplazas.services.CargaLayoutPlazaService;

@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Carga de layout de plaza", description = "Endpoints para la carga del layout de plaza")
@RestController
@RequestMapping("/v1/cargaLayoutPlaza")
@Log4j2
@RequiredArgsConstructor
public class CargaLayoutPlazaController {

	private final CargaLayoutPlazaService cargaLayoutPlazaService;

	@Operation(summary = "Carga de layout de plaza", description = "Recibe el identificador de la convocatoria y el archivo Excel con el layout de plazas a cargar.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Archivo recibido correctamente"),
			@ApiResponse(responseCode = "400", description = "El archivo o la convocatoria proporcionados no son válidos") })
	@PostMapping(value = "/cargarArchivo", consumes = "multipart/form-data")
	public ResponseEntity<RespuestaGenerica<List<PlazaLayoutCargaDTO>>> cargarLayoutPlaza(
			@Parameter(description = "Id de la convocatoria") @RequestParam Long idConvocatoria,
			@Parameter(description = "Archivo Excel con el layout de plazas") @RequestParam MultipartFile archivo,
			@RequestHeader("Authorization") String authorizationHeader) {
		log.info("cargarLayoutPlaza");
		String token = authorizationHeader.substring(7);
		return ResponseEntity.ok(cargaLayoutPlazaService.cargarLayoutPlaza(idConvocatoria, archivo, token));
	}

	@Operation(summary = "Última carga de layout por convocatoria", description = "Obtiene el registro de control de la última carga de layout de plazas realizada para la convocatoria proporcionada.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Consulta realizada correctamente") })
	@GetMapping(value = "/ultimaCarga")
	public ResponseEntity<RespuestaGenerica<ControlCargaPlazaDTO>> obtenerUltimaCargaPorConvocatoria(
			@Parameter(description = "Id de la convocatoria") @RequestParam Long idConvocatoria) {
		log.info("obtenerUltimaCargaPorConvocatoria {}", idConvocatoria);
		return ResponseEntity.ok(cargaLayoutPlazaService.obtenerUltimaCargaPorConvocatoria(idConvocatoria));
	}

}
