package com.example.calenderapp.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.calenderapp.data.ColorCategories
import com.example.calenderapp.ui.viewmodels.CalenderScreenVM
import com.example.calenderapp.ui.viewmodels.EventScreenVM
import com.example.calenderapp.ui.viewmodels.MainScreenVM

@Composable
fun EventsListScreen(
    calenderScreenVM: CalenderScreenVM,
    mainScreenVM: MainScreenVM,
    eventScreenVM: EventScreenVM
) {
    val context = LocalContext.current
    val eventDayList = calenderScreenVM.eventListScreenList.collectAsState(emptyList()).value
    Log.d("EventListScreen", eventDayList.toString())

    Column(
        Modifier.fillMaxSize().statusBarsPadding()
    ) {
        Row(
            Modifier.fillMaxWidth().padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                mainScreenVM.onStopEdit()
                mainScreenVM.onEventScreenDisappear()
            }) {
                Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = null)
            }
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(eventDayList){event->
                Row(modifier = Modifier.fillMaxWidth().border(1.dp, Color.Black, RectangleShape).padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    Row(
                        modifier = Modifier.fillMaxWidth(0.8f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.AddCircle, null, tint = Color(event.category))
                        Column(
                            modifier = Modifier.padding(start = 8.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(event.title, overflow = TextOverflow.Ellipsis)
                            Text(event.timestamp.toString(), overflow = TextOverflow.Ellipsis, modifier = Modifier.padding(top = 4.dp))
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically

                    ) {
                        IconButton(onClick = {
                            calenderScreenVM.deleteEvent(event)
                            mainScreenVM.onStopEdit()
                            Toast.makeText(context, "Event deleted successfully", Toast.LENGTH_SHORT).show()
                        }){ Icon(
                            Icons.Default.Delete,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )}
                        IconButton(onClick = {
                            var colorCategory = ColorCategories.Red
                            for (i in ColorCategories.entries){
                                if(i.color==event.category){
                                    colorCategory = i
                                }
                            }
                            eventScreenVM.onTitleChange(event.title)
                            eventScreenVM.onDescChange(event.desc)
                            eventScreenVM.onCategoryChange(colorCategory)
                            eventScreenVM.onTimestampChange(event.timestamp)
                            mainScreenVM.onEventScreenAppear()
                        }){ Icon(
                            Icons.Default.Edit,
                            contentDescription = null,
                            modifier = Modifier
                                .size(16.dp)
                        )}
                    }
                }

            }
        }
    }
}