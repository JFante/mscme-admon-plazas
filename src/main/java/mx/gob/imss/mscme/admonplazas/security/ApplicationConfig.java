package mx.gob.imss.mscme.admonplazas.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import mx.gob.imss.mscme.admonplazas.exceptions.UserNotFoundException;
import mx.gob.imss.mscme.admonplazas.repository.UsuarioRepository;

@Configuration
public class ApplicationConfig {

        // Cambia el tipo de repositorio para que coincida con tu interfaz
    private final UsuarioRepository usuarioRepository; 

    // Inyecta el repositorio en el constructor
    public ApplicationConfig(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
    
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> usuarioRepository.findByRefEmailAndIndActivo(username, 1L) // Usa findByRefEmail
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado con email: " + username));
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                try {
                    // Intenta autenticar con el comportamiento por defecto de Spring Security
                    return super.authenticate(authentication);
                } catch (UsernameNotFoundException ex) {
                    // Si el usuario no se encuentra, lanza una excepción que puedas capturar en el @RestControllerAdvice
                    throw new UsernameNotFoundException("El usuario no existe.", ex);
                } catch (BadCredentialsException ex) {
                    // Si las credenciales son incorrectas, lanza una excepción específica
                    throw new BadCredentialsException("La contraseña es incorrecta.", ex);
                }
            }
        };

        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
