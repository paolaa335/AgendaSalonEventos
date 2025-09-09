package co.edu.uniajc.AgendaSalonEventos.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag

data class Evento(
    val id: Long,
    val nombre: String,
    val fecha: String,
    val lugar: String,
    val capacidad: Int
)

@Tag(name = "Eventos", description = "Gestión de eventos y actividades")
@RestController
@RequestMapping("/api/eventos")
class EventoController {

    private val eventos = mutableListOf(
        Evento(1, "Cumpleaños Infantil", "2025-09-15", "Salón Principal", 50),
        Evento(2, "Boda en Salón VIP", "2025-09-20", "Salón VIP", 120)
    )

    @Operation(summary = "Listar eventos")
    @GetMapping
    fun listarEventos(
        @RequestParam(required = false) lugar: String?,
        @RequestParam(required = false) fecha: String?
    ): List<Evento> {
        return eventos.filter { e ->
            (lugar == null || e.lugar.contains(lugar, ignoreCase = true)) &&
            (fecha == null || e.fecha == fecha)
        }
    }

    @Operation(summary = "Obtener evento por ID")
    @GetMapping("/{id}")
    fun obtenerEvento(@PathVariable id: Long): ResponseEntity<Any> {
        val evento = eventos.find { it.id == id }
        return evento?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.status(404).body(mapOf("error" to "Evento con id $id no encontrado"))
    }

    @Operation(summary = "Crear evento")
    @PostMapping
    fun crearEvento(@RequestBody nuevoEvento: Evento): ResponseEntity<Any> {
        if (nuevoEvento.nombre.isBlank() || nuevoEvento.lugar.isBlank() || nuevoEvento.capacidad <= 0) {
            return ResponseEntity.badRequest().body(mapOf("error" to "Datos de evento incompletos o inválidos"))
        }
        val idGenerado = (eventos.maxOfOrNull { it.id } ?: 0) + 1
        val eventoConId = nuevoEvento.copy(id = idGenerado)
        eventos.add(eventoConId)
        return ResponseEntity.status(201).body(eventoConId)
    }

    @Operation(summary = "Eliminar evento")
    @DeleteMapping("/{id}")
    fun eliminarEvento(@PathVariable id: Long): ResponseEntity<Map<String, String>> {
        val eliminado = eventos.removeIf { it.id == id }
        return if (eliminado) ResponseEntity.ok(mapOf("mensaje" to "Evento eliminado"))
               else ResponseEntity.status(404).body(mapOf("error" to "Evento no encontrado"))
    }
}
