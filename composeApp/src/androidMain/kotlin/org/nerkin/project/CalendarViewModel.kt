package org.nerkin.project

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.nerkin.project.domain.interactor.CalendarInteractor
import org.nerkin.project.domain.model.Conference
import org.nerkin.project.screen.ui.CalendarUiState


class CalendarViewModel(
    private val interactor: CalendarInteractor
) : ViewModel() {

    private val _uiState = MutableStateFlow<CalendarUiState>(CalendarUiState.Loading)
    val uiState: StateFlow<CalendarUiState> = _uiState

    private val _selectedConference = MutableStateFlow<Conference?>(null)
    val selectedConference: StateFlow<Conference?> = _selectedConference

    fun loadConferences() {
        viewModelScope.launch {
            try {
                val conferences = interactor.loadConferences()
                _uiState.value = CalendarUiState.Success(conferences)
                Log.d("CalendarVM", "Loaded conferences: ${conferences.size}")
            } catch (e: Exception) {
                _uiState.value = CalendarUiState.Error(e.message ?: "Unknown error")
                Log.e("CalendarVM", "Error loading list", e)
            }
        }
    }

    fun selectConference(conference: Conference) {
        _selectedConference.value = conference
        viewModelScope.launch {
            try {
                val details = interactor.loadConferenceDetails()
                _uiState.value = CalendarUiState.Details(details)
            } catch (e: Exception) {
                _uiState.value = CalendarUiState.Error(e.message ?: "Error loading details")
            }
        }
    }
}
