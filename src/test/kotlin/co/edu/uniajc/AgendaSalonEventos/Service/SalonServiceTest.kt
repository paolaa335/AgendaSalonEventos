package co.edu.uniajc.AgendaSalonEventos.service

import co.edu.uniajc.AgendaSalonEventos.service.SalonService
import co.edu.uniajc.AgendaSalonEventos.model.Salon
import co.edu.uniajc.AgendaSalonEventos.repository.SalonRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.math.BigDecimal
import java.util.*

@ExtendWith(SpringExtension::class)
class SalonServiceTest {

    @Mock
    lateinit var salonRepositoryMock: SalonRepository

    private lateinit var salonService: SalonService

    @BeforeEach
    fun setup() {
        salonService = SalonService(salonRepositoryMock)
    }

    @Test
    fun `Expect_CreateSalon_In_DB`() {

        val salon = Salon(
            id = 1L,
            nombre = "Salón Principal",
            direccion = "Calle 10 #5-20",
            capacidad = 120,
            precioBase = BigDecimal("500000.00"),
            activo = true
        )

        given(salonRepositoryMock.save(salon)).willReturn(salon)


        val creado = salonService.createSalon(salon)


        assertEquals(1L, creado.id)
        assertEquals("Salón Principal", creado.nombre)
    }

    @Test
    fun `FindById_Return_Salon`() {

        val id = 10L
        val salon = Salon(
            id = id,
            nombre = "Salón VIP",
            direccion = "Av. Central 123",
            capacidad = 80,
            precioBase = BigDecimal("300000.00"),
            activo = true
        )
        given(salonRepositoryMock.findById(id)).willReturn(Optional.of(salon))


        val encontrado = salonService.findById(id).orElseThrow()


        assertEquals(id, encontrado.id)
        assertEquals("Salón VIP", encontrado.nombre)
    }

    @Test
    fun `UpdateSalon_Return_UpdatedSalon`() {
        val salonOriginal = Salon(
            id = 1L,
            nombre = "Salón Original",
            direccion = "Calle 1",
            capacidad = 100,
            precioBase = BigDecimal("300000.00"),
            activo = true
        )
        
        val salonActualizado = Salon(
            id = 1L,
            nombre = "Salón Actualizado",
            direccion = "Calle 2",
            capacidad = 150,
            precioBase = BigDecimal("400000.00"),
            activo = true
        )

        given(salonRepositoryMock.save(salonActualizado)).willReturn(salonActualizado)

        val resultado = salonService.updateSalon(salonActualizado)

        assertEquals("Salón Actualizado", resultado.nombre)
        assertEquals(150, resultado.capacidad)
    }

    @Test
    fun `DeleteSalon_Verify_RepositoryCall`() {
        val id = 1L

        // Verificar que no lance excepciones
        salonService.deleteSalon(id)

        // Verificar que se llamó al método delete
        org.mockito.Mockito.verify(salonRepositoryMock).deleteById(id)
    }

    @Test
    fun `FindAllSalones_Return_AllSalones`() {
        val salones = listOf(
            Salon(id = 1L, nombre = "Salón A", direccion = "Calle A", capacidad = 100, precioBase = BigDecimal("300000.00"), activo = true),
            Salon(id = 2L, nombre = "Salón B", direccion = "Calle B", capacidad = 200, precioBase = BigDecimal("500000.00"), activo = true)
        )

        given(salonRepositoryMock.findAll()).willReturn(salones)

        val resultado = salonService.findAllSalones()

        assertEquals(2, resultado.size)
        assertEquals("Salón A", resultado[0].nombre)
    }

    @Test
    fun `FindAllSalonesByNombre_Return_FilteredSalones`() {
        val salones = listOf(
            Salon(id = 1L, nombre = "Salón VIP", direccion = "Calle VIP", capacidad = 100, precioBase = BigDecimal("300000.00"), activo = true),
            Salon(id = 2L, nombre = "Salón Premium", direccion = "Calle Premium", capacidad = 200, precioBase = BigDecimal("500000.00"), activo = true)
        )

        given(salonRepositoryMock.findAllByNombreContainsIgnoreCase("VIP")).willReturn(listOf(salones[0]))

        val resultado = salonService.findAllSalonesByNombre("VIP")

        assertEquals(1, resultado.size)
        assertEquals("Salón VIP", resultado[0].nombre)
    }

    @Test
    fun `FindAllSalonesByCapacidad_Return_FilteredSalones`() {
        val salones = listOf(
            Salon(id = 1L, nombre = "Salón Pequeño", direccion = "Calle Pequeña", capacidad = 50, precioBase = BigDecimal("200000.00"), activo = true),
            Salon(id = 2L, nombre = "Salón Grande", direccion = "Calle Grande", capacidad = 200, precioBase = BigDecimal("500000.00"), activo = true)
        )

        given(salonRepositoryMock.findAllByCapacidad(50)).willReturn(listOf(salones[0]))

        val resultado = salonService.findAllSalonesByCapacidad(50)

        assertEquals(1, resultado.size)
        assertEquals("Salón Pequeño", resultado[0].nombre)
        assertEquals(50, resultado[0].capacidad)
    }
}
