package org.nerkin.project.data.api


import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import org.nerkin.project.data.api.models.ListResponse
import org.nerkin.project.data.api.models.ViewResponse
import org.nerkin.project.extra.Constants

class ApiClient(private val client: HttpClient) {

    suspend fun getList(): ListResponse {
        val response = client.get("${Constants.BASE_URL}/list")
        println("Raw JSON (list): ${response.bodyAsText()}")
        return response.body()
    }

    suspend fun getView(conferenceId: Int): ViewResponse {
        val response = client.get("${Constants.BASE_URL}/view?id=$conferenceId")
        println("Raw JSON (view): ${response.bodyAsText()}")
        return response.body()
    }
}