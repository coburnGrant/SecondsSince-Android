package com.example.secondssince.ui.viewModel

import androidx.lifecycle.ViewModel
import com.example.secondssince.model.Love
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoveListViewModel(
    loves: List<LoveViewModel>,
    var selectedLove: LoveViewModel? = null
): ViewModel() {
    private val _loves = MutableStateFlow(loves)
    val loves: StateFlow<List<LoveViewModel>> = _loves.asStateFlow()

    fun addLove(love: Love) {
        // TODO: Save to database
        // TODO: Save image to media store

        val viewModel = LoveViewModel(love = love)

        _loves.update { currentLoves ->
            currentLoves.plus(viewModel)
        }
    }
}