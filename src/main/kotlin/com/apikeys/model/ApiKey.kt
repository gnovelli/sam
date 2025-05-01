package com.apikeys.model

import java.time.LocalDateTime

/**
 * Represents an API key in the system
 */
data class ApiKey(
    val id: String,
    val key: String,
    val projectId: String,
    val owner: String,
    val createdAt: LocalDateTime,
    val expiresAt: LocalDateTime,
    val isActive: Boolean = true
)
