package co.edu.uniajc.AgendaSalonEventos.service

import co.edu.uniajc.AgendaSalonEventos.model.Usuario
import co.edu.uniajc.AgendaSalonEventos.repository.UsuarioRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*

class UsuarioServiceTest {

    private lateinit var usuarioRepository: UsuarioRepository
    private lateinit var usuarioService: UsuarioService

    @BeforeEach
    fun setup() {
        usuarioRepository = mock(UsuarioRepository::class.java)
        usuarioService = UsuarioService(usuarioRepository)
    }

    @Test
    fun `crearUsuario - caso exitoso`() {
        val usuario = Usuario(nombre = "Yeison", correo = "yeison@test.com", contrasena = "1234")

        `when`(usuarioRepository.existsByCorreo(usuario.correo)).thenReturn(false)
        `when`(usuarioRepository.save(usuario)).thenReturn(usuario)

        val resultado = usuarioService.crearUsuario(usuario)

        assertEquals("Yeison", resultado.nombre)
        assertEquals("yeison@test.com", resultado.correo)
    }

    @Test
    fun `crearUsuario - debe fallar por correo duplicado`() {
        val usuario = Usuario(nombre = "Paola", correo = "paola@test.com", contrasena = "abcd")

        `when`(usuarioRepository.existsByCorreo(usuario.correo)).thenReturn(true)

        assertThrows(IllegalArgumentException::class.java) {
            usuarioService.crearUsuario(usuario)
        }
    }
}
