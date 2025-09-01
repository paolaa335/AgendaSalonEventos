package co.edu.uniajc.AgendaSalonEventos.controller

import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/api")
class HomeController {

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
