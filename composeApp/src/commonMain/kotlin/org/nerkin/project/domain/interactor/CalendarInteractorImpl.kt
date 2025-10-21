package org.nerkin.project.domain.interactor

import org.nerkin.project.domain.model.Conference
import org.nerkin.project.domain.usecase.GetListUseCase
import org.nerkin.project.domain.usecase.GetViewUseCase


class CalendarInteractorImpl(
    private val getListUseCase: GetListUseCase,
    private val getViewUseCase: GetViewUseCase
) : CalendarInteractor {

    override suspend fun loadConferences(): List<Conference> = getListUseCase()

    override suspend fun loadConferenceDetails(): Conference = getViewUseCase()
}
