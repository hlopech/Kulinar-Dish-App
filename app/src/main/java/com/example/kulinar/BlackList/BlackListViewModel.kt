package com.example.kulinar.BlackList

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.kulinar.MainViewModel
import com.example.kulinar.data.ExcludedIngredientEntity
import com.example.kulinar.data.ExcludedIngredientsRepository
import com.example.kulinar.models.IngredientUI
import kotlinx.coroutines.launch

class BlackListViewModel : ViewModel() {
    val isLoading = mutableStateOf(false)
    val ingredientsList = mutableStateOf(mutableListOf<IngredientUI>())
    val searchQuery = mutableStateOf("")

    fun searchIngredients(
        query: String,
        context: Context,
        repository: ExcludedIngredientsRepository
    ) {
        isLoading.value = true
        viewModelScope.launch {
            ingredientsList.value.clear()
            repository.searchIngredients(
                query,
                context,
                ingredientsList,
                isLoading
            )
            searchQuery.value = ""
        }
    }

}
