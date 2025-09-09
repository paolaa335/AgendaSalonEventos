package co.edu.uniajc.AgendaSalonEventos.service

import co.edu.uniajc.AgendaSalonEventos.model.Reserva
import co.edu.uniajc.AgendaSalonEventos.repository.ReservaRepository
import org.springframework.stereotype.Service

@Service
class ReservaService(private val reservaRepository: ReservaRepository) {

    fun createReserva(reserva: Reserva): Reserva {
        return reservaRepository.save(reserva)
    }

    fun updateReserva(reserva: Reserva): Reserva {
        return reservaRepository.save(reserva)
    }

    fun deleteReserva(id: Long) {
        reservaRepository.deleteById(id)
    }

    fun findAllReservas(): List<Reserva> {
        return reservaRepository.findAll()
    }

    fun findById(id: Long): Reserva? {
        return reservaRepository.findById(id).orElse(null)
    }
}