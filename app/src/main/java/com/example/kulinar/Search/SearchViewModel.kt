package com.example.kulinar.Search

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.kulinar.R
import com.example.kulinar.models.CategoryItemModel
import com.example.kulinar.models.DishPreviewUI

class SearchViewModel : ViewModel() {

    val categories = listOf<CategoryItemModel>(
        CategoryItemModel(
            "Breakfast",
            R.drawable.category_breakfast_bg,
            "breakfast"
        ),
        CategoryItemModel(
            "Snack",
            R.drawable.category_snack_bg,
            "snack"
        ),
        CategoryItemModel(
            "Soup",
            R.drawable.category_soup_bg,
            "soup"
        ),
        CategoryItemModel(
            "Main dish",
            R.drawable.category_main_dish_bg,
            "main course"
        ),
        CategoryItemModel(
            "Desserts",
            R.drawable.category_dessert_bg,
            "dessert"
        ),
        CategoryItemModel(
            "Drink",
            R.drawable.category_drink_bg,
            "drink"
        ),
        CategoryItemModel(
            "Salad",
            R.drawable.category_side_dish_bg,
            "salad"
        ),
    )


    val searchText: MutableState<String> = mutableStateOf("")
    val dishType: MutableState<String> = mutableStateOf("")

    val isLoading: MutableState<Boolean> = mutableStateOf(false)
    val isError: MutableState<Boolean> = mutableStateOf(false)
    val dishes: MutableState<List<DishPreviewUI>> = mutableStateOf(listOf())
    val isNoResult: MutableState<Boolean?> = mutableStateOf(null)
    val showCategory: MutableState<Boolean> = mutableStateOf(true)
    val bgImage: MutableState<Int> = mutableStateOf(R.drawable.search_default_bg)


    fun clearParams() {
        showCategory.value = true
        bgImage.value = R.drawable.search_default_bg
        dishes.value = listOf()
        searchText.value = ""
        dishType.value = ""
        isNoResult.value = false

    }

}

