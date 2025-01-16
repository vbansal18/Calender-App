package com.example.calenderapp.ui.screens

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.calenderapp.data.ColorCategories
import com.example.calenderapp.data.local.EventItem
import com.example.calenderapp.ui.viewmodels.CalenderScreenVM
import com.example.calenderapp.ui.viewmodels.EventScreenVM
import com.example.calenderapp.ui.viewmodels.MainScreenVM
import java.time.LocalTime
import java.util.Calendar

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventScreen(
    calenderScreenVM: CalenderScreenVM,
    eventScreenVM: EventScreenVM,
    mainScreenVM: MainScreenVM ,
) {

//  Date Picker
    val mContext = LocalContext.current
    val mYear: Int
    val mMonth: Int
    val mDay: Int
    var mHour: Int = -1
    var mMinute: Int = -1
    var mSecond: Int = -1

    val mCalendar = Calendar.getInstance()

    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = java.util.Date()
    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            val tempMonth = if(mMonth+1<10) "0" else ""
            val tempDate = if(mDayOfMonth<10) "0" else ""
            val tempHour = if(mHour<10) "0" else ""
            val tempMinute = if(mMinute<10) "0" else ""
            val tempSecond = if(mSecond<10) "0" else ""
            eventScreenVM.onTimestampChange(("$mYear$tempMonth${(mMonth+1)}$tempDate${mDayOfMonth}$tempHour${mHour}$tempMinute${mMinute}$tempSecond${mSecond}").toLong())
            eventScreenVM.date = "$mYear/$tempMonth${(mMonth+1)}/$tempDate${mDayOfMonth}"

        }, mYear, mMonth, mDay
    )

    ModalBottomSheet(
        onDismissRequest = {
            mainScreenVM.onEventScreenDisappear()
            mainScreenVM.onStopEdit()
            eventScreenVM.resetCurrentEvent()
        }
    ) {

        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
                    .height(40.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                if (eventScreenVM.title_.value != "") {
                    TextButton(
                        onClick = {
                            if(!mainScreenVM.isEditGoingOn){
                                calenderScreenVM.addEvent(EventItem(
                                    title = eventScreenVM.title_.value,
                                    desc = eventScreenVM.desc_.value,
                                    category = eventScreenVM.category.color,
                                    timestamp = eventScreenVM.timestamp.value
                                ))
                                eventScreenVM.resetCurrentEvent()
                                Toast.makeText(mContext,"Event is added successfully", Toast.LENGTH_SHORT).show()
                            }
                            else{
                                calenderScreenVM.editEvent(EventItem(
                                    title = eventScreenVM.title_.value,
                                    desc = eventScreenVM.desc_.value,
                                    category = eventScreenVM.category.color,
                                    timestamp = eventScreenVM.timestamp.value
                                ))
                                eventScreenVM.resetCurrentEvent()
                                Toast.makeText(mContext,"Event has been edited successfully", Toast.LENGTH_SHORT).show()
                                mainScreenVM.onStopEdit()
                            }
                        },
                    ) {
                        if(mainScreenVM.isEditGoingOn)
                        Text("Edit")
                        else
                        Text("Save")
                    }
                }
            }
            Text(
                "Add Event",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )
            TextField(
                value = eventScreenVM.title_.value,
                onValueChange = { eventScreenVM.onTitleChange(it)
                                Log.d("eventScreeVm title val : ", eventScreenVM.title_.value)},
                label = { Text("Title") },
                placeholder = {
                    Text(
                        "Add title",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp),
                maxLines = 1,
                textStyle = MaterialTheme.typography.titleLarge
            )

            TextField(
                value = eventScreenVM.desc_.value,
                onValueChange = { eventScreenVM.onDescChange(it) },
                label = {
                    Row(
                        modifier = Modifier.height(20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.List,
                            contentDescription = "Description"
                        )
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

            Spacer(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp))

            Button(onClick = {
                mDatePickerDialog.show()
                mHour = LocalTime.now().hour
                mMinute = LocalTime.now().minute
                mSecond = LocalTime.now().second
            },
                modifier = Modifier.fillMaxWidth(),
                enabled = !mainScreenVM.isEditGoingOn
            ) {
                Text(text = "Select Date : ${eventScreenVM.date}", modifier = Modifier.padding(12.dp))
            }

            Spacer(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {eventScreenVM.onDropdownExpand()},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ){
                    Row {
                        Text(
                            text = "Category ",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(start = 20.dp)
                        )
                        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null)
                        Text(
                            text = " ${eventScreenVM.category.name}",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(start = 20.dp)
                        )
                    }

                }

                DropdownMenu(
                    expanded = eventScreenVM.isDropdownExpanded,
                    onDismissRequest = {eventScreenVM.onDropdownDismiss()}
                ) {
                    for (color in ColorCategories.entries){
                        DropdownMenuItem(
                            text = {
                                Text(text = color.name)
                            },
                            modifier = Modifier.background(Color.Gray),
                            onClick = {
                                eventScreenVM.onCategoryChange(color)
                                eventScreenVM.onDropdownDismiss()
                                      },
                            colors = MenuItemColors(
                                textColor = Color(color.color),
                                leadingIconColor = Color(color.color),
                                trailingIconColor = Color(color.color),
                                disabledTextColor = Color(color.color),
                                disabledLeadingIconColor = Color(color.color),
                                disabledTrailingIconColor = Color(color.color)
                            ),
                            leadingIcon = { Icon(Icons.Default.CheckCircle, contentDescription = null, modifier = Modifier.padding(end = 8.dp)) }
                        )

                    }
                }
            }
        }
    }
}