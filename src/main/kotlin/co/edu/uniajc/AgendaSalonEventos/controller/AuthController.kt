package co.edu.uniajc.AgendaSalonEventos.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

// Swagger / OpenAPI
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag

data class UsuarioDTO(
    val id: Long? = null,
    val nombre: String,
    val email: String,
    val password: String? = null
)

data class LoginRequest(val email: String, val password: String)
data class AuthResponse(val token: String, val usuario: UsuarioDTO)

@Tag(name = "Auth", description = "Autenticación y gestión de usuarios")
@RestController
@RequestMapping("/api/auth")
class AuthController {

    private val usuarios = mutableListOf(
        UsuarioDTO(1, "Admin", "admin@example.com", null)
    )
    private var nextId = 2L

    @Operation(summary = "Registrar usuario")
    @PostMapping("/register")
    fun register(@RequestBody usuario: UsuarioDTO): ResponseEntity<Any> {
        if (usuario.nombre.isBlank() || usuario.email.isBlank()) {
            return ResponseEntity.badRequest().body(mapOf("error" to "Datos de usuario incompletos"))
        }
        if (usuarios.any { it.email.equals(usuario.email, true) }) {
            return ResponseEntity.badRequest().body(mapOf("error" to "Email ya registrado"))
        }
        val creado = usuario.copy(id = nextId++)
        usuarios.add(creado)
        return ResponseEntity.status(201).body(creado.copy(password = null))
    }

    @Operation(summary = "Login")
    @PostMapping("/login")
    fun login(@RequestBody req: LoginRequest): ResponseEntity<Any> {
        val user = usuarios.find { it.email.equals(req.email, true) }
        return if (user != null) {
            val token = "token-simulado-${user.id}"
            ResponseEntity.ok(AuthResponse(token, user.copy(password = null)))
        } else {
            ResponseEntity.status(401).body(mapOf("error" to "Credenciales inválidas"))
        }
    }

    @Operation(summary = "Listar usuarios (demo)")
    @GetMapping("/usuarios")
    fun listarUsuarios(): List<UsuarioDTO> = usuarios.map { it.copy(password = null) }
}
