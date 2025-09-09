package co.edu.uniajc.AgendaSalonEventos.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.security.SecurityRequirement

@Configuration
class OpenApiConfig {

    @Bean
    fun openAPI(): OpenAPI {
        val info = Info()
            .title("Agenda de Salones de Eventos API")
            .version("v1")
            .description("API para gestionar salones, reservas, eventos y autenticación")
            .contact(Contact().name("InnovaPro").email("iyate@estudiante.uniajc.edu.co"))

        val securityScheme = SecurityScheme()
            .type(SecurityScheme.Type.HTTP)
            .scheme("bearer")
            .bearerFormat("JWT")

        val components = Components()
            .addSecuritySchemes("bearerAuth", securityScheme)

        val securityRequirement = SecurityRequirement().addList("bearerAuth")

        return OpenAPI()
            .components(components)
            .addSecurityItem(securityRequirement)
            .info(info)
    }
}
