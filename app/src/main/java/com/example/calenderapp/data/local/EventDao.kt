package com.example.calenderapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {
    @Insert
    suspend fun addEvent(eventItem: EventItem)

    @Update
    suspend fun editEvent(eventItem: EventItem)

    @Delete
    suspend fun deleteEvent(eventItem: EventItem)

    @Query("SELECT * FROM EVENTS")
    fun getEvents() : Flow<List<EventItem>>
}