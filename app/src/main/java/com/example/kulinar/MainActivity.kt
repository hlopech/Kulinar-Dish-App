package com.example.kulinar

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.kulinar.navigation.BottomNavigation
import com.example.kulinar.navigation.DrawerItem
import com.example.kulinar.navigation.NavGraph
import com.example.kulinar.data.AppDatabase
import com.example.kulinar.data.DishRepository
import com.example.kulinar.data.ExcludedIngredientsRepository
import com.example.kulinar.data.SearchHistoryRepository
import com.example.kulinar.data.ShopListRepository
import com.example.kulinar.ui.theme.Green
import kotlinx.coroutines.launch


val API_KEY = "7248f002bb24400fa69e4f70d631db62"


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen() {
    val coroutineScope = rememberCoroutineScope()
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val db = AppDatabase.getDatabase(LocalContext.current)
    val dishRepository = DishRepository(db)
    val searchHistoryRepository = SearchHistoryRepository(db)
    val shopListRepository = ShopListRepository(db)
    val excludedIngredientsRepository = ExcludedIngredientsRepository(db)

    val mainViewModel = MainViewModel(
        dishRepository = dishRepository,
        searchHistoryRepository = searchHistoryRepository,
        shopListRepository = shopListRepository,
        excludedIngredientsRepository = excludedIngredientsRepository
    )

    Scaffold(bottomBar = { BottomNavigation(navController = navController) }) {
        ModalNavigationDrawer(

            drawerContent = {

                ModalDrawerSheet(
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.drawer_header_bg),
                            contentDescription = "",

                            )


                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.app_logo_without_bg),
                                contentDescription = "",
                                modifier = Modifier.size(200.dp),
                                contentScale = ContentScale.Crop
                            )
                            Text(
                                text = "Taste Trove ",
                                style = TextStyle(
                                    fontFamily = FontFamily.SansSerif,
                                    fontSize = 35.sp,
                                    fontWeight = FontWeight.ExtraBold
                                ),
                                color = Color.White,
                                modifier = Modifier.padding(top = 30.dp)
                            )

                        }
                    }

                    Divider()
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 40.dp)

                    ) {
                        Column {

                            NavigationDrawerItem(
                                label = {
                                    Text(
                                        text = "Shop List",
                                        style = TextStyle(
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.ExtraBold
                                        ),
                                        color = Green
                                    )
                                },
                                selected = false,
                                icon = {
                                    Icon(
                                        painterResource(id = R.drawable.shop_list_drawer_icon),
                                        contentDescription = "",
                                        tint = Green
                                    )
                                },
                                onClick = {
                                    coroutineScope.launch { drawerState.close() }
                                    navController.navigate(DrawerItem.ShopList.route) {
                                        popUpTo(0)
                                    }
                                },
                            )

                            NavigationDrawerItem(
                                label = {
                                    Text(
                                        text = "Black list",
                                        style = TextStyle(
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.ExtraBold
                                        ),
                                        color = Green
                                    )
                                },
                                selected = false,
                                icon = {
                                    Icon(
                                        painterResource(id = R.drawable.black_list_icon),
                                        contentDescription = "",
                                        tint = Green
                                    )
                                },
                                onClick = {
                                    coroutineScope.launch { drawerState.close() }
                                    navController.navigate(DrawerItem.Setting.route) {
                                        popUpTo(0)
                                    }
                                },
                            )
                        }

                    }
                }
            },
            drawerState = drawerState,
            gesturesEnabled = true,
        ) {
            NavGraph(navHostController = navController, navController, drawerState, mainViewModel)

        }

    }
}
