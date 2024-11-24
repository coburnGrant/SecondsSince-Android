package com.example.secondssince.ui.viewModel

import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.lifecycle.ViewModel
import com.example.secondssince.model.Love
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.Date

data class CreateNewLoveUiState(
    var username: String = "",
    var loveName: String = "",
    var selectedDate: Date = Date(),
    var selectedImageUri: Uri? = null,
    var getContent: ActivityResultLauncher<PickVisualMediaRequest>? = null
)

class CreateNewLoveViewModel(
    initialState: CreateNewLoveUiState = CreateNewLoveUiState()
) : ViewModel() {
    private val _createNewLoveUiState = MutableStateFlow(initialState)
    val createNewLoveUiState: StateFlow<CreateNewLoveUiState> = _createNewLoveUiState.asStateFlow()

    fun updateUsername(name: String) {
        _createNewLoveUiState.update { currentState ->
            currentState.copy(username = name)
        }
    }

    fun updateLoveName(name: String) {
        _createNewLoveUiState.update { currentState ->
            currentState.copy(loveName = name)
        }
    }

    fun updateSelectedImageUri(uri: Uri?) {
        _createNewLoveUiState.update { currentState ->
            currentState.copy(selectedImageUri = uri)
        }
    }

    fun updateSelectedDate(date: Date) {
        _createNewLoveUiState.update { currentState ->
            currentState.copy(selectedDate = date)
        }
    }

    fun setGetContent(getContent: ActivityResultLauncher<PickVisualMediaRequest>?) {
        _createNewLoveUiState.update { currentState ->
            currentState.copy(getContent = getContent)
        }
    }

    fun getLove(): Love {
        return Love(
            userName = _createNewLoveUiState.value.username,
            loveName = _createNewLoveUiState.value.loveName,
            anniversary = _createNewLoveUiState.value.selectedDate,
            loveImageUri = _createNewLoveUiState.value.selectedImageUri
        )
    }
}