package com.apikeys.dto

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank

/**
 * Data Transfer Object for API Key creation requests
 */
data class ApiKeyCreationDto(
    @field:NotBlank(message = "Project ID is required")
    val projectId: String,
    
    @field:NotBlank(message = "Owner is required")
    val owner: String,
    
    @field:Min(value = 1, message = "Expiration days must be at least 1")
    val expirationDays: Int
)
