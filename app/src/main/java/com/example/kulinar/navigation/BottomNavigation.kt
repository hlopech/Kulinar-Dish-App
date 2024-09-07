package com.example.kulinar.navigation

import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.kulinar.ui.theme.Green

@Composable
fun BottomNavigation(
    
    navController: NavController
) {

    val listItems = listOf(
        BottomItem.Home,
        BottomItem.Search,
        BottomItem.Favorites
    )

    var selectedItem by remember {
        mutableStateOf(0)
    }
    NavigationBar(
        modifier = Modifier.height(60.dp),
    ) {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route
        listItems.forEachIndexed() { index, item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route)
                    selectedItem = index
                },
                icon = {
                    Icon(
                        painter = painterResource(id = item.iconId),
                        contentDescription = "",
                        tint = when (selectedItem) {
                            index -> Green
                            else -> Color.Gray

                        }
                    )
                },
                label = {

                })

        }
    }
}