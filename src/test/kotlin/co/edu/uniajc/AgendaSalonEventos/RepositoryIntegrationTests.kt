package co.edu.uniajc.AgendaSalonEventos

import co.edu.uniajc.AgendaSalonEventos.model.*
import co.edu.uniajc.AgendaSalonEventos.repository.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import java.time.LocalDate

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ActiveProfiles("test")
class RepositoryIntegrationTests {

    @Autowired
    lateinit var salonRepository: SalonRepository
    @Autowired
    lateinit var usuarioRepository: UsuarioRepository
    @Autowired
    lateinit var reservaRepository: ReservaRepository
    @Autowired
    lateinit var eventoRepository: EventoRepository

    // ---------- SALON ----------
    @Test
    fun `guardar y buscar salon`() {
        val salon = Salon(nombre = "Salón Dorado", capacidad = 100, ubicacion = "Cali")
        val saved = salonRepository.save(salon)

        val encontrado = salonRepository.findById(saved.id!!)
        assertTrue(encontrado.isPresent)
        assertEquals("Salón Dorado", encontrado.get().nombre)
        assertEquals(100, encontrado.get().capacidad)
        assertEquals("Cali", encontrado.get().ubicacion)
    }

    // ---------- USUARIO ----------
    @Test
    fun `guardar y buscar usuario por email`() {
        val usuario = Usuario(nombre = "Vanessa Ruiz", email = "vanessa@correo.com")
        usuarioRepository.save(usuario)

        val encontrado = usuarioRepository.findByEmail("vanessa@correo.com")
        assertNotNull(encontrado)
        assertEquals("Vanessa Ruiz", encontrado?.nombre)
        assertEquals("vanessa@correo.com", encontrado?.email)
    }

    // ---------- EVENTO ----------
    @Test
    fun `guardar y buscar evento por tipo`() {
        val evento = Evento(tipo = "Boda", descripcion = "Boda en Cali", fecha = LocalDate.of(2025, 12, 1))
        eventoRepository.save(evento)

        val encontrados = eventoRepository.findByTipo("Boda")
        assertFalse(encontrados.isEmpty())
        val e = encontrados[0]
        assertEquals("Boda", e.tipo)
        assertEquals("Boda en Cali", e.descripcion)
        assertEquals(LocalDate.of(2025, 12, 1), e.fecha)
    }

    // ---------- RESERVA ----------
    @Test
    fun `guardar reserva y validar cruce de fechas`() {
        val salon = salonRepository.save(Salon(nombre = "Salón VIP", capacidad = 50, ubicacion = "Sur"))
        val usuario = usuarioRepository.save(Usuario(nombre = "Carlos Pérez", email = "carlos@correo.com"))

        val fecha = LocalDate.of(2025, 9, 10)

        val reserva = Reserva(fecha = fecha, salon = salon, usuario = usuario)
        reservaRepository.save(reserva)

        val reservas = reservaRepository.findReservasBySalonAndFecha(salon.id!!, fecha)
        assertEquals(1, reservas.size)
        assertEquals(usuario.id, reservas[0].usuario.id)
        assertEquals("Salón VIP", reservas[0].salon.nombre)
        assertEquals(fecha, reservas[0].fecha)
    }
}
