package co.edu.uniajc.AgendaSalonEventos.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag

data class Salon(
    val id: Long,
    val nombre: String,
    val capacidad: Int,
    val ubicacion: String,
    val disponible: Boolean
)

@Tag(name = "Salones", description = "Gestión de salones de eventos")
@RestController
@RequestMapping("/api/salones")
class SalonController {

    private val salones = mutableListOf(
        Salon(1, "Salón Principal", 50, "Piso 1", true),
        Salon(2, "Salón VIP", 120, "Piso 2", false)
    )

    @Operation(summary = "Listar salones")
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

    @Operation(summary = "Obtener salón por ID")
    @GetMapping("/{id}")
    fun obtenerSalon(@PathVariable id: Long): ResponseEntity<Any> {
        val s = salones.find { it.id == id }
        return s?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.status(404).body(mapOf("error" to "Salón con id $id no encontrado"))
    }

    @Operation(summary = "Crear salón")
    @PostMapping
    fun crearSalon(@RequestBody nuevoSalon: Salon): ResponseEntity<Any> {
        if (nuevoSalon.nombre.isBlank() || nuevoSalon.capacidad <= 0 || nuevoSalon.ubicacion.isBlank()) {
            return ResponseEntity.badRequest().body(mapOf("error" to "Datos de salón incompletos"))
        }
        val idGenerado = (salones.maxOfOrNull { it.id } ?: 0) + 1
        val salonConId = nuevoSalon.copy(id = idGenerado)
        salones.add(salonConId)
        return ResponseEntity.status(201).body(salonConId)
    }

    @Operation(summary = "Eliminar salón")
    @DeleteMapping("/{id}")
    fun eliminarSalon(@PathVariable id: Long): ResponseEntity<Map<String, String>> {
        val eliminado = salones.removeIf { it.id == id }
        return if (eliminado) ResponseEntity.ok(mapOf("mensaje" to "Salón eliminado"))
               else ResponseEntity.status(404).body(mapOf("error" to "Salón no encontrado"))
    }
}
