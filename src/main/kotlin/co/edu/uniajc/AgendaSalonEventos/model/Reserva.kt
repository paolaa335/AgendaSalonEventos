package co.edu.uniajc.AgendaSalonEventos.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "reserva")
data class Reserva(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reserva_id")
    val id: Long? = null,

    @Column(name = "fecha", nullable = false)
    val fecha: LocalDateTime,

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    val usuario: Usuario,

    @ManyToOne
    @JoinColumn(name = "salon_id", nullable = false)
    val salon: Salon
)
