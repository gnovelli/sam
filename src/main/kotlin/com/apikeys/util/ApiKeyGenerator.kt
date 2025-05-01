package com.apikeys.util

import java.security.SecureRandom
import java.util.*

/**
 * Utility class for generating secure API keys
 */
object ApiKeyGenerator {
    private const val API_KEY_LENGTH = 40
    private val secureRandom = SecureRandom()
    
    /**
     * Generate a secure random API key
     */
    fun generateApiKey(): String {
        val randomBytes = ByteArray(API_KEY_LENGTH)
        secureRandom.nextBytes(randomBytes)
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes)
    }
}
