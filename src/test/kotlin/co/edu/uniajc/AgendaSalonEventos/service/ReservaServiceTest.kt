package co.edu.uniajc.AgendaSalonEventos.service

import co.edu.uniajc.AgendaSalonEventos.exception.CapacidadExcedidaException
import co.edu.uniajc.AgendaSalonEventos.exception.SalonNoDisponibleException
import co.edu.uniajc.AgendaSalonEventos.model.Reserva
import co.edu.uniajc.AgendaSalonEventos.model.Usuario
import co.edu.uniajc.AgendaSalonEventos.model.Salon
import co.edu.uniajc.AgendaSalonEventos.repository.ReservaRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.time.LocalDate
import java.time.LocalTime

class ReservaServiceTest {

    private lateinit var reservaRepository: ReservaRepository
    private lateinit var reservaService: ReservaService

    private lateinit var salon: Salon
    private lateinit var usuario: Usuario

    @BeforeEach
    fun setup() {
        reservaRepository = mock(ReservaRepository::class.java)
        reservaService = ReservaService(reservaRepository)

        salon = Salon(nombre = "Salon Principal", capacidad = 50)
        usuario = Usuario(nombre = "Yeison", correo = "yeison@test.com", contrasena = "1234")
    }

    @Test
    fun `crearReserva - caso exitoso`() {
        val reserva = Reserva(
            salon = salon,
            usuario = usuario,
            fecha = LocalDate.of(2025, 9, 1),
            horaInicio = LocalTime.of(10, 0),
            horaFin = LocalTime.of(12, 0),
            cantidadPersonas = 20
        )

        `when`(reservaRepository.findAll()).thenReturn(emptyList())
        `when`(reservaRepository.save(reserva)).thenReturn(reserva)

        val resultado = reservaService.crearReserva(reserva)

        assertEquals(20, resultado.cantidadPersonas)
        assertEquals("Salon Principal", resultado.salon.nombre)
    }

    @Test
    fun `crearReserva - debe fallar por cruce de horario`() {
        val reservaExistente = Reserva(
            salon = salon,
            usuario = usuario,
            fecha = LocalDate.of(2025, 9, 1),
            horaInicio = LocalTime.of(11, 0),
            horaFin = LocalTime.of(13, 0),
            cantidadPersonas = 30
        )

        val nuevaReserva = Reserva(
            salon = salon,
            usuario = usuario,
            fecha = LocalDate.of(2025, 9, 1),
            horaInicio = LocalTime.of(10, 30),
            horaFin = LocalTime.of(11, 30),
            cantidadPersonas = 20
        )

        `when`(reservaRepository.findAll()).thenReturn(listOf(reservaExistente))

        assertThrows(SalonNoDisponibleException::class.java) {
            reservaService.crearReserva(nuevaReserva)
        }
    }

    @Test
    fun `crearReserva - debe fallar por capacidad insuficiente`() {
        val reserva = Reserva(
            salon = salon,
            usuario = usuario,
            fecha = LocalDate.of(2025, 9, 1),
            horaInicio = LocalTime.of(14, 0),
            horaFin = LocalTime.of(15, 0),
            cantidadPersonas = 100 // excede capacidad
        )

        `when`(reservaRepository.findAll()).thenReturn(emptyList())

        assertThrows(CapacidadExcedidaException::class.java) {
            reservaService.crearReserva(reserva)
        }
    }
}
