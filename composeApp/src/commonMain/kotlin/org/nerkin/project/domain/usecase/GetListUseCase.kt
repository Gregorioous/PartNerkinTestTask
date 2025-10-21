package org.nerkin.project.domain.usecase

import org.nerkin.project.domain.model.Conference
import org.nerkin.project.domain.repository.ListRepository


class GetListUseCase(private val repo: ListRepository) {
    suspend operator fun invoke(): List<Conference> = repo.getList()
}