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

    // Crear un nuevo salón
    fun createSalon(salon: Salon): Salon {
        return salonRepository.save(salon)
    }

    // Actualizar un salón existente
    fun updateSalon(salon: Salon): Salon {
        return salonRepository.save(salon)
    }

    // Eliminar un salón por ID
    fun deleteSalon(id: Long) {
        salonRepository.deleteById(id)
    }

    // Buscar salón por ID
    fun findById(id: Long): Optional<Salon> {
        return salonRepository.findById(id)
    }

    // Listar todos los salones
    fun findAllSalones(): List<Salon> {
        return salonRepository.findAll()
    }

    // Listar salones cuyo nombre contenga texto
    fun findAllSalonesByNombre(nombre: String): List<Salon> {
        return salonRepository.findAllByNombreContainsIgnoreCase(nombre)
    }

    // Listar salones por capacidad exacta
    fun findAllSalonesByCapacidad(capacidad: Int): List<Salon> {
        return salonRepository.findAllByCapacidad(capacidad)
    }
}
