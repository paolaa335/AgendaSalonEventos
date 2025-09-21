package co.edu.uniajc.AgendaSalonEventos.controller

import co.edu.uniajc.AgendaSalonEventos.model.Usuario
import co.edu.uniajc.AgendaSalonEventos.service.UsuarioService
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/usuario")
class UsuarioController @Autowired constructor(
    private val usuarioService: UsuarioService
) {

    @PostMapping("/save")
    fun saveUsuario(@RequestBody usuario: Usuario): Usuario {
        return usuarioService.createUsuario(usuario)
    }

    @PutMapping("/update")
    fun updateUsuario(@RequestBody usuario: Usuario): Usuario {
        return usuarioService.updateUsuario(usuario)
    }

    @DeleteMapping("/delete")
    fun deleteUsuario(@RequestParam(name = "id") id: Long) {
        usuarioService.deleteUsuario(id)
    }

    @GetMapping("/all")
    fun findAllUsuarios(): List<Usuario> {
        return usuarioService.findAllUsuarios()
    }

    @GetMapping("/id")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "400", description = "Petición inválida"),
            ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            ApiResponse(responseCode = "500", description = "Error interno del servidor")
        ]
    )
    fun findById(@RequestParam(name = "id") idUsuario: Long): ResponseEntity<Usuario> {
        val usuario = usuarioService.findById(idUsuario)
            ?: throw RuntimeException("Usuario no encontrado")
        return ResponseEntity.ok(usuario)
    }
}