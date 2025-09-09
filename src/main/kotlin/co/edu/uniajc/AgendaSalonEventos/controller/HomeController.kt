package co.edu.uniajc.AgendaSalonEventos.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HomeController {

    @GetMapping("/")
    fun index(): Map<String, String> = mapOf(
        "app" to "AgendaSalonEventos",
        "status" to "ok",
        "version" to "v1"
    )
}
