package org.nerkin.project.data.remote

import io.ktor.client.plugins.api.createClientPlugin
import org.nerkin.project.extra.Constants


val ApiKeyPlugin = createClientPlugin("ApiKeyInterceptor") {
    onRequest { request, _ ->
        val urlBuilder = request.url
        urlBuilder.parameters.append("api_key", Constants.API_KEY)
    }
}
