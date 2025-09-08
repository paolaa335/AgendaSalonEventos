package co.edu.uniajc.AgendaSalonEventos.controller

import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

// Swagger / OpenAPI
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag

@Tag(name = "Home", description = "Información general y estado de la API")
@RestController
@RequestMapping("/api")
class HomeController {

    @Operation(summary = "Información base", description = "Mensaje de bienvenida, versión y lista de endpoints disponibles")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Información obtenida correctamente")
        ]
    )
    @GetMapping
    fun home(): Map<String, Any> {
        return mapOf(
            "mensaje" to "Bienvenido a la API de Agenda de Salones de Eventos",
            "version" to "1.0.0",
            "fecha" to LocalDateTime.now(),
            "endpoints" to listOf(
                "/api/auth",
                "/api/eventos",
                "/api/salones",
                "/api/reservas"
            )
        )
    }
}
