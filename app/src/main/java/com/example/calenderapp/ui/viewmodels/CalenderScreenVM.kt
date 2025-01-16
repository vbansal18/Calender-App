package com.example.calenderapp.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calenderapp.data.local.EventItem
import com.example.calenderapp.data.repository.EventsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalenderScreenVM @Inject constructor(private val repository: EventsRepository) : ViewModel() {

    private var _allEvents = flow<List<EventItem>> { }
    val allEvents : Flow<List<EventItem>>
        get() = _allEvents

    private var _eventListScreenList = flow<List<EventItem>> { }
    val eventListScreenList : Flow<List<EventItem>>
        get() = _eventListScreenList

    init {
        getEvents()
    }

    private fun getEvents(){
        viewModelScope.launch {
            try{
                _allEvents = repository.getEvents()
            }
            catch (e:Exception){
                Log.d("Error", e.message.toString())
            }
        }
    }
    fun setEventsListScreenEvents(eventList: Flow<List<EventItem>>){
        viewModelScope.launch {
            try{
                _eventListScreenList = eventList
            }
            catch (e:Exception){
                Log.d("Error", e.message.toString())
            }
        }
    }
    fun addEvent(eventItem: EventItem){
        viewModelScope.launch {
            try{
                repository.addEvent(eventItem)
            }
            catch (e:Exception){
                Log.d("Error", e.message.toString())
            }
        }
    }
    fun editEvent(eventItem: EventItem){
        viewModelScope.launch {
            try{
                repository.editEvent(eventItem)
            }
            catch (e:Exception){
                Log.d("Error", e.message.toString())
            }
        }
    }
    fun deleteEvent(eventItem: EventItem){
        viewModelScope.launch {
            try{
                repository.deleteEvent(eventItem)
            }
            catch (e:Exception){
                Log.d("Error", e.message.toString())
            }
        }
    }

}