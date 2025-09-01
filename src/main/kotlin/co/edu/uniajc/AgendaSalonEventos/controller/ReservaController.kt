package co.edu.uniajc.AgendaSalonEventos.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

// Data class para representar una reserva
data class Reserva(
    val id: Long,
    val cliente: String,
    val evento: String,
    val salon: String,
    val fecha: String,
    val estado: String // Ej: Confirmada, Pendiente, Cancelada
)

@RestController
@RequestMapping("/api/reservas")
class ReservaController {

    // Lista simulada de reservas
    private val reservas = mutableListOf(
        Reserva(1, "Ana López", "Cumpleaños Infantil", "Salón Principal", "2025-09-15", "Confirmada"),
        Reserva(2, "Carlos Ruiz", "Boda", "Salón VIP", "2025-09-20", "Pendiente")
    )

    // GET /api/reservas → devuelve lista simulada de reservas
    @GetMapping
    fun listarReservas(): List<Reserva> = reservas

    // GET /api/reservas/{id} → devuelve reserva específica
    @GetMapping("/{id}")
    fun obtenerReserva(@PathVariable id: Long): ResponseEntity<Any> {
        val reserva = reservas.find { it.id == id }
        return if (reserva != null) {
            ResponseEntity.ok(reserva)
        } else {
            ResponseEntity.status(404).body(mapOf("error" to "Reserva con id $id no encontrada"))
        }
    }

    // POST /api/reservas → crea una reserva (simulado)
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

    // DELETE /api/reservas/{id} → elimina una reserva (simulado)
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
