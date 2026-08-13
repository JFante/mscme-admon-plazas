package mx.gob.imss.mscme.admonplazas.security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class CorsConfig implements WebMvcConfigurer {

     @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                // CAMBIO CLAVE: Usar "*" para permitir todos los orígenes.
                .allowedOrigins("*") 
                .allowedMethods("*")
                .allowedHeaders("*")
                .exposedHeaders("Authorization", "Content-Disposition")
                // NOTA: Con allowedOrigins("*"), allowCredentials(true) puede causar problemas
                // y es menos seguro. Si no necesitas credenciales (cookies/tokens), 
                // es mejor establecerlo en 'false' si usas "*". 
                // Sin embargo, si lo necesitas, mira la nota en el CorsFilter.
                .allowCredentials(false) 
                .maxAge(3600);
    }


    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        
              
        config.setAllowCredentials(false); // Recomendado si usas addAllowedOrigin("*")

        // CAMBIO CLAVE: Usar "*" para permitir todos los orígenes.
        config.addAllowedOrigin("*"); 

        // Una alternativa más moderna y que funciona bien con la propiedad `origins` 
        // del CorsConfiguration es:
        // config.setAllowedOrigins(Collections.singletonList("*"));
        
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.addExposedHeader("Authorization");
        config.addExposedHeader("Content-Disposition");
        config.setMaxAge(3600L);
        
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
