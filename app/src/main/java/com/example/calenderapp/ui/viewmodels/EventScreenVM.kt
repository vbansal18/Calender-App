package com.example.calenderapp.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.calenderapp.data.ColorCategories

class EventScreenVM  : ViewModel() {
//    DROPDOWN Category
    var isDropdownExpanded by mutableStateOf(false)
        private set

    fun onDropdownExpand() {
        isDropdownExpanded = true
    }

    fun onDropdownDismiss() {
        isDropdownExpanded = false
    }

//  CurrentEvent



    var date by mutableStateOf("")

    var category by mutableStateOf(ColorCategories.Red)
        private set
    var timestamp = mutableStateOf<Long>(-1)
        private set
    var title_ = mutableStateOf("")
        private set
    var desc_ = mutableStateOf("")
        private set

    fun onTitleChange(title : String) {
         title_.value = title
        Log.d("Change: title", title_.value)
    }
    fun onDescChange(desc : String) {
        desc_.value = desc
        Log.d("Change: desc", desc_.value)

    }
    fun onTimestampChange(timestamp_ : Long) {
        timestamp.value = timestamp_
        date = timestamp.value.toString()
        Log.d("Change: timestamp", "${timestamp.value}")

    }
    fun onCategoryChange(color : ColorCategories) {
        category = color
        Log.d("Change: category", category.name)

    }

    fun resetCurrentEvent() {
        title_.value = ""
        desc_.value = ""
        category = ColorCategories.Red
        timestamp.value = -1
        date = ""
    }

}