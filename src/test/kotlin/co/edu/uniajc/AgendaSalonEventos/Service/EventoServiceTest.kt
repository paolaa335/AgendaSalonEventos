package co.edu.uniajc.AgendaSalonEventos.Service

import co.edu.uniajc.AgendaSalonEventos.mode.Evento
import co.edu.uniajc.AgendaSalonEventos.model.Salon
import co.edu.uniajc.AgendaSalonEventos.model.Usuario
import co.edu.uniajc.AgendaSalonEventos.repository.EventoRepository
import co.edu.uniajc.AgendaSalonEventos.service.EventoService
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
class EventoServiceTest {

    @Mock
    lateinit var eventoRepository: EventoRepository

    @InjectMocks
    lateinit var eventoService: EventoService

    private lateinit var salon: Salon
    private lateinit var usuario: Usuario
    private lateinit var evento: Evento

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
            nombre = "Conferencia Kotlin",
            descripcion = "Evento de prueba",
            fechaInicio = LocalDateTime.of(2025, 9, 15, 10, 0),
            fechaFin = LocalDateTime.of(2025, 9, 15, 12, 0),
            usuario = usuario,
            salon = salon
        )
    }

    @Test
    fun `guardar evento`() {
        Mockito.`when`(eventoRepository.save(evento)).thenReturn(evento)

        val resultado = eventoService.createEvento(evento)

        assertEquals(evento, resultado)
        Mockito.verify(eventoRepository, Mockito.times(1)).save(evento)
    }

    @Test
    fun `obtener todos los eventos`() {
        val listaEventos = listOf(evento)
        Mockito.`when`(eventoRepository.findAll()).thenReturn(listaEventos)

        val resultado = eventoService.findAllEventos()

        assertEquals(1, resultado.size)

    }
}