package co.edu.uniajc.AgendaSalonEventos.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

// Swagger / OpenAPI
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag

// Data class para representar una reserva
data class Reserva(
    val id: Long,
    val cliente: String,
    val evento: String,
    val salon: String,
    val fecha: String,
    val estado: String // Ej: Confirmada, Pendiente, Cancelada
)

@Tag(name = "Reservas", description = "Gestión de reservas de salones de eventos")
@RestController
@RequestMapping("/api/reservas")
class ReservaController {

    // Lista simulada de reservas
    private val reservas = mutableListOf(
        Reserva(1, "Ana López", "Cumpleaños Infantil", "Salón Principal", "2025-09-15", "Confirmada"),
        Reserva(2, "Carlos Ruiz", "Boda", "Salón VIP", "2025-09-20", "Pendiente")
    )

    @Operation(summary = "Listar reservas", description = "Devuelve el listado completo de reservas")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Listado de reservas obtenido correctamente")
        ]
    )
    @GetMapping
    fun listarReservas(): List<Reserva> = reservas

    @Operation(summary = "Obtener reserva por ID", description = "Devuelve una reserva específica por su identificador")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Reserva encontrada"),
            ApiResponse(responseCode = "404", description = "Reserva no encontrada")
        ]
    )
    @GetMapping("/{id}")
    fun obtenerReserva(@PathVariable id: Long): ResponseEntity<Any> {
        val reserva = reservas.find { it.id == id }
        return if (reserva != null) {
            ResponseEntity.ok(reserva)
        } else {
            ResponseEntity.status(404).body(mapOf("error" to "Reserva con id $id no encontrada"))
        }
    }

    @Operation(summary = "Crear reserva", description = "Registra una nueva reserva en el sistema")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Reserva creada correctamente"),
            ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
        ]
    )
    @PostMapping
    fun crearReserva(@RequestBody nuevaReserva: Reserva): ResponseEntity<Any> {
        if (nuevaReserva.cliente.isBlank() || nuevaReserva.evento.isBlank() || nuevaReserva.salon.isBlank()) {
            return ResponseEntity.badRequest().body(mapOf("error" to "Datos de reserva incompletos"))
        }
        val idGenerado = (reservas.maxOfOrNull { it.id } ?: 0) + 1
        val reservaConId = nuevaReserva.copy(id = idGenerado)
        reservas.add(reservaConId)
        return ResponseEntity.status(201).body(reservaConId)
    }

    @Operation(summary = "Eliminar reserva", description = "Elimina una reserva por su identificador")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Reserva eliminada correctamente"),
            ApiResponse(responseCode = "404", description = "Reserva no encontrada")
        ]
    )
    @DeleteMapping("/{id}")
    fun eliminarReserva(@PathVariable id: Long): ResponseEntity<Map<String, String>> {
        val eliminado = reservas.removeIf { it.id == id }
        return if (eliminado) {
            ResponseEntity.ok(mapOf("mensaje" to "Reserva con id $id eliminada correctamente"))
        } else {
            ResponseEntity.status(404).body(mapOf("error" to "Reserva con id $id no encontrada"))
        }
    }
}
