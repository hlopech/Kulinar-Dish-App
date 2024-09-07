package com.example.kulinar.Favorites

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.kulinar.data.DishEntity

class FavoritesViewModel : ViewModel() {
    val currentSort: MutableState<String> = mutableStateOf<String>("")

    val sorts = listOf("Newest", "Oldest", "By time")

    fun sortDishes(dishes: List<DishEntity>): List<DishEntity> {
        when (currentSort.value) {
            "By time" -> return dishes.sortedBy { it.readyInMinutes }
            "Oldest" -> return dishes
            "Newest" -> return dishes.reversed()
        }
        return dishes
    }

}