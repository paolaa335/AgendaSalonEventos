package co.edu.uniajc.AgendaSalonEventos.service

import co.edu.uniajc.AgendaSalonEventos.model.Evento
import co.edu.uniajc.AgendaSalonEventos.repository.EventoRepository
import org.springframework.stereotype.Service

@Service
class EventoService(private val eventoRepository: EventoRepository) {

    fun listarTodos(): List<Evento> = eventoRepository.findAll()

    fun buscarPorId(id: Long): Evento? = eventoRepository.findById(id).orElse(null)

    fun crear(evento: Evento): Evento = eventoRepository.save(evento)

    fun actualizar(id: Long, eventoActualizado: Evento): Evento? {
        return if (eventoRepository.existsById(id)) {
            val evento = eventoActualizado.copy(id = id)
            eventoRepository.save(evento)
        } else {
            null
        }
    }

    fun eliminar(id: Long): Boolean {
        return if (eventoRepository.existsById(id)) {
            eventoRepository.deleteById(id)
            true
        } else {
            false
        }
    }
}
