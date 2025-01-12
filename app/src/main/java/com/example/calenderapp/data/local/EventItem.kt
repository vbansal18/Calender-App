package com.example.calenderapp.data.local

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp

@Entity(tableName = "Events")
data class EventItem(
    @PrimaryKey()
    val timestamp: Timestamp,
    val title : String,
    val desc : String,
    val category : Color,
    )