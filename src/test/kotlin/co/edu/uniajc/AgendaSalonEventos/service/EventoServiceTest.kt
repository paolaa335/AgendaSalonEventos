package co.edu.uniajc.AgendaSalonEventos.service

import co.edu.uniajc.AgendaSalonEventos.model.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class EventoServiceTest {

    private lateinit var eventoService: EventoService
    private lateinit var salon: Salon
    private lateinit var usuario: Usuario

    @BeforeEach
    fun setUp() {
        eventoService = EventoService()
        salon = Salon(id = 1, nombre = "Salon Principal", capacidad = 50)
        usuario = Usuario(id = 1, nombre = "Yeison", correo = "yeison@mail.com", contrasena = "12345")
    }

    @Test
    fun `crear evento debe agregarlo a la lista`() {
        val evento = Evento(
            id = 1,
            nombre = "Cumpleaños",
            descripcion = "Fiesta sorpresa",
            fecha = "2024-09-10",
            horaInicio = "18:00",
            horaFin = "22:00",
            salon = salon,
            usuario = usuario
        )

        val resultado = eventoService.crearEvento(evento)
        assertEquals(evento, resultado)
        assertEquals(1, eventoService.listarEventos().size)
    }

    @Test
    fun `buscarPorId debe retornar el evento correcto`() {
        val evento = Evento(1, "Reunión", "Trabajo", "2024-09-15", "10:00", "12:00", salon, usuario)
        eventoService.crearEvento(evento)

        val encontrado = eventoService.buscarPorId(1)
        assertNotNull(encontrado)
        assertEquals("Reunión", encontrado?.nombre)
    }

    @Test
    fun `eliminarEvento debe borrar el evento`() {
        val evento = Evento(1, "Boda", "Ceremonia", "2024-12-01", "14:00", "20:00", salon, usuario)
        eventoService.crearEvento(evento)

        val eliminado = eventoService.eliminarEvento(1)
        assertTrue(eliminado)
        assertEquals(0, eventoService.listarEventos().size)
    }
}
