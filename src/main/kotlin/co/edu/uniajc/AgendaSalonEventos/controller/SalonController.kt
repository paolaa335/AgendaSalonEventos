package co.edu.uniajc.AgendaSalonEventos.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

// Swagger / OpenAPI
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
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

    // Lista simulada de salones
    private val salones = mutableListOf(
        Salon(1, "Salón Principal", 50, "Piso 1", true),
        Salon(2, "Salón VIP", 120, "Piso 2", false),
        Salon(3, "Sala Ejecutiva", 30, "Piso 3", true)
    )

    @Operation(summary = "Listar salones", description = "Obtiene todos los salones disponibles con filtros opcionales por capacidad y disponibilidad")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Listado de salones obtenido correctamente")
        ]
    )
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

    @Operation(summary = "Obtener salón por ID", description = "Devuelve un salón específico según su identificador")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Salón encontrado"),
            ApiResponse(responseCode = "404", description = "Salón no encontrado")
        ]
    )
    @GetMapping("/{id}")
    fun obtenerSalon(@PathVariable id: Long): ResponseEntity<Any> {
        val salon = salones.find { it.id == id }
        return if (salon != null) {
            ResponseEntity.ok(salon)
        } else {
            ResponseEntity.status(404).body(mapOf("error" to "Salón con id $id no encontrado"))
        }
    }

    @Operation(summary = "Crear salón", description = "Registra un nuevo salón en el sistema")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Salón creado correctamente"),
            ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
        ]
    )
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

    @Operation(summary = "Eliminar salón", description = "Elimina un salón según su identificador")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Salón eliminado correctamente"),
            ApiResponse(responseCode = "404", description = "Salón no encontrado")
        ]
    )
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
