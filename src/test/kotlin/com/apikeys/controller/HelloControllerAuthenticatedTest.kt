package com.apikeys.controller

import com.apikeys.dto.ApiKeyCreationDto
import com.apikeys.service.ApiKeyService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@SpringBootTest
@AutoConfigureMockMvc
class HelloControllerAuthenticatedTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var apiKeyService: ApiKeyService

    @Test
    fun `should return 200 OK when valid API key is provided`() {
        // Crea dinamicamente una API key valida
        val apiKeyDto = apiKeyService.createApiKey(
            ApiKeyCreationDto(
                projectId = "test-project",
                owner = "test-user",
                expirationDays = 30
            )
        )

        mockMvc.get("/api/hello") {
            header("X-API-KEY", apiKeyDto.key)
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            jsonPath("$.user") { value("test-user") }
            jsonPath("$.message") { value("Hello, authenticated user!") }
        }
    }
}
