package co.edu.uniajc.AgendaSalonEventos.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

// Definición de la entidad simulada "Evento"
data class Evento(
    val id: Long,
    val nombre: String,
    val fecha: String,
    val lugar: String,
    val capacidad: Int
)

/**
 * Controlador encargado de gestionar los eventos.
 * Expone endpoints REST que permiten listar, obtener, crear y eliminar eventos.
 * Actualmente, los datos son simulados (no conectados a base de datos).
 */
@RestController
@RequestMapping("/api/eventos")
class EventoController {

    // Lista simulada de eventos en memoria
    private val eventos = mutableListOf(
        Evento(1, "Cumpleaños Infantil", "2025-09-15", "Salón Principal", 50),
        Evento(2, "Boda en Salón VIP", "2025-09-20", "Salón VIP", 120),
        Evento(3, "Reunión Empresarial", "2025-10-01", "Sala Ejecutiva", 30)
    )

    /**
     * GET /api/eventos
     * Retorna la lista completa de eventos disponibles, con filtros opcionales por lugar o fecha.
     */
    @GetMapping
    fun listarEventos(
        @RequestParam(required = false) lugar: String?,
        @RequestParam(required = false) fecha: String?
    ): List<Evento> {
        return eventos.filter { evento ->
            (lugar == null || evento.lugar.contains(lugar, ignoreCase = true)) &&
            (fecha == null || evento.fecha == fecha)
        }
    }

    /**
     * GET /api/eventos/{id}
     * Retorna un evento específico de acuerdo con el identificador solicitado.
     */
    @GetMapping("/{id}")
    fun obtenerEvento(@PathVariable id: Long): ResponseEntity<Any> {
        val evento = eventos.find { it.id == id }
        return if (evento != null) {
            ResponseEntity.ok(evento)
        } else {
            ResponseEntity.status(404).body(mapOf("error" to "Evento con id $id no encontrado"))
        }
    }

    /**
     * POST /api/eventos
     * Permite crear un nuevo evento. Se valida que los campos principales no estén vacíos.
     */
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

    /**
     * DELETE /api/eventos/{id}
     * Elimina un evento según su identificador, con respuesta clara si no se encuentra.
     */
    @DeleteMapping("/{id}")
    fun eliminarEvento(@PathVariable id: Long): ResponseEntity<Map<String, String>> {
        val eliminado = eventos.removeIf { it.id == id }
        return if (eliminado) {
            ResponseEntity.ok(mapOf("mensaje" to "Evento con id $id eliminado correctamente"))
        } else {
            ResponseEntity.status(404).body(mapOf("error" to "Evento con id $id no encontrado"))
        }
    }
}
