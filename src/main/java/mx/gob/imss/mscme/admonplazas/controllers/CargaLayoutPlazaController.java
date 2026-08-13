package mx.gob.imss.mscme.admonplazas.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import mx.gob.imss.mscme.admonplazas.services.CargaLayoutPlazaService;

@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Carga de layout de plaza", description = "Endpoints para la carga del layout de plaza")
@RestController
@RequestMapping("/v1/cargaLayoutPlaza")
@RequiredArgsConstructor
public class CargaLayoutPlazaController {

	private final CargaLayoutPlazaService cargaLayoutPlazaService;

}
