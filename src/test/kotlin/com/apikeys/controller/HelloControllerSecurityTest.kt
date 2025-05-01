package com.apikeys.controller

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@SpringBootTest
@AutoConfigureMockMvc
class HelloControllerSecurityTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `should return 401 Unauthorized when API key is missing`() {
        mockMvc.get("/api/hello")
            .andExpect {
                status { isUnauthorized() }
            }
    }
}
