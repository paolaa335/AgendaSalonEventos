package co.edu.uniajc.AgendaSalonEventos.model

import jakarta.persistence.*

@Entity
@Table(name = "usuario")
data class Usuario(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id")
    val id: Long? = null,

    @Column(name = "nombre", nullable = false, length = 250)
    val nombre: String,

    @Column(name = "email", nullable = false, unique = true, length = 250)
    val email: String,

    @Column(name = "password", nullable = false, length = 250)
    val password: String,

    @Column(name = "activo", nullable = false)
    val activo: Boolean = true
)