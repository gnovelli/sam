package com.apikeys.config

import com.influxdb.client.InfluxDBClient
import com.influxdb.client.InfluxDBClientFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.slf4j.LoggerFactory

@Configuration
@ConditionalOnProperty(value = ["influxdb.enabled"], havingValue = "true")
class InfluxDBConfig {
    private val logger = LoggerFactory.getLogger(InfluxDBConfig::class.java)

    @Value("\${influxdb.url}")
    private lateinit var url: String

    @Value("\${influxdb.token}")
    private lateinit var token: String

    @Value("\${influxdb.org}")
    private lateinit var org: String

    @Value("\${influxdb.bucket}")
    private lateinit var bucket: String

    @Bean
    fun influxDBClient(): InfluxDBClient {
        logger.info("Initializing InfluxDB client with URL: {}", url)
        return InfluxDBClientFactory.create(url, token.toCharArray(), org, bucket)
    }
}
