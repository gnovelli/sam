package com.apikeys.dto

import com.apikeys.model.ApiKey
import java.time.LocalDateTime

/**
 * Data Transfer Object for API Key responses
 */
data class ApiKeyDto(
    val id: String,
    val key: String, // Will only be shown during creation, not listing
    val projectId: String,
    val owner: String,
    val createdAt: LocalDateTime,
    val expiresAt: LocalDateTime,
    val isActive: Boolean,
    val daysRemaining: Long
) {
    companion object {
        fun fromApiKey(apiKey: ApiKey, includeKey: Boolean = false): ApiKeyDto {
            val now = LocalDateTime.now()
            val daysRemaining = if (apiKey.expiresAt.isAfter(now)) {
                java.time.Duration.between(now, apiKey.expiresAt).toDays()
            } else {
                0
            }
            
            return ApiKeyDto(
                id = apiKey.id,
                key = if (includeKey) apiKey.key else "************",
                projectId = apiKey.projectId,
                owner = apiKey.owner,
                createdAt = apiKey.createdAt,
                expiresAt = apiKey.expiresAt,
                isActive = apiKey.isActive,
                daysRemaining = daysRemaining
            )
        }
    }
}
