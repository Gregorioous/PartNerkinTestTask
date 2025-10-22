package org.nerkin.project.domain.repository

import org.nerkin.project.domain.model.Conference

interface ListRepository {
    suspend fun getList(): List<Conference>
    suspend fun getView(conferenceId: Int): Conference
}