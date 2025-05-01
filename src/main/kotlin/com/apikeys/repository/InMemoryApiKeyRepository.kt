package com.apikeys.repository

import com.apikeys.model.ApiKey
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Repository
import java.util.concurrent.ConcurrentHashMap

/**
 * In-memory implementation of the ApiKeyRepository for development/testing
 * Used as primary implementation when InfluxDB is disabled
 */
@Repository
@Primary
@ConditionalOnProperty(value = ["influxdb.enabled"], havingValue = "false", matchIfMissing = true)
class InMemoryApiKeyRepository : ApiKeyRepository {
    private val apiKeys = ConcurrentHashMap<String, ApiKey>()
    
    override fun save(apiKey: ApiKey): ApiKey {
        apiKeys[apiKey.id] = apiKey
        return apiKey
    }
    
    override fun findByKey(key: String): ApiKey? {
        return apiKeys.values.firstOrNull { it.key == key }
    }
    
    override fun findAll(): List<ApiKey> {
        return apiKeys.values.toList()
    }
    
    override fun findByProjectId(projectId: String): List<ApiKey> {
        return apiKeys.values.filter { it.projectId == projectId }
    }
    
    override fun findByOwner(owner: String): List<ApiKey> {
        return apiKeys.values.filter { it.owner == owner }
    }
    
    override fun findById(id: String): ApiKey? {
        return apiKeys[id]
    }
    
    override fun update(apiKey: ApiKey): ApiKey {
        apiKeys[apiKey.id] = apiKey
        return apiKey
    }
}