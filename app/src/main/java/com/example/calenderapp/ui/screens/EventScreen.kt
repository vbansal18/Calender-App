package com.example.calenderapp.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventScreen(onclick : (Boolean)->Unit) {
    val title = remember { mutableStateOf("") }
    val desc = remember { mutableStateOf("") }
    val category = remember { mutableStateOf(String) }
    val onOptionSelected : ((String)->Unit) = {category.value  }
    Log.d("Category", "${category.value}")
    val colors = listOf(
        ("Red"),
        ("Cyan"),
        ("Gray"),
        ("White"),
        ("DarkGray"),
        ("Green"),
        ("LightGray"),
        ("Magenta"),
        ("Yellow"),
    )

    ModalBottomSheet(
        onDismissRequest = { onclick(false) }
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp)) {
            LazyRow(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.padding(vertical = 20.dp)
            ) {
                items(colors){colorString ->
                    RadioButton(
                        colors = RadioButtonDefaults.colors(
                            unselectedColor = Color(colorString.toColorInt()),
                            selectedColor = Color(colorString.toColorInt())
                        ),
                        selected = false,
                        onClick = {
                            onOptionSelected(colorString)
                            Log.d("Category", "${category.value}")
                        },
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                    )
                }
            }
            TextField(
                value = title.value,
                onValueChange = {title.value = it},
                label = { Text("Title") },
                placeholder = {
                    Text(
                    "Add title",
                    style = MaterialTheme.typography.titleLarge
                ) },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1,
                textStyle = MaterialTheme.typography.titleLarge
            )

            TextField(
                value = desc.value,
                onValueChange = {desc.value = it},
                label = {
                    Row(
                        modifier = Modifier.height(20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.List, contentDescription = "Description")
                        Text("Description")
                    }
                },
                placeholder = {
                    Text(
                        "Add Description",
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                minLines = 8,
                maxLines = 10,
                textStyle = MaterialTheme.typography.titleMedium
            )

        }
    }
}