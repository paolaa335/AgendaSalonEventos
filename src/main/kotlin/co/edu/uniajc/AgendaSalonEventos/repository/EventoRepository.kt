package co.edu.uniajc.AgendaSalonEventos.repository

import co.edu.uniajc.AgendaSalonEventos.model.Evento
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EventoRepository : JpaRepository<Evento, Long> {
    fun findByTipo(tipo: String): List<Evento>
}
