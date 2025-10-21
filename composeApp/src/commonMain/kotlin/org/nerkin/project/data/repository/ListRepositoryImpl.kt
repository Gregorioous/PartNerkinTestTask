package org.nerkin.project.data.repository

import org.nerkin.project.data.mappers.toDomain
import org.nerkin.project.data.remote.ApiClient
import org.nerkin.project.data.remote.Logger
import org.nerkin.project.domain.model.Conference
import org.nerkin.project.domain.repository.ListRepository


class ListRepositoryImpl(
    private val api: ApiClient
) : ListRepository {

    override suspend fun getList(): List<Conference> {
        return try {
            val resp = api.getList()
            if (resp.error != null) {
                Logger.e("CalendarRepo", "API error: ${resp.error}")
                emptyList()
            } else {
                val result = resp.data?.result ?: emptyList()
                Logger.d("CalendarRepo", "Parsed ${result.size} conferences")
                result.map { it.conference.toDomain() }
            }
        } catch (e: Exception) {
            Logger.e("CalendarRepo", "Error fetching conferences", e)
            emptyList()
        }
    }

    override suspend fun getView(): Conference {
        val resp = api.getView()
        return resp.result?.toDomain() ?: throw IllegalStateException("Empty response")

    }


}