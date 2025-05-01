package com.apikeys.service

import com.apikeys.dto.ApiKeyCreationDto
import com.apikeys.dto.ApiKeyDto
import com.apikeys.exception.ApiKeyException
import com.apikeys.model.ApiKey
import com.apikeys.repository.ApiKeyRepository
import com.apikeys.util.ApiKeyGenerator
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class ApiKeyService(private val apiKeyRepository: ApiKeyRepository) {

    /**
     * Create a new API key
     */
    fun createApiKey(apiKeyCreationDto: ApiKeyCreationDto): ApiKeyDto {
        val now = LocalDateTime.now()
        val expiresAt = now.plusDays(apiKeyCreationDto.expirationDays.toLong())
        
        val apiKey = ApiKey(
            id = UUID.randomUUID().toString(),
            key = ApiKeyGenerator.generateApiKey(),
            projectId = apiKeyCreationDto.projectId,
            owner = apiKeyCreationDto.owner,
            createdAt = now,
            expiresAt = expiresAt,
            isActive = true
        )
        
        val savedApiKey = apiKeyRepository.save(apiKey)
        return ApiKeyDto.fromApiKey(savedApiKey, includeKey = true)
    }
    
    /**
     * Get all API keys
     */
    fun getAllApiKeys(): List<ApiKeyDto> {
        return apiKeyRepository.findAll()
            .map { ApiKeyDto.fromApiKey(it) }
    }
    
    /**
     * Get API keys by project ID
     */
    fun getApiKeysByProjectId(projectId: String): List<ApiKeyDto> {
        return apiKeyRepository.findByProjectId(projectId)
            .map { ApiKeyDto.fromApiKey(it) }
    }
    
    /**
     * Get API keys by owner
     */
    fun getApiKeysByOwner(owner: String): List<ApiKeyDto> {
        return apiKeyRepository.findByOwner(owner)
            .map { ApiKeyDto.fromApiKey(it) }
    }
    
    /**
     * Get API key by ID
     */
    fun getApiKeyById(id: String): ApiKeyDto {
        val apiKey = apiKeyRepository.findById(id)
            ?: throw ApiKeyException("API key not found with ID: $id")
        return ApiKeyDto.fromApiKey(apiKey)
    }
    
    /**
     * Delete (deactivate) API key by ID
     */
    fun deleteApiKey(id: String, requesterOwner: String): ApiKeyDto {
        val apiKey = apiKeyRepository.findById(id)
            ?: throw ApiKeyException("API key not found with ID: $id")
        
        if (apiKey.owner != requesterOwner) {
            throw ApiKeyException("Only the owner can delete their API key")
        }
        
        val updatedApiKey = apiKey.copy(isActive = false)
        val savedApiKey = apiKeyRepository.update(updatedApiKey)
        return ApiKeyDto.fromApiKey(savedApiKey)
    }
    
    /**
     * Deactivate API key without owner check (for admin operations)
     */
    fun deactivateApiKey(id: String): ApiKeyDto {
        val apiKey = apiKeyRepository.findById(id)
            ?: throw ApiKeyException("API key not found with ID: $id")
        
        val updatedApiKey = apiKey.copy(isActive = false)
        val savedApiKey = apiKeyRepository.update(updatedApiKey)
        return ApiKeyDto.fromApiKey(savedApiKey)
    }
    
    /**
     * Validate API key for authentication
     */
    fun validateApiKey(key: String): ApiKey {
        val apiKey = apiKeyRepository.findByKey(key)
            ?: throw ApiKeyException("Invalid API key")
        
        if (!apiKey.isActive) {
            throw ApiKeyException("API key is inactive")
        }
        
        if (apiKey.expiresAt.isBefore(LocalDateTime.now())) {
            // Automatically deactivate expired keys
            val deactivatedKey = apiKey.copy(isActive = false)
            apiKeyRepository.update(deactivatedKey)
            throw ApiKeyException("API key has expired")
        }
        
        return apiKey
    }
}
