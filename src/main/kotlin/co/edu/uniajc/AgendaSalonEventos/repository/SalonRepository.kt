package co.edu.uniajc.AgendaSalonEventos.repository

import co.edu.uniajc.AgendaSalonEventos.model.Salon
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SalonRepository : JpaRepository<Salon, Long> {

    // Buscar todos los salones cuyo nombre contenga un texto (ignora mayúsculas/minúsculas)
    fun findAllByNombreContainsIgnoreCase(nombre: String): List<Salon>

    // Buscar por id (ya viene en JpaRepository, pero puedes declararlo si quieres)
    override fun findById(id: Long): java.util.Optional<Salon>

    // Buscar todos por capacidad exacta
    fun findAllByCapacidad(capacidad: Int): List<Salon>
}

