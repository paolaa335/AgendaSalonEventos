package co.edu.uniajc.AgendaSalonEventos.model

import jakarta.persistence.*

@Entity
@Table(
    name = "usuarios",
    uniqueConstraints = [UniqueConstraint(columnNames = ["correo"])]
)
class Usuario(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var nombre: String,

    @Column(nullable = false, unique = true)
    var correo: String,

    var contrasena: String
)
