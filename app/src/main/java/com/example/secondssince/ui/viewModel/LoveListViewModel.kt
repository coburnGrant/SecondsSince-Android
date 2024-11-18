package com.example.secondssince.ui.viewModel

import androidx.lifecycle.ViewModel

class LoveListViewModel(
    var loves: List<LoveViewModel>,
    var selectedLove: LoveViewModel? = null
): ViewModel() {

}