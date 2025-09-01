package co.edu.uniajc.AgendaSalonEventos.model

import jakarta.persistence.*

@Entity
data class Salon(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val nombre: String,

    @Column(nullable = false)
    val capacidad: Int,

    @Column(nullable = false)
    val ubicacion: String
)
