package org.gulaii.app.ui.screens.initialScreen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class InitialScreenViewModel : ViewModel() {

    private val _headerText = MutableStateFlow("Gulaii")
    val headerText = _headerText.asStateFlow()

}
