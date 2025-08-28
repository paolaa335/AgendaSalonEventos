package co.edu.uniajc.AgendaSalonEventos.controller

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/salones")
class SalonController {

    // GET /api/salones → devuelve una lista de salones simulada
    @GetMapping
    fun listarSalones(): List<String> {
        return listOf("Salón Principal", "Salón VIP")
    }

    // GET /api/salones/{id} → devuelve un salón específico por id
    @GetMapping("/{id}")
    fun obtenerSalon(@PathVariable id: Long): String {
        return "Salón con id: $id"
    }

    // POST /api/salones → crea un salón (simulado)
    @PostMapping
    fun crearSalon(@RequestBody salon: Map<String, Any>): Map<String, Any> {
        return salon
    }

    // DELETE /api/salones/{id} → elimina un salón (simulado)
    @DeleteMapping("/{id}")
    fun eliminarSalon(@PathVariable id: Long): String {
        return "Salón con id $id eliminado"
    }
}
