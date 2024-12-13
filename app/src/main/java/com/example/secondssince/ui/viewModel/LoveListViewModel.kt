package com.example.secondssince.ui.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.secondssince.data.LoveDao
import com.example.secondssince.data.UserMediaRepository
import com.example.secondssince.model.Love
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
            val viewModel = LoveViewModel(love = love)

            // Immediately update the UI
            _loves.update {
                it + viewModel
            }

            // Cache on background thread
            withContext(Dispatchers.IO) {
                if (love.loveImageUri != null) {
                    val savedUri = userMediaRepository.saveImage(love.loveImageUri!!)
                    if (savedUri != null) {
                        love.loveImageUri = savedUri
                    } else {
                        Log.e("LoveListViewModel", "Failed to save image")
                    }
                }

                // Save the love to the database
                loveDao.insertLove(love)

                // Update from the new state of the dao
                updateLovesFromDao()
            }
        }
    }

    fun deleteLove(love: Love) {
        viewModelScope.launch {
            loveDao.deleteLove(love)

            updateLovesFromDao()
        }
    }
}