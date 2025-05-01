package com.apikeys.security

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority

class ApiKeyAuthToken : AbstractAuthenticationToken {
    
    private var apiKey: String
    private var principal: String
    
    // For unauthenticated token
    constructor(apiKey: String) : super(null) {
        this.apiKey = apiKey
        this.principal = "anonymous"
        isAuthenticated = false
    }
    
    // For authenticated token
    constructor(
        apiKey: String,
        principal: String,
        authorities: Collection<GrantedAuthority>
    ) : super(authorities) {
        this.apiKey = apiKey
        this.principal = principal
        isAuthenticated = true
    }
    
    override fun getCredentials(): Any {
        return apiKey
    }
    
    override fun getPrincipal(): Any {
        return principal
    }
}
