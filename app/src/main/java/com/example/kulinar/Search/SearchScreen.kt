package com.example.kulinar.Search

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.kulinar.Search.UI.DishListItem
import com.example.kulinar.MainActivity
import com.example.kulinar.MainViewModel
import com.example.kulinar.R
import com.example.kulinar.Search.UI.CategoryItem
import com.example.kulinar.Search.UI.HistoryItem
import com.example.kulinar.data.SearchHistoryItemEntity
import com.example.kulinar.models.CategoryItemModel
import com.example.kulinar.ui.theme.Green

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun SearchScreen(
    drawerState: DrawerState,
    mainViewModel: MainViewModel,
    navController: NavController
) {

    val isSearchBarFocused = remember { mutableStateOf(false) }
    val context = LocalContext.current as MainActivity
    val viewModel: SearchViewModel = viewModel()
    val searchText = viewModel.searchText
    val isLoading = viewModel.isLoading
    val isError = viewModel.isError
    val bgImage = viewModel.bgImage
    val searchHistory = mainViewModel.searchHistory.collectAsState(initial = emptyList())

    mainViewModel.selectedRecipe.value = null


    val excludedIngredients =
        mainViewModel.excludedIngredients.collectAsState(initial = emptyList())

    var excludedIngredientsString = ""

    excludedIngredients.value.forEach { it -> excludedIngredientsString += it.ingredient.toString() + "," }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        ImageBackground(bgImage.value)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                query = searchText.value,
                onQueryChange = { text -> searchText.value = text },
                onSearch = {
                    viewModel.showCategory.value = false
                    isLoading.value = true
                    viewModel.dishes.value = listOf()
                    mainViewModel.dishRepository.searchDish(
                        context,
                        viewModel.searchText,
                        viewModel.dishType,
                        viewModel.isLoading,
                        viewModel.isError,
                        viewModel.dishes,
                        viewModel.isNoResult,
                        excludedIngredientsString
                    )
                    mainViewModel.addSearchHistoryItem(SearchHistoryItemEntity(searchText.value))
                    isSearchBarFocused.value = false

                },
                active = isSearchBarFocused.value,
                onActiveChange = { isActive -> isSearchBarFocused.value = isActive },
                placeholder = {
                    Text(text = "Search...")
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.search_icon),
                        modifier = Modifier.size(25.dp),
                        contentDescription = ""
                    )
                }
            ) {

                Column {
                    Text(
                        text = "History",
                        style = TextStyle(fontSize = 30.sp, fontWeight = FontWeight.Bold),
                        fontSize = 20.sp,
                        color = Color.Black
                    )
                    LazyColumn() {
                        itemsIndexed(searchHistory.value) { index, item ->
                            HistoryItem(
                                mainViewModel,
                                item = item,
                                viewModel,
                                isSearchBarFocused,
                                context,
                            )
                        }
                    }
                }
            }


            if (viewModel.dishType.value != "") {
                viewModel.showCategory.value = false
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
                ) {

                    Text(
                        text = viewModel.dishType.value,
                        style = TextStyle(fontSize = 25.sp, color = Color.White)
                    )
                    IconButton(onClick = {
                        viewModel.clearParams()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.edit_icon),
                            contentDescription = "",
                            tint = Color.White
                        )
                    }
                }
            }

            if (viewModel.showCategory.value) {
                Spacer(modifier = Modifier.height(50.dp))
                LazyVerticalGrid(
                    horizontalArrangement = Arrangement.Center,
                    columns = GridCells.Fixed(3),
                    modifier = Modifier
                        .fillMaxWidth()

                )
                {
                    itemsIndexed(viewModel.categories) { index, item ->
                        CategoryItem(item = item, viewModel, context, mainViewModel)

                    }
                }

            }

            if (isLoading.value) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 200.dp),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(100.dp),
                        color = Green,
                        strokeWidth = 8.dp
                    )
                }
            } else if (isError.value) {
                Log.d("ERROR", "ERROR")
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
//                            .background(Color.White)
                        elevation = CardDefaults.elevatedCardElevation(20.dp),

                        ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {


                            Text(
                                text = "Network error",
                                style = TextStyle(fontSize = 20.sp)
                            )
                            Text(
                                text = "Try again",
                                modifier = Modifier.clickable {
                                    isLoading.value = true
                                },
                                style = TextStyle(fontSize = 20.sp)
                            )
                        }
                    }


                }

            } else if (viewModel.isNoResult.value == true) {
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
                                text = "Nothing was found for your request",
                                style = TextStyle(fontSize = 20.sp),
                            )
                        }
                    }


                }
            }
            LazyVerticalGrid(
                modifier = Modifier
                    .width(400.dp)
                    .padding(bottom = 50.dp),
                columns = GridCells.Adaptive(minSize = 190.dp),
            ) {

                itemsIndexed(viewModel.dishes.value) { index, item ->
                    DishListItem(
                        navController,
                        item = item,
                        mainViewModel = mainViewModel,
                        context,
                        drawerState
                    )
                }
            }
        }
    }
}

@Composable
fun ImageBackground(image: Int) {
    val painter: Painter = painterResource(id = image)

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillHeight
        )
    }
}

