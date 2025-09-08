package co.edu.uniajc.AgendaSalonEventos.controller

import co.edu.uniajc.AgendaSalonEventos.exception.CapacidadExcedidaException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/reservas")
class ReservaController(private val reservaService: ReservaService) {

    @PostMapping
    fun crearReserva(@RequestBody reserva: Reserva): ResponseEntity<Any> {
        return try {
            val nuevaReserva = reservaService.crearReserva(reserva)
            ResponseEntity.ok(nuevaReserva)
        } catch (e: CapacidadExcedidaException) {
            ResponseEntity.badRequest().body(mapOf("error" to e.message))
        }
        catch (e: SalonNoDisponibleException) {
        ResponseEntity.badRequest().body(mapOf("error" to e.message))
    }
    }

    @GetMapping
    fun listarReservas(): List<Reserva> = reservaService.listarReservas()
}
