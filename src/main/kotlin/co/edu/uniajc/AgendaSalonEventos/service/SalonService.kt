package co.edu.uniajc.AgendaSalonEventos.service

import co.edu.uniajc.AgendaSalonEventos.model.Salon
import co.edu.uniajc.AgendaSalonEventos.repository.SalonRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class SalonService @Autowired constructor(
    private val salonRepository: SalonRepository
) {

    fun createSalon(salon: Salon): Salon {
        return salonRepository.save(salon)
    }

    fun updateSalon(salon: Salon): Salon {
        return salonRepository.save(salon)
    }

    fun deleteSalon(id: Long) {
        salonRepository.deleteById(id)
    }

    fun findById(id: Long): Optional<Salon> {
        return salonRepository.findById(id)
    }

    fun findAllSalones(): List<Salon> {
        return salonRepository.findAll()
    }

    fun findAllSalonesByNombre(nombre: String): List<Salon> {
        return salonRepository.findAllByNombreContainsIgnoreCase(nombre)
    }

    fun findAllSalonesByCapacidad(capacidad: Int): List<Salon> {
        return salonRepository.findAllByCapacidad(capacidad)
    }
}
