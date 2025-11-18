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

    @PutMapping("/cancelar")
    fun cancelarReserva(@RequestParam(name = "id") id: Long): Reserva {
        return reservaService.cancelarReserva(id)
    }

    @PutMapping("/confirmar")
    fun confirmarReserva(@RequestParam(name = "id") id: Long): Reserva {
        return reservaService.confirmarReserva(id)
    }

    @GetMapping("/all")
    fun findAllReservas(): List<Reserva> {
        return reservaService.findAllReservas()
    }

    @GetMapping("/activas")
    fun findReservasActivas(): List<Reserva> {
        return reservaService.findReservasActivas()
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

    @GetMapping("/usuario")
    fun findByUsuario(@RequestParam(name = "usuarioId") usuarioId: Long): List<Reserva> {
        return reservaService.findByUsuario(usuarioId)
    }

    @GetMapping("/salon")
    fun findBySalon(@RequestParam(name = "salonId") salonId: Long): List<Reserva> {
        return reservaService.findBySalon(salonId)
    }

    @GetMapping("/disponibilidad")
    fun checkDisponibilidad(
        @RequestParam(name = "salonId") salonId: Long,
        @RequestParam(name = "fechaInicio") fechaInicio: String,
        @RequestParam(name = "fechaFin") fechaFin: String
    ): ResponseEntity<Map<String, Boolean>> {
        // Implementar parsing de fechas y validación
        val disponible = true // Placeholder
        return ResponseEntity.ok(mapOf("disponible" to disponible))
    }
}