package com.example.calenderapp.data.repository

import android.util.Log
import com.example.calenderapp.data.local.EventDao
import com.example.calenderapp.data.local.EventItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class EventsRepository @Inject constructor(private val eventDao: EventDao) {

    fun getEvents() : Flow<List<EventItem>> = try {
        eventDao.getEvents()
    }
    catch (e:Exception){
        Log.d("Error", e.message.toString())
        emptyFlow()
    }

    suspend fun addEvent(eventItem: EventItem){
        try {
            eventDao.addEvent(eventItem)
        }
        catch (e:Exception){
            Log.d("Error", e.message.toString())
        }
    }
    suspend fun editEvent(eventItem: EventItem){
        try {
            eventDao.editEvent(eventItem)
        }
        catch (e:Exception){
            Log.d("Error", e.message.toString())
        }
    }
    suspend fun deleteEvent(eventItem: EventItem){
        try {
            eventDao.deleteEvent(eventItem)
        }
        catch (e:Exception){
            Log.d("Error", e.message.toString())
        }
    }
}