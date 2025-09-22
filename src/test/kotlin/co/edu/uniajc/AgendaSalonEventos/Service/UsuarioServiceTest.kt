package co.edu.uniajc.AgendaSalonEventos.Service

import co.edu.uniajc.AgendaSalonEventos.model.Usuario
import co.edu.uniajc.AgendaSalonEventos.repository.UsuarioRepository
import co.edu.uniajc.AgendaSalonEventos.service.UsuarioService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import kotlin.test.assertEquals

@ExtendWith(MockitoExtension::class)
class UsuarioServiceTest {

    @Mock
    lateinit var usuarioRepository: UsuarioRepository

    @InjectMocks
    lateinit var usuarioService: UsuarioService

    private lateinit var usuario: Usuario

    @BeforeEach
    fun setup() {
        usuario = Usuario(
            id = 1L,
            nombre = "Vanessa",
            email = "vanessa@email.com",
            password = "12345",
            activo = true
        )
    }

    @Test
    fun `guardar usuario`() {
        Mockito.`when`(usuarioRepository.save(usuario)).thenReturn(usuario)

        val resultado = usuarioService.guardarUsuario(usuario)

        assertEquals(usuario, resultado)
        Mockito.verify(usuarioRepository, Mockito.times(1)).save(usuario)
    }

    @Test
    fun `obtener todos los usuarios`() {
        val lista = listOf(usuario)
        Mockito.`when`(usuarioRepository.findAll()).thenReturn(lista)

        val resultado = usuarioService.findAllUsuarios()

        assertEquals(1, resultado.size)
        assertEquals("Vanessa", resultado[0].nombre)
    }
}