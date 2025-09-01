package co.edu.uniajc.AgendaSalonEventos.model

import jakarta.persistence.*
import java.time.LocalDate

@Entity
data class Evento(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val tipo: String,
    val descripcion: String,
    val fecha: LocalDate? = null
)
