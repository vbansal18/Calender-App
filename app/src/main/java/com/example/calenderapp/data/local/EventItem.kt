package com.example.calenderapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Events")
data class EventItem(
    @PrimaryKey
    var timestamp: Long,
    var title: String,
    var desc: String,
    var category: Long,
    )