package co.edu.uniajc.AgendaSalonEventos.model

import jakarta.persistence.*
import java.time.LocalDate

@Entity
data class Reserva(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val fecha: LocalDate,

    @ManyToOne
    @JoinColumn(name = "salon_id", nullable = false)
    val salon: Salon,

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    val usuario: Usuario
)
