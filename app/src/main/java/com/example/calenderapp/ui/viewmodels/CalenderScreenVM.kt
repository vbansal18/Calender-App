package com.example.calenderapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calenderapp.data.local.EventItem
import com.example.calenderapp.data.repository.EventsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CalenderScreenVM(private val repository: EventsRepository) : ViewModel() {
    private val _events = MutableStateFlow<List<EventItem>>(emptyList())
    val events : StateFlow<List<EventItem>>
        get() = _events.asStateFlow()

    init {
        viewModelScope.launch {
            repository.allEvents.collect{
                _events.value = it
            }
        }
    }



}