package co.edu.uniajc.AgendaSalonEventos.controller

import co.edu.uniajc.AgendaSalonEventos.model.Evento
import co.edu.uniajc.AgendaSalonEventos.service.EventoService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/eventos")
class EventoController(private val eventoService: EventoService) {

    @GetMapping
    fun listar(): List<Evento> = eventoService.listarTodos()

    @GetMapping("/{id}")
    fun obtenerPorId(@PathVariable id: Long): ResponseEntity<Evento> {
        val evento = eventoService.buscarPorId(id)
        return if (evento != null) ResponseEntity.ok(evento)
        else ResponseEntity.notFound().build()
    }

    @PostMapping
    fun crear(@RequestBody evento: Evento): Evento = eventoService.crear(evento)

    @PutMapping("/{id}")
    fun actualizar(@PathVariable id: Long, @RequestBody evento: Evento): ResponseEntity<Evento> {
        val actualizado = eventoService.actualizar(id, evento)
        return if (actualizado != null) ResponseEntity.ok(actualizado)
        else ResponseEntity.notFound().build()
    }

    @DeleteMapping("/{id}")
    fun eliminar(@PathVariable id: Long): ResponseEntity<Void> {
        return if (eventoService.eliminar(id)) ResponseEntity.noContent().build()
        else ResponseEntity.notFound().build()
    }
}
