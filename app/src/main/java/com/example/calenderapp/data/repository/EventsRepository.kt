package com.example.calenderapp.data.repository

import com.example.calenderapp.data.local.EventDao
import com.example.calenderapp.data.local.EventItem
import kotlinx.coroutines.flow.Flow

class EventsRepository(private val eventDao: EventDao) {

    val allEvents : Flow<List<EventItem>> = eventDao.getEvents()

    suspend fun addEvent(eventItem: EventItem){eventDao.addEvent(eventItem)}

    suspend fun editEvent(eventItem: EventItem){ eventDao.editEvent(eventItem) }

    suspend fun deleteEvent(eventItem: EventItem){ eventDao.deleteEvent(eventItem) }


}