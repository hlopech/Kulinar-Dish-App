package com.example.kulinar.Favorites

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.kulinar.Favorites.UI.DishFavoriteListItem
import com.example.kulinar.MainViewModel
import com.example.kulinar.R
import com.example.kulinar.data.mapper.toUI
import com.example.kulinar.ui.theme.FavorietesTopAppBarBg
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun FavoritesScreen(
    drawerState: DrawerState,
    mainViewModel: MainViewModel,
    navController: NavController
) {
    val viewModel: FavoritesViewModel = viewModel()
    val dishes = mainViewModel.favoritesDishes.collectAsState(initial = emptyList())
    val coroutineScope = rememberCoroutineScope()
    var expanded by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current


    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Favorites",
                        style = TextStyle(fontSize = 30.sp, fontWeight = FontWeight.ExtraBold)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        coroutineScope.launch {
                            drawerState.run { open() }
                        }

                    }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "",
                            tint = Color.White,
                            modifier = Modifier.size(40.dp),

                            )
                    }
                },
                actions = {
                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(
                            painter = painterResource(id = R.drawable.favorites_filter_icon),
                            contentDescription = "",
                            modifier = Modifier.size(35.dp),
                            tint = Color.White
                        )
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = {
                                expanded = false
                            },
                        ) {
                            viewModel.sorts.forEach { item ->
                                DropdownMenuItem(
                                    text = { Text(text = item) },
                                    onClick = {
                                        expanded = !expanded
                                        viewModel.currentSort.value = item
                                        Toast.makeText(context, "Sort $item selected", Toast.LENGTH_SHORT).show()
                                    })
                            }

                        }
                    }
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = FavorietesTopAppBarBg,
                    titleContentColor = Color.White
                )

            )
        },
    ) {

        Image(
            painter = painterResource(id = R.drawable.favorites_page_bg),
            contentDescription = "",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillHeight
        )

        Column(
        ) {
            if (dishes.value.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Card(
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier
                            .fillMaxHeight(0.4f)
                            .fillMaxWidth(0.8f),
                        elevation = CardDefaults.elevatedCardElevation(20.dp),

                        ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {

                            Text(
                                text = "There's nothing here yet",
                                style = TextStyle(fontSize = 20.sp),
                            )
                        }
                    }
                }

            } else {

                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {


                    LazyVerticalGrid(
                        modifier = Modifier
                            .width(400.dp),
                        columns = GridCells.Adaptive(minSize = 190.dp),
                    ) {

                        item {
                            Spacer(modifier = Modifier.height(100.dp))
                        }
                        item {
                            Spacer(modifier = Modifier.height(100.dp))
                        }

                        itemsIndexed(viewModel.sortDishes(dishes.value)) { index, item ->
                            DishFavoriteListItem(
                                item = item.toUI(),
                                mainViewModel = mainViewModel,
                                context = context,
                                navController
                            )
                        }
                        item {
                            Spacer(modifier = Modifier.height(100.dp))
                        }
                        item {
                            Spacer(modifier = Modifier.height(100.dp))
                        }
                    }
                }

            }

        }
    }
}

