package co.edu.uniajc.AgendaSalonEventos.Service

import co.edu.uniajc.AgendaSalonEventos.model.Salon
import co.edu.uniajc.AgendaSalonEventos.repository.SalonRepository
import co.edu.uniajc.AgendaSalonEventos.service.SalonService
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
        // Arrange
        val salon = Salon(
            id = 1L,
            nombre = "Salón Principal",
            direccion = "Calle 10 #5-20",
            capacidad = 120,
            precioBase = BigDecimal("500000.00"),
            activo = true
        )

        given(salonRepositoryMock.save(salon)).willReturn(salon)

        // Act
        val creado = salonService.createSalon(salon)

        // Assert
        assertEquals(1L, creado.id)
        assertEquals("Salón Principal", creado.nombre)
    }

    @Test
    fun `FindById_Return_Salon`() {
        // Arrange
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

        // Act
        val encontrado = salonService.findById(id).orElseThrow()

        // Assert
        assertEquals(id, encontrado.id)
        assertEquals("Salón VIP", encontrado.nombre)
    }
}
