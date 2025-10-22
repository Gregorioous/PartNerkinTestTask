package org.nerkin.project.domain.interactor

import org.nerkin.project.domain.model.Conference


interface CalendarInteractor {
    suspend fun loadConferences(): List<Conference>
    suspend fun loadConferenceDetails(conferenceId: Int): Conference
}
