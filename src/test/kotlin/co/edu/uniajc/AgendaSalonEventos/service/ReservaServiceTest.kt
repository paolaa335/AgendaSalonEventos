package co.edu.uniajc.AgendaSalonEventos.service

import co.edu.uniajc.AgendaSalonEventos.model.Reserva
import co.edu.uniajc.AgendaSalonEventos.model.Salon
import co.edu.uniajc.AgendaSalonEventos.model.Usuario
import co.edu.uniajc.AgendaSalonEventos.model.Evento
import co.edu.uniajc.AgendaSalonEventos.repository.ReservaRepository
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
import kotlin.test.assertNotNull

@ExtendWith(MockitoExtension::class)
class ReservaServiceTest {

    @Mock
    lateinit var reservaRepository: ReservaRepository

    @InjectMocks
    lateinit var reservaService: ReservaService

    private lateinit var salon: Salon
    private lateinit var usuario: Usuario
    private lateinit var evento: Evento
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

        evento = Evento(
            id = 1L,
            nombre = "Boda de Vanessa",
            descripcion = "Ceremonia de boda",
            fechaInicio = LocalDateTime.of(2025, 12, 20, 14, 0),
            fechaFin = LocalDateTime.of(2025, 12, 20, 18, 0),
            usuario = usuario,
            salon = salon
        )

        reserva = Reserva(
            id = 1L,
            fechaInicio = LocalDateTime.of(2025, 12, 20, 14, 0),
            fechaFin = LocalDateTime.of(2025, 12, 20, 16, 0),
            estado = "PENDIENTE",
            usuario = usuario,
            salon = salon,
            evento = evento
        )
    }

    @Test
    fun `guardar reserva`() {
        // SOLUCIÓN: No mockear existsReservaSolapada, dejar que pase la validación
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
        assertEquals("PENDIENTE", resultado[0].estado)
    }

    @Test
    fun `encontrar reserva por id`() {
        Mockito.`when`(reservaRepository.findById(1L)).thenReturn(java.util.Optional.of(reserva))

        val resultado = reservaService.findById(1L)

        assertNotNull(resultado)
        assertEquals(1L, resultado!!.id)  // CORRECCIÓN: Eliminar safe call
        assertEquals("PENDIENTE", resultado.estado)
    }

    @Test
    fun `cancelar reserva exitosa`() {
        Mockito.`when`(reservaRepository.findById(1L)).thenReturn(java.util.Optional.of(reserva))
        Mockito.`when`(reservaRepository.save(Mockito.any(Reserva::class.java))).thenAnswer { it.arguments[0] as Reserva }

        val resultado = reservaService.cancelarReserva(1L)

        assertEquals("CANCELADA", resultado.estado)
    }

    @Test
    fun `confirmar reserva exitosa`() {
        Mockito.`when`(reservaRepository.findById(1L)).thenReturn(java.util.Optional.of(reserva))
        Mockito.`when`(reservaRepository.save(Mockito.any(Reserva::class.java))).thenAnswer { it.arguments[0] as Reserva }

        val resultado = reservaService.confirmarReserva(1L)

        assertEquals("CONFIRMADA", resultado.estado)
    }

    @Test
    fun `actualizar reserva`() {
        // SOLUCIÓN: No mockear existsReservaSolapada
        val reservaActualizada = reserva.copy(estado = "CONFIRMADA")
        Mockito.`when`(reservaRepository.save(Mockito.any(Reserva::class.java))).thenReturn(reservaActualizada)

        val resultado = reservaService.updateReserva(reservaActualizada)

        assertEquals("CONFIRMADA", resultado.estado)
    }
}