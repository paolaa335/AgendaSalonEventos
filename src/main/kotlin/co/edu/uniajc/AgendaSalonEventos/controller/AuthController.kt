package co.edu.uniajc.AgendaSalonEventos.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController {

    // Data class para representar un usuario
    data class Usuario(val id: Long, val nombre: String, val email: String)

    // Endpoint de login simulado
    @PostMapping("/login")
    fun login(@RequestBody credenciales: Map<String, String>): ResponseEntity<Map<String, Any>> {
        val email = credenciales["email"]
        val password = credenciales["password"]

        // Validación básica de ejemplo
        return if (email == "juan@example.com" && password == "1234") {
            ResponseEntity.ok(
                mapOf(
                    "mensaje" to "Inicio de sesión exitoso",
                    "usuario" to Usuario(1, "Juan Pérez", email),
                    "token" to "fake-jwt-token-12345"
                )
            )
        } else {
            ResponseEntity.status(401).body(mapOf("error" to "Credenciales incorrectas"))
        }
    }

    // Endpoint opcional para registro simulado
    @PostMapping("/register")
    fun register(@RequestBody nuevoUsuario: Map<String, String>): ResponseEntity<Map<String, Any>> {
        val nombre = nuevoUsuario["nombre"] ?: return ResponseEntity.badRequest().body(mapOf("error" to "Falta nombre"))
        val email = nuevoUsuario["email"] ?: return ResponseEntity.badRequest().body(mapOf("error" to "Falta email"))

        // Solo simulación, no se guarda realmente
        return ResponseEntity.status(201).body(
            mapOf(
                "mensaje" to "Usuario registrado exitosamente",
                "usuario" to Usuario(2, nombre, email)
            )
        )
    }
}
