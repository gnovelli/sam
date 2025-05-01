package com.apikeys.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {

    @Bean
    fun openAPI(): OpenAPI {
        val securitySchemeName = "apiKey"
        return OpenAPI()
            .info(
                Info()
                    .title("API Key Management API")
                    .description("API for managing and using API keys with InfluxDB")
                    .version("1.0.0")
            )
            .addSecurityItem(SecurityRequirement().addList(securitySchemeName))
            .components(
                Components()
                    .addSecuritySchemes(
                        securitySchemeName,
                        SecurityScheme()
                            .name("X-API-KEY")
                            .type(SecurityScheme.Type.APIKEY)
                            .`in`(SecurityScheme.In.HEADER)
                            .description("API key for authentication")
                    )
            )
    }
}
