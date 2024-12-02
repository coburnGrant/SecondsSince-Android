package com.example.secondssince.ui.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.secondssince.data.LoveDao
import com.example.secondssince.data.UserMediaRepository
import com.example.secondssince.model.Love
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.collections.plus

class LoveListViewModel(
    private val loveDao: LoveDao,
    private val userMediaRepository: UserMediaRepository,
    var selectedLove: LoveViewModel? = null
) : ViewModel() {
    private val _loves = MutableStateFlow<List<LoveViewModel>>(emptyList())
    val loves: StateFlow<List<LoveViewModel>> = _loves.asStateFlow()

    init {
        updateLovesFromDao()
    }

    fun refreshLoves() {
        updateLovesFromDao()
    }

    private fun updateLovesFromDao() {
        viewModelScope.launch {
            try {
                val loveVMs = loveDao.getAllLoves()
                    .map { LoveViewModel(it) }

                _loves.value = loveVMs
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun addLove(love: Love) {
        viewModelScope.launch {
            // Save image to user media repo
            if (love.loveImageUri != null) {
                val savedUri = userMediaRepository.saveImage(love.loveImageUri!!)

                if (savedUri == null) {
                    Log.e("LoveListViewModel", "Failed to get URI from user media repository")
                } else {
                    love.loveImageUri = savedUri
                }
            }

            // Save love to db
            loveDao.insertLove(love)

            updateLovesFromDao()
        }

        val viewModel = LoveViewModel(love = love)

        _loves.update { currentLoves ->
            currentLoves.plus(viewModel)
        }
    }
}