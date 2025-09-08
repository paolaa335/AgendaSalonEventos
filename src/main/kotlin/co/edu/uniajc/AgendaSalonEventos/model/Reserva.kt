package co.edu.uniajc.AgendaSalonEventos.model

import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalTime

@Entity
class Reserva(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne
    var salon: Salon,

    @ManyToOne
    var usuario: Usuario,

    var fecha: LocalDate,
    var horaInicio: LocalTime,
    var horaFin: LocalTime,
    var cantidadPersonas: Int
)
