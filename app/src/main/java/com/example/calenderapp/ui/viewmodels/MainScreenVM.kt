package com.example.calenderapp.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MainScreenVM  : ViewModel() {
//    DROPDOWN Category
    var isEventScreenAppear by mutableStateOf(false)
        private set

    fun onEventScreenAppear() {
        isEventScreenAppear = true
    }

    fun onEventScreenDisappear() {
        isEventScreenAppear = false
    }


    var isEditGoingOn by mutableStateOf(false)
        private set

    fun onStartEdit() {
        isEditGoingOn = true
    }

    fun onStopEdit() {
        isEditGoingOn = false
    }


}