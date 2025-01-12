package com.example.calenderapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.calenderapp.ui.screens.CalendarView
import com.example.calenderapp.ui.screens.EventScreen
import com.example.calenderapp.ui.screens.EventsListScreen
import com.example.calenderapp.ui.viewmodels.CalenderScreenVM
import com.example.calenderapp.ui.viewmodels.EventScreenVM
import com.example.calenderapp.ui.viewmodels.MainScreenVM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
                val mainScreenVM: MainScreenVM = viewModel()
                val calenderScreenVM: CalenderScreenVM = viewModel()
                val eventScreenVM: EventScreenVM = viewModel()

                Scaffold(
                    floatingActionButton = {
                        FloatingActionButton(onClick = {
                            mainScreenVM.onEventScreenAppear()
                        }) {
                            Icon(imageVector = Icons.Default.Add, contentDescription = "add")
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding->
                    if(!mainScreenVM.isEventListScreenAppear){
                        CalendarView(
                            modifier = Modifier.padding(innerPadding),
                            mainScreenVM = mainScreenVM,
                            eventScreenVM= eventScreenVM,
                            calenderScreenVM = calenderScreenVM,
                        )
                    }
                    else if (mainScreenVM.isEventListScreenAppear){
                        EventsListScreen(
                            mainScreenVM = mainScreenVM,
                            eventScreenVM= eventScreenVM,
                            calenderScreenVM = calenderScreenVM,
                            )
                    }
                    if (mainScreenVM.isEventScreenAppear) {
                        EventScreen(
                            mainScreenVM = mainScreenVM,
                            eventScreenVM= eventScreenVM,
                            calenderScreenVM = calenderScreenVM,
                        )
                    }

                }
            }

    }
}
