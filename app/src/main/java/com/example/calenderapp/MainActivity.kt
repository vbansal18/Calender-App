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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.calenderapp.ui.screens.CalenderScreen
import com.example.calenderapp.ui.screens.EventScreen
import com.example.calenderapp.ui.theme.CalenderAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalenderAppTheme {
                val isEventScreenAppear = remember { mutableStateOf(false) }

                Scaffold(
                    floatingActionButton = {
                        FloatingActionButton(onClick = { isEventScreenAppear.value = true }, containerColor = Color.DarkGray) {
                            Icon(imageVector = Icons.Default.Add, contentDescription = "add")
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding->
                    CalenderScreen(modifier = Modifier.padding(innerPadding))
                    if(isEventScreenAppear.value){
                        EventScreen { isEventScreenAppear.value = !isEventScreenAppear.value }
                    }
                }
            }
        }
    }
}
