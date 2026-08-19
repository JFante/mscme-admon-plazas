package mx.gob.imss.mscme.admonplazas.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import mx.gob.imss.mscme.admonplazas.enums.EstatusCargaEnum;
import mx.gob.imss.mscme.admonplazas.models.entities.ControlCargaPlaza;
import mx.gob.imss.mscme.admonplazas.models.entities.Convocatoria;
import mx.gob.imss.mscme.admonplazas.models.entities.PlazaLayout;
import mx.gob.imss.mscme.admonplazas.models.entities.Usuario;

/**
 * Persiste el control de carga (inicio/fin) en una transacción propia (REQUIRES_NEW),
 * independiente de la transacción de la carga de layout, para que el registro de
 * auditoría sobreviva aunque la carga falle y haga rollback.
 */
public interface ControlCargaPlazaService {

    ControlCargaPlaza guardarInicioControlCarga(Convocatoria convocatoria, MultipartFile archivo,
            LocalDateTime fechaHoraInicioProceso, Usuario usuarioAdmon);

    void guardarFinControlCarga(ControlCargaPlaza controlCarga, EstatusCargaEnum estatusFinal,
            List<PlazaLayout> plazas, String mensajeResultado, Usuario usuarioAdmon);

}
