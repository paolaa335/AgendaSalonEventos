package co.edu.uniajc.AgendaSalonEventos.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class WebController {

    @GetMapping("/", "/app", "/reservas", "/reserva")
    fun serveFrontend(): String {
        return "forward:/index.html"
    }
}