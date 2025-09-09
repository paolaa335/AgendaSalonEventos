package co.edu.uniajc.AgendaSalonEventos.mode

import co.edu.uniajc.AgendaSalonEventos.model.Salon
import co.edu.uniajc.AgendaSalonEventos.model.Usuario
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "evento")
data class Evento(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "evento_id")
    val id: Long? = null,

    @Column(name = "nombre", nullable = false, length = 250)
    val nombre: String,

    @Column(name = "descripcion", nullable = true, length = 500)
    val descripcion: String? = null,

    @Column(name = "fecha_inicio", nullable = false)
    val fechaInicio: LocalDateTime,

    @Column(name = "fecha_fin", nullable = false)
    val fechaFin: LocalDateTime,

    // Relación con Usuario (organizador del evento)
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    val usuario: Usuario,

    // Relación con Salón (dónde se realizará el evento)
    @ManyToOne
    @JoinColumn(name = "salon_id", nullable = false)
    val salon: Salon
)