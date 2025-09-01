package co.edu.uniajc.AgendaSalonEventos.repository

import co.edu.uniajc.AgendaSalonEventos.model.Reserva
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface ReservaRepository : JpaRepository<Reserva, Long> {

    @Query("SELECT r FROM Reserva r WHERE r.salon.id = :salonId AND r.fecha = :fecha")
    fun findReservasBySalonAndFecha(
        @Param("salonId") salonId: Long,
        @Param("fecha") fecha: LocalDate
    ): List<Reserva>
}
