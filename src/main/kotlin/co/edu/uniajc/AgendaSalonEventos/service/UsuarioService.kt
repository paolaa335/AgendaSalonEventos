package co.edu.uniajc.AgendaSalonEventos.service

import co.edu.uniajc.AgendaSalonEventos.model.Usuario
import co.edu.uniajc.AgendaSalonEventos.repository.UsuarioRepository
import org.springframework.stereotype.Service

@Service
class UsuarioService(private val usuarioRepository: UsuarioRepository) {

    fun crearUsuario(usuario: Usuario): Usuario {
        if (usuarioRepository.existsByCorreo(usuario.correo)) {
            throw IllegalArgumentException("El correo ya está registrado")
        }
        return usuarioRepository.save(usuario)
    }

    fun listarUsuarios(): List<Usuario> = usuarioRepository.findAll()

    fun buscarPorId(id: Long): Usuario =
        usuarioRepository.findById(id).orElseThrow { IllegalArgumentException("Usuario no encontrado") }

    fun actualizarUsuario(id: Long, nuevoUsuario: Usuario): Usuario {
        val usuario = usuarioRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Usuario no encontrado") }

        if (usuario.correo != nuevoUsuario.correo &&
            usuarioRepository.existsByCorreo(nuevoUsuario.correo)) {
            throw IllegalArgumentException("El correo ya está registrado")
        }

        usuario.nombre = nuevoUsuario.nombre
        usuario.correo = nuevoUsuario.correo
        usuario.contrasena = nuevoUsuario.contrasena

        return usuarioRepository.save(usuario)
    }

    fun eliminarUsuario(id: Long) {
        val usuario = usuarioRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Usuario no encontrado") }
        usuarioRepository.delete(usuario)
    }
}
