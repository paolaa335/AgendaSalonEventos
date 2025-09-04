package co.edu.uniajc.AgendaSalonEventos.repository

import co.edu.uniajc.AgendaSalonEventos.model.Salon
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SalonRepository : JpaRepository<Salon, Long> {


    fun findAllByNombreContainsIgnoreCase(nombre: String): List<Salon>


    override fun findById(id: Long): java.util.Optional<Salon>


    fun findAllByCapacidad(capacidad: Int): List<Salon>
}

