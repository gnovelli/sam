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
import java.time.LocalDateTime
import com.apikeys.repository.ApiKeyRepository

@SpringBootTest
@AutoConfigureMockMvc
class HelloControllerInvalidKeysTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var apiKeyService: ApiKeyService

    @Autowired
    private lateinit var apiKeyRepository: ApiKeyRepository

    @Test
    fun `should return 401 when API key is deactivated`() {
        val dto = ApiKeyCreationDto("test-project", "user-deactivated", 30)
        val key = apiKeyService.createApiKey(dto)
        apiKeyService.deactivateApiKey(key.id)

        mockMvc.get("/api/hello") {
            header("X-API-KEY", key.key)
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isUnauthorized() }
        }
    }

    @Test
    fun `should return 401 when API key is expired`() {
        val dto = ApiKeyCreationDto("test-project", "user-expired", 1)
        val key = apiKeyService.createApiKey(dto)

        // Forza la scadenza nel passato (hack diretto sul repository)
        val expiredKey = apiKeyRepository.findById(key.id)!!.copy(
            expiresAt = LocalDateTime.now().minusDays(1),
            isActive = true
        )
        apiKeyRepository.update(expiredKey)

        mockMvc.get("/api/hello") {
            header("X-API-KEY", key.key)
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isUnauthorized() }
        }
    }
}
