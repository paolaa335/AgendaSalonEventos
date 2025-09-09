package co.edu.uniajc.AgendaSalonEventos.controller

import co.edu.uniajc.AgendaSalonEventos.model.Reserva
import co.edu.uniajc.AgendaSalonEventos.service.ReservaService
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/reserva")
class ReservaController @Autowired constructor(
    private val reservaService: ReservaService
) {

    @PostMapping("/save")
    fun saveReserva(@RequestBody reserva: Reserva): Reserva {
        return reservaService.createReserva(reserva)
    }

    @PutMapping("/update")
    fun updateReserva(@RequestBody reserva: Reserva): Reserva {
        return reservaService.updateReserva(reserva)
    }

    @DeleteMapping("/delete")
    fun deleteReserva(@RequestParam(name = "id") id: Long) {
        reservaService.deleteReserva(id)
    }

    @GetMapping("/all")
    fun findAllReservas(): List<Reserva> {
        return reservaService.findAllReservas()
    }

    @GetMapping("/id")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "400", description = "Petición inválida"),
            ApiResponse(responseCode = "404", description = "Reserva no encontrada"),
            ApiResponse(responseCode = "500", description = "Error interno del servidor")
        ]
    )
    fun findById(@RequestParam(name = "id") idReserva: Long): ResponseEntity<Reserva> {
        val reserva = reservaService.findById(idReserva)
            ?: throw RuntimeException("Reserva no encontrada")
        return ResponseEntity.ok(reserva)
    }
}