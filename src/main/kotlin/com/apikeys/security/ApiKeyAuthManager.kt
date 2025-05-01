package com.apikeys.security

import com.apikeys.exception.ApiKeyException
import com.apikeys.service.ApiKeyService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component

@Component
class ApiKeyAuthManager(private val apiKeyService: ApiKeyService) : AuthenticationManager {

    override fun authenticate(authentication: Authentication): Authentication {
        val apiKey = authentication.credentials.toString()
        
        try {
            if (apiKey.isBlank()) {
                throw BadCredentialsException("API key is required")
            }
            
            val validatedKey = apiKeyService.validateApiKey(apiKey)
            
            // Create successful authentication
            val authenticated = ApiKeyAuthToken(
                apiKey,
                validatedKey.owner,
                listOf(SimpleGrantedAuthority("ROLE_API_USER"))
            )
            authenticated.details = mapOf(
                "projectId" to validatedKey.projectId,
                "owner" to validatedKey.owner
            )
            return authenticated
            
        } catch (e: ApiKeyException) {
            throw BadCredentialsException(e.message)
        }
    }
}
