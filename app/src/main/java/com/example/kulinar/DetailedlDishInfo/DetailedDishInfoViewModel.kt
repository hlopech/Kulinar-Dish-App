package com.example.kulinar.Search

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.kulinar.models.DishUI

class DetailedDishInfoViewModel : ViewModel() {


    val dish: MutableState<DishUI?> = mutableStateOf(
        null
    )


}

