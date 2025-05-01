package com.apikeys.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.web.util.matcher.RequestMatcher

class ApiKeyAuthFilter : AbstractAuthenticationProcessingFilter(AntPathRequestMatcher("/api/**")) {

    companion object {
        const val API_KEY_HEADER = "X-API-KEY"
    }

    init {
        // Skip auth for API key management endpoints and Swagger/OpenAPI endpoints
        setRequiresAuthenticationRequestMatcher(
            object : RequestMatcher {
                private val apiPathMatcher = AntPathRequestMatcher("/api/**")
                private val skipAuthPathMatcher = AntPathRequestMatcher("/api/keys/**")
                private val swaggerMatcher = AntPathRequestMatcher("/v3/api-docs/**")
                private val swaggerUiMatcher = AntPathRequestMatcher("/swagger-ui/**") 
                
                override fun matches(request: HttpServletRequest): Boolean {
                    return apiPathMatcher.matches(request) && 
                           !skipAuthPathMatcher.matches(request) && 
                           !swaggerMatcher.matches(request) && 
                           !swaggerUiMatcher.matches(request)
                }
            }
        )
    }

    override fun attemptAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse
    ): ApiKeyAuthToken {
        val apiKey = request.getHeader(API_KEY_HEADER) ?: ""
        val authRequest = ApiKeyAuthToken(apiKey)
        return authenticationManager.authenticate(authRequest) as ApiKeyAuthToken
    }

    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain,
        authResult: org.springframework.security.core.Authentication
    ) {
        SecurityContextHolder.getContext().authentication = authResult
        chain.doFilter(request, response)
    }

    // Removed unnecessary method that was causing compilation issues
    // The custom RequestMatcher in the init block handles this functionality
}
