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


    var isEventListScreenAppear by mutableStateOf(false)
        private set

    fun onEventListScreenAppear() {
        isEventListScreenAppear = true
    }

    fun onEventListScreenDisappear() {
        isEventListScreenAppear = false
    }


}