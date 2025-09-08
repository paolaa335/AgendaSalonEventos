package co.edu.uniajc.AgendaSalonEventos.model

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "salon")
data class Salon(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "salon_id")
    val id: Long? = null,

    @Column(name = "nombre", nullable = false, length = 250)
    val nombre: String,

    @Column(name = "direccion", nullable = false, length = 250)
    val direccion: String,

    @Column(name = "capacidad", nullable = false)
    val capacidad: Int,

    @Column(name = "precio_base", nullable = false, precision = 12, scale = 2)
    val precioBase: BigDecimal,

    @Column(name = "activo", nullable = false)
    val activo: Boolean = true
)

