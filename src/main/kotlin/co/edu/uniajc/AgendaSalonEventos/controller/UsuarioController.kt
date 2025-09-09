package co.edu.uniajc.AgendaSalonEventos.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.atomic.AtomicLong

data class UsuarioDTO(
    val id: Long? = null,
    val nombre: String,
    val email: String,
    val password: String? = null
)

@Tag(name = "Usuarios", description = "Gestión de usuarios (demo en memoria)")
@RestController
@RequestMapping("/api/usuarios")
class UsuarioController {

    private val seq = AtomicLong(2)
    private val usuarios = CopyOnWriteArrayList<UsuarioDTO>(
        listOf(UsuarioDTO(1, "Admin", "admin@example.com", null))
    )

    @Operation(summary = "Listar usuarios")
    @GetMapping
    fun listar(): List<UsuarioDTO> = usuarios.map { it.copy(password = null) }

    @Operation(summary = "Obtener usuario por id")
    @GetMapping("/{id}")
    fun obtener(@PathVariable id: Long): ResponseEntity<Any> {
        val u = usuarios.find { it.id == id } ?: return ResponseEntity.status(404).body(mapOf("error" to "Usuario no encontrado"))
        return ResponseEntity.ok(u.copy(password = null))
    }

    @Operation(summary = "Crear usuario (demo)")
    @PostMapping
    fun crear(@RequestBody nuevo: UsuarioDTO): ResponseEntity<Any> {
        if (nuevo.nombre.isBlank() || nuevo.email.isBlank()) {
            return ResponseEntity.badRequest().body(mapOf("error" to "Datos incompletos"))
        }
        if (usuarios.any { it.email.equals(nuevo.email, true) }) {
            return ResponseEntity.badRequest().body(mapOf("error" to "Email ya registrado"))
        }
        val creado = nuevo.copy(id = seq.getAndIncrement(), password = null)
        usuarios.add(creado)
        return ResponseEntity.status(201).body(creado)
    }

    @Operation(summary = "Actualizar usuario (demo)")
    @PutMapping("/{id}")
    fun actualizar(@PathVariable id: Long, @RequestBody update: UsuarioDTO): ResponseEntity<Any> {
        val idx = usuarios.indexOfFirst { it.id == id }
        if (idx == -1) return ResponseEntity.status(404).body(mapOf("error" to "Usuario no encontrado"))
        val merged = update.copy(id = id, password = null)
        usuarios[idx] = merged
        return ResponseEntity.ok(merged)
    }

    @Operation(summary = "Eliminar usuario (demo)")
    @DeleteMapping("/{id}")
    fun eliminar(@PathVariable id: Long): ResponseEntity<Any> {
        val removed = usuarios.removeIf { it.id == id }
        return if (removed) ResponseEntity.ok(mapOf("mensaje" to "Usuario eliminado"))
               else ResponseEntity.status(404).body(mapOf("error" to "Usuario no encontrado"))
    }
}
