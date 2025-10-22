package org.nerkin.project.screen.ui

import org.nerkin.project.domain.model.Conference

sealed interface CalendarUiState {
    object Loading : CalendarUiState
    data class Success(val conferences: List<Conference>) : CalendarUiState
    data class Details(val conference: Conference) : CalendarUiState
    data class Error(val message: String) : CalendarUiState
}