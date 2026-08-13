package mx.gob.imss.mscme.admonplazas.security;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(

		info = @Info(title = "Administracion de plazas", description = """
				                - Autenticación JWT para aplicaciones internas y socios.
				`Authorization: Bearer <token>`.
				                """, version = "0.0.1", contact = @Contact(name = "asignacion.com"), extensions = @Extension(name = "x-deployment-notes", properties = @ExtensionProperty(name = "value", value = ".")))

)

@SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer")
public class SwaggerConfig {
}
