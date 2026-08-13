package mx.gob.imss.mscme.admonplazas.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import mx.gob.imss.mscme.admonplazas.models.entities.Usuario;
import mx.gob.imss.mscme.admonplazas.repository.UsuarioRepository;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // La entidad Usuario ya implementa UserDetails, por lo que podemos retornarla
        // directamente
        return usuarioRepository.findByRefEmailAndIndActivo(username, 1L)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el email: " + username));
    }

    public UserDetails loadUserById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El id del usuario no puede ser null");
        }
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isEmpty()) {
            throw new UsernameNotFoundException("Usuario no encontrado con el ID: " + id);
        }
        return usuario.get();
    }
}
