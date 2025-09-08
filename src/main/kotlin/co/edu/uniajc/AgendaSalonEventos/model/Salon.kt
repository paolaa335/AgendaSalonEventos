package co.edu.uniajc.AgendaSalonEventos.model

import jakarta.persistence.*

@Entity
@Table(name = "salones")
class Salon(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var nombre: String,
    var capacidad: Int
)
