package co.edu.uniajc.AgendaSalonEventos.Service

import co.edu.uniajc.AgendaSalonEventos.model.Reserva
import co.edu.uniajc.AgendaSalonEventos.model.Salon
import co.edu.uniajc.AgendaSalonEventos.model.Usuario
import co.edu.uniajc.AgendaSalonEventos.repository.ReservaRepository
import co.edu.uniajc.AgendaSalonEventos.service.ReservaService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import java.math.BigDecimal
import java.time.LocalDateTime
import kotlin.test.assertEquals

@ExtendWith(MockitoExtension::class)
class ReservaServiceTest {

    @Mock
    lateinit var reservaRepository: ReservaRepository

    @InjectMocks
    lateinit var reservaService: ReservaService

    private lateinit var salon: Salon
    private lateinit var usuario: Usuario
    private lateinit var reserva: Reserva

    @BeforeEach
    fun setup() {
        salon = Salon(
            id = 1L,
            nombre = "Salón Principal",
            direccion = "Calle 123",
            capacidad = 100,
            precioBase = BigDecimal("500000.00"),
            activo = true
        )

        usuario = Usuario(
            id = 1L,
            nombre = "Vanessa",
            email = "vanessa@email.com",
            password = "12345",
            activo = true
        )

        reserva = Reserva(
            id = 1L,
            fecha = LocalDateTime.of(2025, 9, 20, 14, 0),
            usuario = usuario,
            salon = salon
        )
    }

    @Test
    fun `guardar reserva`() {
        Mockito.`when`(reservaRepository.save(reserva)).thenReturn(reserva)

        val resultado = reservaService.createReserva(reserva)

        assertEquals(reserva, resultado)
        Mockito.verify(reservaRepository, Mockito.times(1)).save(reserva)
    }

    @Test
    fun `obtener todas las reservas`() {
        val listaReservas = listOf(reserva)
        Mockito.`when`(reservaRepository.findAll()).thenReturn(listaReservas)

        val resultado = reservaService.findAllReservas()

        assertEquals(1, resultado.size)
        assertEquals("Vanessa", resultado[0].usuario.nombre)
        assertEquals("Salón Principal", resultado[0].salon.nombre)
    }
}