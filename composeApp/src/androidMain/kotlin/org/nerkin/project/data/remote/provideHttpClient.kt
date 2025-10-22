package org.nerkin.project.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.nerkin.project.data.api.ApiKeyPlugin

// HttpClientProvider.kt
interface HttpClientProvider {
    fun provideHttpClient(): HttpClient
}

class DefaultHttpClientProvider : HttpClientProvider {
    override fun provideHttpClient(): HttpClient = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }

        install(Logging) {
            level = LogLevel.HEADERS
        }

        install(ApiKeyPlugin) // Убедись, что ApiKeyPlugin корректно импортирован и настроен
    }
}