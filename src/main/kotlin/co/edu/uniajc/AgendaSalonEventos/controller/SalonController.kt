package co.edu.uniajc.AgendaSalonEventos.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

data class Salon(
    val id: Long,
    val nombre: String,
    val capacidad: Int,
    val ubicacion: String,
    val disponible: Boolean
)

@RestController
@RequestMapping("/api/salones")
class SalonController {

    // Lista simulada de salones
    private val salones = mutableListOf(
        Salon(1, "Salón Principal", 50, "Piso 1", true),
        Salon(2, "Salón VIP", 120, "Piso 2", false),
        Salon(3, "Sala Ejecutiva", 30, "Piso 3", true)
    )

    // GET /api/salones → lista salones con filtros opcionales
    @GetMapping
    fun listarSalones(
        @RequestParam(required = false) disponible: Boolean?,
        @RequestParam(required = false) capacidad: Int?
    ): List<Salon> {
        return salones.filter { s ->
            (disponible == null || s.disponible == disponible) &&
            (capacidad == null || s.capacidad >= capacidad)
        }
    }

    // GET /api/salones/{id} → devuelve salón específico
    @GetMapping("/{id}")
    fun obtenerSalon(@PathVariable id: Long): ResponseEntity<Any> {
        val salon = salones.find { it.id == id }
        return if (salon != null) {
            ResponseEntity.ok(salon)
        } else {
            ResponseEntity.status(404).body(mapOf("error" to "Salón con id $id no encontrado"))
        }
    }

    // POST /api/salones → crea un salón con validación
    @PostMapping
    fun crearSalon(@RequestBody nuevoSalon: Salon): ResponseEntity<Any> {
        if (nuevoSalon.nombre.isBlank() || nuevoSalon.capacidad <= 0 || nuevoSalon.ubicacion.isBlank()) {
            return ResponseEntity.badRequest().body(mapOf("error" to "Datos de salón incompletos o inválidos"))
        }
        val idGenerado = (salones.maxOfOrNull { it.id } ?: 0) + 1
        val salonConId = nuevoSalon.copy(id = idGenerado)
        salones.add(salonConId)
        return ResponseEntity.status(201).body(salonConId)
    }

    // DELETE /api/salones/{id} → elimina un salón
    @DeleteMapping("/{id}")
    fun eliminarSalon(@PathVariable id: Long): ResponseEntity<Map<String, String>> {
        val eliminado = salones.removeIf { it.id == id }
        return if (eliminado) {
            ResponseEntity.ok(mapOf("mensaje" to "Salón con id $id eliminado correctamente"))
        } else {
            ResponseEntity.status(404).body(mapOf("error" to "Salón con id $id no encontrado"))
        }
    }
}
