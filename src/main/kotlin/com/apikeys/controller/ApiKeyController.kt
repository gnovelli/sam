package com.apikeys.controller

import com.apikeys.dto.ApiKeyCreationDto
import com.apikeys.dto.ApiKeyDto
import com.apikeys.service.ApiKeyService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/keys")
@Tag(name = "API Key Management", description = "Endpoints for managing API keys")
class ApiKeyController(private val apiKeyService: ApiKeyService) {

    @PostMapping
    @Operation(summary = "Create a new API key")
    fun createApiKey(@Valid @RequestBody apiKeyCreationDto: ApiKeyCreationDto): ResponseEntity<ApiKeyDto> {
        val apiKey = apiKeyService.createApiKey(apiKeyCreationDto)
        return ResponseEntity(apiKey, HttpStatus.CREATED)
    }
    
    @GetMapping
    @Operation(summary = "Get all API keys")
    fun getAllApiKeys(): ResponseEntity<List<ApiKeyDto>> {
        return ResponseEntity.ok(apiKeyService.getAllApiKeys())
    }
    
    @GetMapping("/project/{projectId}")
    @Operation(summary = "Get API keys by project ID")
    fun getApiKeysByProjectId(@PathVariable projectId: String): ResponseEntity<List<ApiKeyDto>> {
        return ResponseEntity.ok(apiKeyService.getApiKeysByProjectId(projectId))
    }
    
    @GetMapping("/owner/{owner}")
    @Operation(summary = "Get API keys by owner")
    fun getApiKeysByOwner(@PathVariable owner: String): ResponseEntity<List<ApiKeyDto>> {
        return ResponseEntity.ok(apiKeyService.getApiKeysByOwner(owner))
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get API key by ID")
    fun getApiKeyById(@PathVariable id: String): ResponseEntity<ApiKeyDto> {
        return ResponseEntity.ok(apiKeyService.getApiKeyById(id))
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete (deactivate) API key")
    fun deleteApiKey(
        @PathVariable id: String,
        @RequestParam owner: String
    ): ResponseEntity<ApiKeyDto> {
        return ResponseEntity.ok(apiKeyService.deleteApiKey(id, owner))
    }
    
    @PutMapping("/{id}/deactivate")
    @Operation(summary = "Deactivate an API key")
    fun deactivateApiKey(@PathVariable id: String): ResponseEntity<ApiKeyDto> {
        val apiKey = apiKeyService.getApiKeyById(id)
        return ResponseEntity.ok(apiKeyService.deactivateApiKey(id))
    }
}
