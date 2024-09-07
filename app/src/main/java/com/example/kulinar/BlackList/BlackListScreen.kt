package com.example.kulinar.BlackList

import android.annotation.SuppressLint
import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kulinar.MainActivity
import com.example.kulinar.MainViewModel
import com.example.kulinar.R
import com.example.kulinar.data.ExcludedIngredientEntity
import com.example.kulinar.ui.theme.FavorietesTopAppBarBg
import com.example.kulinar.ui.theme.Green
import com.example.kulinar.ui.theme.GreenLight
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun BlackListScreen(
    drawerState: DrawerState,
    mainViewModel: MainViewModel,
) {
    val context = LocalContext.current as MainActivity
    val coroutineScope = rememberCoroutineScope()
    val excludedIngredients =
        mainViewModel.excludedIngredients.collectAsState(initial = emptyList())

    val blackListViewModel: BlackListViewModel = viewModel()

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()


    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Black list",
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
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = FavorietesTopAppBarBg,
                    titleContentColor = Color.White
                ),
                scrollBehavior = scrollBehavior,

                )
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(start = 20.dp, end = 20.dp)
                .verticalScroll(
                    rememberScrollState()
                ),
        ) {
            Spacer(modifier = Modifier.height(150.dp))

            Text(
                text = "Here you can select the ingredients that you want to exclude from your diet, and recipes with them will not appear in the search.",
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight(300))
            )
            Spacer(modifier = Modifier.height(50.dp))
            FlowRow() {

                excludedIngredients.value.forEach { item ->
                    Card(
                        colors = CardDefaults.cardColors(Color.Red),
                        elevation = CardDefaults.cardElevation(defaultElevation = 20.dp),
                        modifier = Modifier.padding(2.dp), shape = RoundedCornerShape(20.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {

                            Text(
                                text = item.ingredient,
                                fontSize = 20.sp,
                                color = Color.White,
                                fontWeight = FontWeight(900),
                                modifier = Modifier.padding(
                                    top = 5.dp,
                                    bottom = 5.dp,
                                    start = 10.dp
                                )

                            )
                            IconButton(onClick = { mainViewModel.deleteExcludedIngredient(item) }) {
                                Icon(
                                    imageVector = Icons.Rounded.Clear,
                                    contentDescription = "",
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }

                }
            }

            Spacer(modifier = Modifier.height(50.dp))


            TextField(
                value = blackListViewModel.searchQuery.value,
                onValueChange = { newValue ->
                    blackListViewModel.searchQuery.value = newValue
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(RoundedCornerShape(28.dp)),
                placeholder = { Text("Search...") },
                singleLine = true,
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.search_icon),
                        modifier = Modifier.size(20.dp),
                        contentDescription = ""
                    )
                },
                keyboardActions = KeyboardActions(onSearch = {

                    blackListViewModel.searchIngredients(
                        blackListViewModel.searchQuery.value,
                        context,
                        mainViewModel.excludedIngredientsRepository
                    )

                    val inputMethodManager =
                        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(context.currentFocus?.windowToken, 0)
                }),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Search
                ),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = Color.Black,
                )
            )

            if (blackListViewModel.isLoading.value == true) {
                Spacer(modifier = Modifier.height(80.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(100.dp),
                        color = Green,
                        strokeWidth = 8.dp
                    )
                }
            } else if (blackListViewModel.ingredientsList.value.isNotEmpty()) {
                Column() {
                    Spacer(modifier = Modifier.height(40.dp))

                    blackListViewModel.ingredientsList.value.forEach { item ->

                        Card(
                            modifier = Modifier
                                .padding(2.dp)
                                .fillMaxWidth()
                                .clickable {
                                    mainViewModel.addExcludedIngredient(
                                        ExcludedIngredientEntity(
                                            item.ingredient
                                        )
                                    )
                                    blackListViewModel.ingredientsList.value.clear()
                                },
                            elevation = CardDefaults.cardElevation(20.dp),
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(GreenLight)
                        ) {

                            Text(
                                text = item.ingredient,
                                style = TextStyle(fontSize = 23.sp, fontWeight = FontWeight(900)),
                                modifier = Modifier
                                    .padding(15.dp),
                                color = Color.White
                            )
                        }

                    }

                }
            } else {
                Spacer(modifier = Modifier.height(50.dp))

                Text(
                    text = "There's nothing here yet. Enter a query to search for ingredients",
                    style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight(300)),
                    modifier = Modifier.padding(20.dp),
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.height(100.dp))

        }

    }
}
