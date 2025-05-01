package com.apikeys.config

import com.apikeys.security.ApiKeyAuthFilter
import com.apikeys.security.ApiKeyAuthManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(private val apiKeyAuthManager: ApiKeyAuthManager) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        // Create custom API key filter
        val apiKeyAuthFilter = ApiKeyAuthFilter()
        apiKeyAuthFilter.setAuthenticationManager(apiKeyAuthManager)

        http
            .csrf { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .addFilterBefore(apiKeyAuthFilter, UsernamePasswordAuthenticationFilter::class.java)
            .authorizeHttpRequests {
                it.requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                it.requestMatchers("/api/keys/**").permitAll() // API key management endpoints are not secured
                it.anyRequest().authenticated()
            }

        return http.build()
    }

    @Bean
    fun corsFilter(): CorsFilter {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()
        config.allowCredentials = false // Changed from true because allowCredentials=true is incompatible with allowedOrigin="*"
        config.addAllowedOriginPattern("*") // Using pattern instead of fixed origin
        config.addAllowedHeader("*")
        config.addAllowedMethod("*")
        source.registerCorsConfiguration("/**", config)
        return CorsFilter(source)
    }
}
