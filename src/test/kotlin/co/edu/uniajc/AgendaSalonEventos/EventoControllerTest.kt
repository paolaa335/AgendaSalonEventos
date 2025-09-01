package co.edu.uniajc.AgendaSalonEventos.controller

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import co.edu.uniajc.AgendaSalonEventos.service.EventoService

@WebMvcTest(EventoController::class)
class EventoControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var eventoService: EventoService

    @Test
    fun `listar eventos devuelve status OK`() {
        mockMvc.get("/api/eventos")
            .andExpect { status { isOk() } }
    }
}
