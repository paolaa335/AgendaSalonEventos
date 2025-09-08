package co.edu.uniajc.AgendaSalonEventos.service

import co.edu.uniajc.AgendaSalonEventos.model.Evento
import co.edu.uniajc.AgendaSalonEventos.repository.EventoRepository
import org.springframework.stereotype.Service

@Service
class EventoService(private val eventoRepository: EventoRepository) {

    fun createEvento(evento: Evento): Evento {
        return eventoRepository.save(evento)
    }

    fun updateEvento(evento: Evento): Evento {
        return eventoRepository.save(evento)
    }

    fun deleteEvento(id: Long) {
        eventoRepository.deleteById(id)
    }

    fun findAllEventos(): List<Evento> {
        return eventoRepository.findAll()
    }

    fun findById(id: Long): Evento? {
        return eventoRepository.findById(id).orElse(null)
    }
}
