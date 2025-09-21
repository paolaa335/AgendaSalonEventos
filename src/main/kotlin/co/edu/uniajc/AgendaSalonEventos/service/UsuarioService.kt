package co.edu.uniajc.AgendaSalonEventos.service

import co.edu.uniajc.AgendaSalonEventos.model.Usuario
import co.edu.uniajc.AgendaSalonEventos.repository.UsuarioRepository
import org.springframework.stereotype.Service

@Service
class UsuarioService(private val usuarioRepository: UsuarioRepository) {

    fun createUsuario(usuario: Usuario): Usuario {
        return usuarioRepository.save(usuario)
    }

    fun updateUsuario(usuario: Usuario): Usuario {
        return usuarioRepository.save(usuario)
    }

    fun deleteUsuario(id: Long) {
        usuarioRepository.deleteById(id)
    }

    fun findAllUsuarios(): List<Usuario> {
        return usuarioRepository.findAll()
    }

    fun findById(id: Long): Usuario? {
        return usuarioRepository.findById(id).orElse(null)
    }

    fun guardarUsuario(usuario: Usuario): Usuario {
        return usuarioRepository.save(usuario)
    }
}