package co.edu.uniajc.AgendaSalonEventos.repository

import co.edu.uniajc.AgendaSalonEventos.model.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UsuarioRepository : JpaRepository<Usuario, Long> {

    // Buscar por email (como el login)
    fun findByEmail(email: String): Usuario?

    // Buscar todos los usuarios activos
    fun findAllByActivoTrue(): List<Usuario>
}