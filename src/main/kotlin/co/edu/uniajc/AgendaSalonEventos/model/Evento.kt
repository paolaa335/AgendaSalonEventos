package co.edu.uniajc.AgendaSalonEventos.model

import jakarta.persistence.* 
import java.time.LocalDateTime
import co.edu.uniajc.AgendaSalonEventos.model.Salon
import co.edu.uniajc.AgendaSalonEventos.model.Usuario

@Entity
@Table(name = "eventos")
data class Evento(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "salon_id", nullable = false)
    val salon: Salon,

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    val usuario: Usuario,

    val nombre: String,
    val fecha: LocalDateTime,
    val horaInicio: String,
    val horaFin: String,
    val cantidadPersonas: Int
)