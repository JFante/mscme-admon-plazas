package mx.gob.imss.mscme.admonplazas.services;

import java.util.List;

import mx.gob.imss.mscme.admonplazas.models.entities.BitacoraCargaPlaza;
import mx.gob.imss.mscme.admonplazas.models.entities.ControlCargaPlaza;
import mx.gob.imss.mscme.admonplazas.models.entities.Usuario;

public interface BitacoraService {

    /**
     *
     * @param usuario
     * @param evento
     * @param idTipoMovimientoBitacora
     * @param idModulo
     */
    void guardarBitacora(Usuario usuario, String evento, Long idTipoMovimientoBitacora, Long idModulo);

    /**
     *
     * @param controlCarga
     * @param fallos
     * @param usuario
     */
    void registrarFallosCargaPlaza(ControlCargaPlaza controlCarga, List<BitacoraCargaPlaza> fallos, Usuario usuario);

}
