package co.edu.uniajc.AgendaSalonEventos.controller

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import co.edu.uniajc.AgendaSalonEventos.service.ReservaService

@WebMvcTest(ReservaController::class)
class ReservaControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var reservaService: ReservaService

    @Test
    fun `listar reservas devuelve status OK`() {
        mockMvc.get("/api/reservas")
            .andExpect { status { isOk() } }
    }
}
