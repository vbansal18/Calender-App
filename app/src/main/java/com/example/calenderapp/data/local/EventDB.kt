package com.example.calenderapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [EventItem::class], version = 1, exportSchema = false)
abstract class EventDB : RoomDatabase(){
    abstract fun eventDao() : EventDao
}