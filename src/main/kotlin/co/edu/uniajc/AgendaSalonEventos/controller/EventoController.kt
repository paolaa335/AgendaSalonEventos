package co.edu.uniajc.AgendaSalonEventos.controller

import co.edu.uniajc.AgendaSalonEventos.model.Evento
import co.edu.uniajc.AgendaSalonEventos.service.EventoService
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/evento")
class EventoController @Autowired constructor(
    private val eventoService: EventoService
) {

    @PostMapping("/save")
    fun saveEvento(@RequestBody evento: Evento): Evento {
        return eventoService.createEvento(evento)
    }

    @PutMapping("/update")
    fun updateEvento(@RequestBody evento: Evento): Evento {
        return eventoService.updateEvento(evento)
    }

    @DeleteMapping("/delete")
    fun deleteEvento(@RequestParam(name = "id") id: Long) {
        eventoService.deleteEvento(id)
    }

    @GetMapping("/all")
    fun findAllEventos(): List<Evento> {
        return eventoService.findAllEventos()
    }

    @GetMapping("/id")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "400", description = "Petición inválida"),
            ApiResponse(responseCode = "404", description = "Evento no encontrado"),
            ApiResponse(responseCode = "500", description = "Error interno del servidor")
        ]
    )
    fun findById(@RequestParam(name = "id") idEvento: Long): ResponseEntity<Evento> {
        val evento = eventoService.findById(idEvento)
            ?: throw RuntimeException("Evento no encontrado")
        return ResponseEntity.ok(evento)
    }
}
