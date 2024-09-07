package com.example.kulinar.navigation

import com.example.kulinar.R

sealed class BottomItem(val title: String, val iconId: Int, val route: String) {
    data object Home : BottomItem("Home", R.drawable.home_icon, "Home")
    data object Search : BottomItem("Search", R.drawable.search_nav_item, "Search")
    data object Favorites : BottomItem("Favorites", R.drawable.favorites_dish_icon, "Favorites")
}