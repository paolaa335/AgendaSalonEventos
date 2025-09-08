package co.edu.uniajc.AgendaSalonEventos.service

import co.edu.uniajc.AgendaSalonEventos.model.Salon
import co.edu.uniajc.AgendaSalonEventos.repository.SalonRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.math.BigDecimal

class SalonServiceTest {

    private val salonRepository: SalonRepository = mock(SalonRepository::class.java)
    private val salonService: SalonService = SalonService(salonRepository)

    @Test
    fun `crear salon deberia guardarlo en el repositorio`() {
        // Arrange
        val salon = Salon(
            id = null,
            nombre = "Salon Principal",
            direccion = "Calle 123",
            capacidad = 100,
            precioBase = BigDecimal("500000.00"),
            activo = true
        )
        `when`(salonRepository.save(salon)).thenReturn(salon)

        // Act
        val result = salonService.createSalon(salon)

        // Assert
        assertNotNull(result)
        assertEquals("Salon Principal", result.nombre)
        verify(salonRepository, times(1)).save(salon)
    }
}
