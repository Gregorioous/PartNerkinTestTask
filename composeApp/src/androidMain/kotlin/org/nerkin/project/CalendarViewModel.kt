package org.nerkin.project

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.nerkin.project.domain.interactor.CalendarInteractor
import org.nerkin.project.domain.model.Conference

sealed interface UiState {
    object Loading : UiState
    data class Success(val items: List<Conference>) : UiState
    data class Error(val message: String) : UiState
}

class CalendarViewModel(
    private val interactor: CalendarInteractor
) : ViewModel() {

    private val _state = MutableStateFlow<List<Conference>>(emptyList())
    val state: StateFlow<List<Conference>> = _state

    fun loadConferences() {
        viewModelScope.launch {
            try {
                val data = interactor.loadConferences()
                _state.value = data
                Log.d("CalendarVM", "Loaded conferences: ${data.size}")
            } catch (e: Exception) {
                Log.e("CalendarVM", "Error loading list", e)
            }
        }
    }

    fun loadDetails() {
        viewModelScope.launch {
            try {
                val conf = interactor.loadConferenceDetails()
                Log.d("CalendarVM", "Loaded details: ${conf.title}")
            } catch (e: Exception) {
                Log.e("CalendarVM", "Error loading details", e)
            }
        }
    }
}

