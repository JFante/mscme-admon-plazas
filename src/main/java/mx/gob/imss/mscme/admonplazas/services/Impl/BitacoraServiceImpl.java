package mx.gob.imss.mscme.admonplazas.services.Impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import mx.gob.imss.mscme.admonplazas.models.entities.Auditoria;
import mx.gob.imss.mscme.admonplazas.models.entities.BitacoraCargaPlaza;
import mx.gob.imss.mscme.admonplazas.models.entities.ControlCargaPlaza;
import mx.gob.imss.mscme.admonplazas.models.entities.Usuario;
import mx.gob.imss.mscme.admonplazas.repository.AuditoriaRepository;
import mx.gob.imss.mscme.admonplazas.repository.BitacoraCargaPlazaRepository;
import mx.gob.imss.mscme.admonplazas.services.BitacoraService;
import mx.gob.imss.mscme.admonplazas.utils.Constantes;

@Service
@RequiredArgsConstructor
public class BitacoraServiceImpl implements BitacoraService {

    private final AuditoriaRepository auditoriaRepository;
    private final BitacoraCargaPlazaRepository bitacoraCargaPlazaRepository;

    @Override
    public void guardarBitacora(Usuario usuario, String evento, Long idTipoMovimientoBitacora, Long idModulo) {
        Auditoria auditoria = new Auditoria();
        auditoria.setIdUsuario(usuario);
        auditoria.setIdUsuarioAlta(usuario.getIdUsuario());
        auditoria.setRefEvento(evento);
        auditoria.setIdTipoMovimientoBitacora(idTipoMovimientoBitacora);
        auditoria.setIdModulo(idModulo);
        auditoria.setStpAltaRegistro(LocalDateTime.now());
        auditoria.setIndActivo(Constantes.ESTADO_ACTIVO);
        auditoriaRepository.save(auditoria);
    }

    @Override
    public void registrarFallosCargaPlaza(ControlCargaPlaza controlCarga, List<BitacoraCargaPlaza> fallos, Usuario usuario) {
        if (fallos == null || fallos.isEmpty()) {
            return;
        }
        LocalDateTime fechaRegistro = LocalDateTime.now();
        for (BitacoraCargaPlaza fallo : fallos) {
            fallo.setIdControlCargaPlaza(controlCarga);
            fallo.setIndActivo(Constantes.ESTADO_ACTIVO);
            fallo.setIdUsuarioAlta(usuario.getIdUsuario());
            fallo.setStpAltaRegistro(fechaRegistro);
        }
        bitacoraCargaPlazaRepository.saveAll(fallos);
    }

}
