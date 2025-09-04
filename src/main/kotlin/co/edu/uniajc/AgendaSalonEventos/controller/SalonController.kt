package co.edu.uniajc.AgendaSalonEventos.controller
import co.edu.uniajc.AgendaSalonEventos.model.Salon
import co.edu.uniajc.AgendaSalonEventos.service.SalonService
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/salon") // mismo estilo simple del profe (sin /api)
class SalonController @Autowired constructor(
    private val salonService: SalonService
) {

    @PostMapping(path = arrayOf("/save"))
    fun saveSalon(@RequestBody salon: Salon): Salon {
        return salonService.createSalon(salon)
    }

    @PutMapping(path = arrayOf("/update"))
    fun updateSalon(@RequestBody salon: Salon): Salon {
        return salonService.updateSalon(salon)
    }

    @DeleteMapping(path = arrayOf("/delete"))
    fun deleteSalon(@RequestParam(name = "id") id: Long) {
        salonService.deleteSalon(id)
    }

    @GetMapping(path = arrayOf("/all"))
    fun findAllSalon(): List<Salon> {
        return salonService.findAllSalones()
    }

    @GetMapping(path = arrayOf("/all/name"))
    fun findAllSalonByName(@RequestParam(name = "name") name: String): List<Salon> {
        return salonService.findAllSalonesByNombre(name)
    }

    // análogo a /all/age del profe; aquí usamos capacidad
    @GetMapping(path = arrayOf("/all/capacidad"))
    fun findAllSalonByCapacidad(@RequestParam(name = "capacidad") capacidad: Int): List<Salon> {
        return salonService.findAllSalonesByCapacidad(capacidad)
    }

    @GetMapping(path = arrayOf("/id"))
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "400", description = "Something Went Wrong"),
            ApiResponse(responseCode = "404", description = "No se encontro"),
            ApiResponse(responseCode = "500", description = "Internal Server Error")
        ]
    )
    fun findById(@RequestParam(name = "id") idSalon: Long): ResponseEntity<Salon> {
        val salon = salonService.findById(idSalon)
            .orElseThrow { RuntimeException("No se encontro") }
        return ResponseEntity.ok(salon)
    }
}
