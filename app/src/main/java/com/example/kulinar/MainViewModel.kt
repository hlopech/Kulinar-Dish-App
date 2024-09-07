package com.example.kulinar

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kulinar.data.DishRepository
import com.example.kulinar.data.ExcludedIngredientEntity
import com.example.kulinar.data.ExcludedIngredientsRepository
import com.example.kulinar.data.SearchHistoryItemEntity
import com.example.kulinar.data.SearchHistoryRepository
import com.example.kulinar.data.ShopListItemEntity
import com.example.kulinar.data.ShopListRepository
import com.example.kulinar.data.mapper.toShopListItem
import com.example.kulinar.models.DishUI
import kotlinx.coroutines.launch

class MainViewModel(
    val dishRepository: DishRepository,
    val searchHistoryRepository: SearchHistoryRepository,
    val shopListRepository: ShopListRepository,
    val excludedIngredientsRepository: ExcludedIngredientsRepository
) : ViewModel() {

    val shopList get() = shopListRepository.getShopList()

    val searchHistory get() = searchHistoryRepository.getAllSearchHistoryItems()

    val favoritesDishes get() = dishRepository.getAllFavoritesDishes()

    val selectedRecipe: MutableState<DishUI?> = mutableStateOf(null)

    val excludedIngredients get() = excludedIngredientsRepository.getExcludedIngredients()

    fun addExcludedIngredient(excludedIngredientEntity: ExcludedIngredientEntity) {
        viewModelScope.launch {
            excludedIngredientsRepository.insertExcludedIngredient(excludedIngredientEntity)
        }
    }
    fun deleteExcludedIngredient(excludedIngredientEntity: ExcludedIngredientEntity) {
        viewModelScope.launch {
            excludedIngredientsRepository.deleteExcludedIngredient(
                excludedIngredientEntity
            )
        }
    }
    fun addDishToShopList(dishUI: DishUI) {
        viewModelScope.launch {
            shopListRepository.insertShopListItem(dishUI.toShopListItem())
        }
    }
    fun deleteIngredient(
        dish: ShopListItemEntity,
        ingredient: String,
        isSheetOpen: MutableState<Boolean>
    ) {
        viewModelScope.launch {
            val ingredients = dish.ingredients.toMutableList()
            ingredients.remove(ingredient)
            if (ingredients.isEmpty()) {
                shopListRepository.deleteShopListItem(dish)
                isSheetOpen.value = false
            } else {
                val updatedDish = dish.copy(ingredients = ingredients)
                shopListRepository.updateShopListItem(updatedDish)
            }
        }
    }

    fun addSearchHistoryItem(searchItem: SearchHistoryItemEntity) {
        viewModelScope.launch {
            searchHistoryRepository.insertSearchHistoryItem(searchItem)
        }
    }
    fun deleteSearchHistoryItem(searchItem: SearchHistoryItemEntity) {
        viewModelScope.launch {
            searchHistoryRepository.deleteSearchHistoryItem(searchItem)
        }
    }
    suspend fun isDishFavorite(dishTitle: String): Boolean {
        return dishRepository.isDishExists(dishTitle)
    }

    fun addFavoriteDish(dishUI: DishUI) {
        viewModelScope.launch {
            if (dishRepository.getDishByTitle(dishUI.title) == null) {
                dishRepository.insertDish(dishUI)
            } else {
                dishRepository.deleteDish(dishUI)

            }

        }
    }


}

