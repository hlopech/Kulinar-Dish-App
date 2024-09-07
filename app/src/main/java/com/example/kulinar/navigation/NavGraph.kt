package com.example.kulinar.navigation

import DetailedDishInfoScreen
import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.kulinar.Favorites.FavoritesScreen
import com.example.kulinar.Home.HomeScreen
import com.example.kulinar.MainViewModel
import com.example.kulinar.BlackList.BlackListScreen
import com.example.kulinar.Search.SearchScreen
import com.example.kulinar.ShopList.ShopListScreen

@Composable
fun NavGraph(
    navHostController: NavHostController,
    navController: NavController,
    drawerState: DrawerState,
    mainViewModel: MainViewModel,
) {
    NavHost(navController = navHostController, startDestination = "Home") {
        composable("Home") {
            HomeScreen(navController, drawerState, mainViewModel)
        }
        composable("Search") {
            SearchScreen(drawerState, mainViewModel, navController)
        }
        composable("Favorites") {
            FavoritesScreen(drawerState, mainViewModel, navController)
        }
        composable("ShopList") {
            ShopListScreen(drawerState, mainViewModel, navController)
        }
        composable("DetalInfo") {
            DetailedDishInfoScreen(drawerState, mainViewModel, navController)
        }
        composable("Setting") {
            BlackListScreen(drawerState, mainViewModel)
        }
    }
}