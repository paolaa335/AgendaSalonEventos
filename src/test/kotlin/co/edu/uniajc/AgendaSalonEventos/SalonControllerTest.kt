package co.edu.uniajc.AgendaSalonEventos.controller

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@WebMvcTest(SalonController::class)
class SalonControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `listar salones devuelve status OK`() {
        mockMvc.get("/api/salones")
            .andExpect { status { isOk() } }
    }
}
