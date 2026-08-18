package mx.gob.imss.mscme.admonplazas.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import mx.gob.imss.mscme.admonplazas.models.response.PlazaLayoutCargaDTO;
import mx.gob.imss.mscme.admonplazas.models.response.RespuestaGenerica;

public interface CargaLayoutPlazaService {

    RespuestaGenerica<List<PlazaLayoutCargaDTO>> cargarLayoutPlaza(Long idConvocatoria, MultipartFile archivo,
            String token);

}
