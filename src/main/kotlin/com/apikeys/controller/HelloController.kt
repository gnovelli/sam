package com.apikeys.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
@RequestMapping("/api")
@Tag(name = "API Endpoints", description = "Example API endpoints secured with API keys")
class HelloController {

    @GetMapping("/hello")
    @Operation(
        summary = "Hello endpoint",
        description = "A simple hello endpoint that requires API key authentication",
        security = [SecurityRequirement(name = "apiKey")]
    )
    fun hello(authentication: Authentication): ResponseEntity<Map<String, Any>> {
        val response = mapOf(
            "message" to "Hello, authenticated user!",
            "timestamp" to LocalDateTime.now().toString(),
            "user" to authentication.name
        )
        return ResponseEntity.ok(response)
    }
}
