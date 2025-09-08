package co.edu.uniajc.AgendaSalonEventos.repository

import co.edu.uniajc.AgendaSalonEventos.model.Reserva
import co.edu.uniajc.AgendaSalonEventos.model.Salon
import co.edu.uniajc.AgendaSalonEventos.model.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface ReservaRepository : JpaRepository<Reserva, Long> {

    // Buscar reservas por usuario
    fun findAllByUsuario(usuario: Usuario): List<Reserva>

    // Buscar reservas por salón
    fun findAllBySalon(salon: Salon): List<Reserva>

    // Buscar reservas entre fechas
    fun findAllByFechaBetween(start: LocalDateTime, end: LocalDateTime): List<Reserva>
}
