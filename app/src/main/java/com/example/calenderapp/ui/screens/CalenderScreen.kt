package com.example.calenderapp.ui.screens
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calenderapp.data.local.EventItem
import com.example.calenderapp.ui.viewmodels.CalenderScreenVM
import com.example.calenderapp.ui.viewmodels.EventScreenVM
import com.example.calenderapp.ui.viewmodels.MainScreenVM
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale


@Composable
fun CalendarView(modifier: Modifier = Modifier, calenderScreenVM: CalenderScreenVM, mainScreenVM: MainScreenVM, eventScreenVM: EventScreenVM) {
    val today = LocalDate.now()
    val pagerState = rememberPagerState(initialPage = 0)
    val allEvents = calenderScreenVM.allEvents.collectAsState(emptyList()).value

    Column(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            count = Int.MAX_VALUE,
            state = pagerState,
            modifier = Modifier
                .weight(1f)
                .statusBarsPadding()
        ) { pageOffset ->
            val yearMonth = YearMonth.now().plusMonths(pageOffset.toLong())
            CalendarMonthView(
                yearMonth = yearMonth,
                today = today,
                calenderScreenVM = calenderScreenVM,
                events = allEvents,
                mainScreenVM = mainScreenVM,
            )
        }
    }
}

@Composable
fun CalendarMonthView(
    yearMonth: YearMonth,
    today: LocalDate,
    calenderScreenVM: CalenderScreenVM,
    events: List<EventItem>,
    mainScreenVM: MainScreenVM
) {
    val daysInMonth = yearMonth.lengthOfMonth()
    val firstDayOfMonth = yearMonth.atDay(1).dayOfWeek.value % 7 // 0-based (Sun = 0)
    val currentMonth = yearMonth.month.getDisplayName(TextStyle.FULL, Locale.getDefault())
    val currentYear = yearMonth.year
    val tempMonth = if(yearMonth.month.value+1<10) "0" else ""
    val tempMonthPlus1 = if((yearMonth.month.value+2)%12<10) "0" else ""
    val tempYear = if(yearMonth.month.value==12) currentYear+1 else currentYear
    val allEvents = events.filter { list->
        list.timestamp >= "${yearMonth.year}$tempMonth${yearMonth.month.value}01000000".toLong() && list.timestamp < "${tempYear}$tempMonthPlus1${(yearMonth.month.value+1)%12}01000000".toLong()
    }
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "$currentMonth $currentYear",
            fontSize = 24.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            textAlign = TextAlign.Center
        )

        // Days of the Week Header
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp)) {
            listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat").forEach { day ->
                Text(
                    text = day,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                )
            }
        }

        // Calendar Days
        val totalCells = daysInMonth + firstDayOfMonth
        val rows = (totalCells + 6) / 7
        Column(modifier = Modifier.fillMaxWidth()) {
            repeat(rows) { row ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    for (col in 0..6) {
                        val day = row * 7 + col - firstDayOfMonth + 1
                        if (day in 1..daysInMonth) {
                            val tempDay = (if(day<10) "0" else "")
                            val tempDayEnd = (if(day<9 || (day+1)%(daysInMonth+1)==0) "0" else "")
                            val isToday = today.dayOfMonth == day && today.month == yearMonth.month && today.year == yearMonth.year
                            val eventDayList = allEvents.filter { event->
                                event.timestamp >= "${yearMonth.year}$tempMonth${yearMonth.month.value}$tempDay${day}000000".toLong() && event.timestamp < "${yearMonth.year}$tempMonth${yearMonth.month.value}$tempDayEnd${(day)%daysInMonth+1}000000".toLong()
                            }
                            Log.d("1EventDayList - calender Screen", "${yearMonth.year}$tempMonth${yearMonth.month.value}$tempDay${day}000000".toLong().toString())
                            Log.d("2EventDayList - calender Screen", "${yearMonth.year}$tempMonth${yearMonth.month.value}$tempDayEnd${(day)%(daysInMonth)+1}000000".toLong().toString())
                            Log.d("xEventDayList X - calender Screen", eventDayList.toString())
                            Log.d("xEventDayList allEvents - calender Screen", allEvents.toString())

                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .aspectRatio(1f)
                                    .clip(CircleShape)
                                    .background(if (isToday) Color.Blue else Color.Transparent)
                                    .clickable {
                                        calenderScreenVM.setEventsListScreenEvents(eventDayList)
                                        mainScreenVM.onEventListScreenAppear()
                                    },
                                contentAlignment = Alignment.Center,
                            ) {
                                Row {
                                    Text(
                                        text = day.toString(),
                                        color = if (isToday) Color.White else Color.Black,
                                        fontSize = 16.sp
                                    )
                                    if(eventDayList.isNotEmpty()){
                                        Icon(
                                            Icons.Default.Star,
                                            contentDescription = null,
                                            tint = Color.Red,
                                            modifier = Modifier.size(8.dp)
                                        )
                                    }
                                }
                            }
                        } else {
                            Spacer(modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f))
                        }
                    }
                }
            }
            Column(
                Modifier.fillMaxSize().padding(top = 30.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Total events overall : ${events.size}")
                Text("Total events this month : ${allEvents.size}")
            }

        }

    }
}
