package co.edu.uniajc.AgendaSalonEventos.service

import co.edu.uniajc.AgendaSalonEventos.exception.CapacidadExcedidaException
import co.edu.uniajc.AgendaSalonEventos.exception.SalonNoDisponibleException
import co.edu.uniajc.AgendaSalonEventos.model.Reserva
import co.edu.uniajc.AgendaSalonEventos.repository.ReservaRepository
import org.springframework.stereotype.Service

@Service
class ReservaService(private val reservaRepository: ReservaRepository) {

    fun crearReserva(reserva: Reserva): Reserva {
        // Validar capacidad
        if (reserva.cantidadPersonas > reserva.salon.capacidad) {
            throw CapacidadExcedidaException("El salón no soporta esa cantidad de personas")
        }

        // Validar disponibilidad (simplificado)
        val reservasExistentes = reservaRepository.findAll()
        val cruce = reservasExistentes.any {
            it.salon.id == reserva.salon.id &&
            it.fecha == reserva.fecha &&
            reserva.horaInicio < it.horaFin &&
            reserva.horaFin > it.horaInicio
        }

        if (cruce) {
            throw SalonNoDisponibleException("El salón ya está reservado en ese horario")
        }

        return reservaRepository.save(reserva)
    }
    fun listarReservas(): List<Reserva> = reservaRepository.findAll()
}
