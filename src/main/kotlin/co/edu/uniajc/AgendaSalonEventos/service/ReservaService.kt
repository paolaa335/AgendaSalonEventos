package co.edu.uniajc.AgendaSalonEventos.service

import co.edu.uniajc.AgendaSalonEventos.model.Reserva
import co.edu.uniajc.AgendaSalonEventos.repository.ReservaRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ReservaService(private val reservaRepository: ReservaRepository) {

    fun createReserva(reserva: Reserva): Reserva {
        validarReserva(reserva)
        return reservaRepository.save(reserva)
    }

    fun updateReserva(reserva: Reserva): Reserva {
        validarReserva(reserva, reserva.id)
        return reservaRepository.save(reserva)
    }

    fun deleteReserva(id: Long) {
        reservaRepository.deleteById(id)
    }

    fun cancelarReserva(id: Long): Reserva {
        val reserva = findById(id) ?: throw IllegalArgumentException("Reserva no encontrada")
        val reservaCancelada = reserva.copy(estado = "CANCELADA")
        return reservaRepository.save(reservaCancelada)
    }

    fun confirmarReserva(id: Long): Reserva {
        val reserva = findById(id) ?: throw IllegalArgumentException("Reserva no encontrada")
        val reservaConfirmada = reserva.copy(estado = "CONFIRMADA")
        return reservaRepository.save(reservaConfirmada)
    }

    fun findAllReservas(): List<Reserva> {
        return reservaRepository.findAll()
    }

    fun findById(id: Long): Reserva? {
        return reservaRepository.findById(id).orElse(null)
    }

    fun findByUsuario(usuarioId: Long): List<Reserva> {
        // Necesitarías un usuarioService para obtener el usuario por ID
        // Por ahora retornamos todas, luego se implementa
        return reservaRepository.findAll()
    }

    fun findBySalon(salonId: Long): List<Reserva> {
        // Similar al método anterior, necesita salonService
        return reservaRepository.findAll()
    }

    fun findReservasDisponibles(salonId: Long, fechaInicio: LocalDateTime, fechaFin: LocalDateTime): Boolean {
        // Implementación posterior con salonService
        return true
    }

    private fun validarReserva(reserva: Reserva, excludeReservaId: Long? = null) {
        // Validar fechas
        if (reserva.fechaInicio >= reserva.fechaFin) {
            throw IllegalArgumentException("La fecha de inicio debe ser anterior a la fecha fin")
        }

        // Validar que no sea en el pasado
        if (reserva.fechaInicio.isBefore(LocalDateTime.now())) {
            throw IllegalArgumentException("No se pueden crear reservas en el pasado")
        }

        // Validar duración mínima (30 minutos)
        val duracionMinima = java.time.Duration.ofMinutes(30)
        if (java.time.Duration.between(reserva.fechaInicio, reserva.fechaFin) < duracionMinima) {
            throw IllegalArgumentException("La reserva debe tener una duración mínima de 30 minutos")
        }

        // Validar solapamiento
        if (reservaRepository.existsReservaSolapada(
                reserva.salon,
                reserva.fechaInicio,
                reserva.fechaFin,
                excludeReservaId
            )) {
            throw IllegalArgumentException("El salón no está disponible en el horario seleccionado. Ya existe una reserva activa.")
        }
    }

    fun findReservasActivas(): List<Reserva> {
        return reservaRepository.findBySalonAndEstadoNotAndFechaInicioBetween(
            // Esto necesita más implementación, por ahora retornamos todas no canceladas
            reservaRepository.findAll().firstOrNull()?.salon ?: throw IllegalStateException("No hay salones"),
            "CANCELADA",
            LocalDateTime.now().minusYears(1),
            LocalDateTime.now().plusYears(1)
        )
    }
}