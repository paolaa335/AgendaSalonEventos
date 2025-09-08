package co.edu.uniajc.AgendaSalonEventos.repository

import co.edu.uniajc.AgendaSalonEventos.model.Reserva
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

import java.time.LocalDate
import java.time.LocalTime

@Repository
interface ReservaRepository : JpaRepository<Reserva, Long>{

    fun findBySalonIdAndFechaAndHoraInicioLessThanEqualAndHoraFinGreaterThanEqual(
        salonId: Long,
        fecha: LocalDate,
        horaInicio: LocalTime,
        horaFin: LocalTime
    ): List<Reserva>
}
