package com.apikeys.repository

import com.apikeys.model.ApiKey

/**
 * Repository interface for ApiKey management
 */
interface ApiKeyRepository {
    /**
     * Save an API key
     */
    fun save(apiKey: ApiKey): ApiKey
    
    /**
     * Find an API key by its key
     */
    fun findByKey(key: String): ApiKey?
    
    /**
     * Find all API keys
     */
    fun findAll(): List<ApiKey>
    
    /**
     * Find API keys by project ID
     */
    fun findByProjectId(projectId: String): List<ApiKey>
    
    /**
     * Find API keys by owner
     */
    fun findByOwner(owner: String): List<ApiKey>
    
    /**
     * Find an API key by ID
     */
    fun findById(id: String): ApiKey?
    
    /**
     * Update API key (mainly to deactivate it)
     */
    fun update(apiKey: ApiKey): ApiKey
}
