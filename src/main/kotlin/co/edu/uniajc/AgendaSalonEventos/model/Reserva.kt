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

    @Column(name = "fecha_inicio", nullable = false)
    val fechaInicio: LocalDateTime,

    @Column(name = "fecha_fin", nullable = false)
    val fechaFin: LocalDateTime,

    @Column(name = "estado", nullable = false, length = 20)
    val estado: String = "PENDIENTE", // CONFIRMADA, CANCELADA, PENDIENTE

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    val usuario: Usuario,

    @ManyToOne
    @JoinColumn(name = "salon_id", nullable = false)
    val salon: Salon,

    @ManyToOne
    @JoinColumn(name = "evento_id", nullable = false)
    val evento: Evento
)