package co.edu.uniajc.AgendaSalonEventos.repository

import co.edu.uniajc.AgendaSalonEventos.model.Evento

import co.edu.uniajc.AgendaSalonEventos.model.Usuario
import co.edu.uniajc.AgendaSalonEventos.model.Salon
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface EventoRepository : JpaRepository<Evento, Long> {

    // Buscar eventos por organizador
    fun findAllByUsuario(usuario: Usuario): List<Evento>

    // Buscar eventos en un salón específico
    fun findAllBySalon(salon: Salon): List<Evento>

    // Buscar eventos entre fechas
    fun findAllByFechaInicioBetween(start: LocalDateTime, end: LocalDateTime): List<Evento>
}