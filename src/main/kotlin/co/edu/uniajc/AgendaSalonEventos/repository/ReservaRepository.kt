package co.edu.uniajc.AgendaSalonEventos.repository

import co.edu.uniajc.AgendaSalonEventos.model.Reserva
import co.edu.uniajc.AgendaSalonEventos.model.Salon
import co.edu.uniajc.AgendaSalonEventos.model.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface ReservaRepository : JpaRepository<Reserva, Long> {

    // Buscar reservas por usuario
    fun findAllByUsuario(usuario: Usuario): List<Reserva>

    // Buscar reservas por salón
    fun findAllBySalon(salon: Salon): List<Reserva>

    // Buscar reservas por evento
    fun findAllByEventoId(eventoId: Long): List<Reserva>

    // Buscar reservas entre fechas
    fun findAllByFechaInicioBetween(start: LocalDateTime, end: LocalDateTime): List<Reserva>

    // Validar si existe reserva solapada para un salón
    @Query("""
        SELECT COUNT(r) > 0 FROM Reserva r 
        WHERE r.salon = :salon 
        AND r.estado != 'CANCELADA'
        AND (
            (r.fechaInicio <= :fechaFin AND r.fechaFin >= :fechaInicio)
            OR (r.fechaInicio BETWEEN :fechaInicio AND :fechaFin)
            OR (r.fechaFin BETWEEN :fechaInicio AND :fechaFin)
        )
        AND r.id != COALESCE(:excludeReservaId, -1)
    """)
    fun existsReservaSolapada(
        @Param("salon") salon: Salon,
        @Param("fechaInicio") fechaInicio: LocalDateTime,
        @Param("fechaFin") fechaFin: LocalDateTime,
        @Param("excludeReservaId") excludeReservaId: Long? = null
    ): Boolean

    // Buscar reservas activas por salón y rango de fechas
    fun findBySalonAndEstadoNotAndFechaInicioBetween(
        salon: Salon,
        estado: String,
        start: LocalDateTime,
        end: LocalDateTime
    ): List<Reserva>
}