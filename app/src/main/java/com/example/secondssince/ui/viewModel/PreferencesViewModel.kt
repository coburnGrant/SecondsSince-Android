package com.example.secondssince.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.secondssince.data.UserPreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PreferencesViewModel(
    private val userPreferencesRepository: UserPreferencesRepository
): ViewModel() {
    var notifyOnSpecialDay = userPreferencesRepository.notifyOnSpecialDay

    var notifyOnSpecialMoment = userPreferencesRepository.notifyOnSpecialMoment

    private val _selectedTime = MutableStateFlow(Pair(8, 0))
    val selectedTime: StateFlow<Pair<Int, Int>> = _selectedTime.asStateFlow()

    init {
        viewModelScope.launch {
            userPreferencesRepository.getSelectedTime()
                .collect { time ->
                    _selectedTime.value = time
                }
        }
    }

    fun updateNotifyOnSpecialDay(notify: Boolean) {
        viewModelScope.launch {
            userPreferencesRepository.saveNotifyOnSpecialDayPreference(notify)
        }
    }

    fun updateNotifyOnSpecialMoment(notify: Boolean) {
        viewModelScope.launch {
            userPreferencesRepository.saveNotifyOnSpecialMomentPreference(notify)
        }
    }

    // Update selected time and save to DataStore
    fun updateTime(hour: Int, minute: Int) {
        _selectedTime.value = Pair(hour, minute)
        viewModelScope.launch {
            userPreferencesRepository.saveSelectedTime(hour, minute)
        }
    }
}