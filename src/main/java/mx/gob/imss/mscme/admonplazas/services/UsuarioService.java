package mx.gob.imss.mscme.admonplazas.services;

import java.util.Date;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import mx.gob.imss.mscme.admonplazas.models.entities.Usuario;
import mx.gob.imss.mscme.admonplazas.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;

    public UsuarioService(UsuarioRepository usuarioRepository, JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.jwtService = jwtService;
    }

    public Usuario obtenerUsuarioToken(String token) {
        Usuario usuario = null;
        try {
            // Decodifica el token para obtener el email
            String userEmailFromToken = jwtService.extractUsername(token);

            Claims claims = jwtService.extractAllClaims(token);

            // Verifica si el token ha expirado
            if (claims.getExpiration().before(new Date())) {
                throw new JwtException("El token ha expirado");
            }

            // Actualizar la contraseña del usuario
            usuario = usuarioRepository.findByRefEmailAndIndActivo(userEmailFromToken, 1L)
                    .orElseThrow(
                            () -> new IllegalStateException(
                                    "No se encontró el usuario"));

        } catch (JwtException e) {
            // Captura las excepciones de JWT (firma inválida, expirado, etc.)
            throw new IllegalArgumentException(e.getMessage());
        }

        return usuario;
    }
}