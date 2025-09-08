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
    val password: String? = null // no devolver en respuestas reales
)

data class LoginRequest(val email: String, val password: String)
data class AuthResponse(val token: String, val usuario: UsuarioDTO)

@Tag(name = "Auth", description = "Autenticación y gestión de usuarios")
@RestController
@RequestMapping("/api/auth")
class AuthController {

    // Simulación simple de almacenamiento en memoria
    private val usuarios = mutableListOf(
        UsuarioDTO(1, "Admin", "admin@example.com", null)
    )
    private var nextId = 2L

    @Operation(summary = "Registrar usuario", description = "Crea una nueva cuenta de usuario")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Usuario creado correctamente"),
            ApiResponse(responseCode = "400", description = "Datos inválidos o usuario ya existe")
        ]
    )
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

    @Operation(summary = "Login", description = "Autentica un usuario y devuelve token simulado")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Autenticación exitosa"),
            ApiResponse(responseCode = "401", description = "Credenciales inválidas")
        ]
    )
    @PostMapping("/login")
    fun login(@RequestBody req: LoginRequest): ResponseEntity<Any> {
        val user = usuarios.find { it.email.equals(req.email, true) }
        // Simulación: no hay verificación real de password
        return if (user != null) {
            val token = "token-simulado-${user.id}"
            ResponseEntity.ok(AuthResponse(token, user.copy(password = null)))
        } else {
            ResponseEntity.status(401).body(mapOf("error" to "Credenciales inválidas"))
        }
    }

    @Operation(summary = "Listar usuarios (demo)", description = "Devuelve lista de usuarios sin contraseñas")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Listado de usuarios")
        ]
    )
    @GetMapping("/usuarios")
    fun listarUsuarios(): List<UsuarioDTO> = usuarios.map { it.copy(password = null) }
}
