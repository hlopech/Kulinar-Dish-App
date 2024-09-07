package com.example.kulinar.Home

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.kulinar.MainViewModel
import com.example.kulinar.R
import com.example.kulinar.Search.SearchViewModel
import com.example.kulinar.models.DishUI
import com.example.kulinar.ui.theme.Green
import com.example.kulinar.ui.theme.Grey
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    navController: NavController,
    drawerState: DrawerState,
    mainViewModel: MainViewModel,
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val randomDish: MutableState<DishUI?> = remember {
        mutableStateOf(null)
    }

    val viewModel: HomeScreenViewModel = viewModel()
    val isLoading = viewModel.isLoading
    val isError = viewModel.isError

    Scaffold(topBar = {

        CenterAlignedTopAppBar(
            title = { Text(text = "") },
            navigationIcon = {
                IconButton(onClick = {

                    coroutineScope.launch {
                        drawerState.run { open() }
                    }

                }) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "",
                        tint = Color.Black
                    )
                }
            },
            colors = TopAppBarDefaults.largeTopAppBarColors(
                containerColor = Color.Transparent,

                )
        )
    })
    {

        Image(
            painter = painterResource(id = R.drawable.home_page_bg),
            contentDescription = "",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillHeight
        )


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(
                    rememberScrollState()
                )
        ) {
            Box {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.background(Color.White)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = "Taste Trove -",
                            style = TextStyle(fontSize = 50.sp),
                            fontWeight = FontWeight.ExtraBold,
                            color = Green,
                            modifier = Modifier.padding(top = 30.dp)

                        )
                    }

                    Text(
                        text = "is your guide to the world of cooking, here you can find a recipe for every taste with a detailed explanation.",
                        style = TextStyle(fontSize = 20.sp),
                        fontWeight = FontWeight.Light,
                        fontStyle = FontStyle.Italic,
                        color = Color.Black,
                        modifier = Modifier.padding(30.dp)
                    )

                    Card(
                        elevation = CardDefaults.cardElevation(100.dp),
                        modifier = Modifier
                            .padding(
                                10.dp,
                            ),
                        colors = CardDefaults.cardColors(Green),
                        shape = RoundedCornerShape(40.dp)
                    )
                    {
                        Button(
                            onClick = {
                                navController.navigate("Search")

                            },
                            colors = ButtonDefaults.buttonColors(Green)
                        ) {
                            Text(
                                text = "Find dish now!",
                                style = TextStyle(
                                    fontSize = 30.sp,
                                    fontWeight = FontWeight(500)
                                ),
                                modifier = Modifier.padding(
                                    10.dp,
                                ),
                                color = Color.White
                            )
                        }


                    }
                    Spacer(modifier = Modifier.height(30.dp))
                }
            }

            Column() {

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(20.dp))

                    Card(
                        colors = CardDefaults.cardColors(Grey),
                        modifier = Modifier.fillMaxWidth(0.9f)
                    ) {
//
                        Text(
                            text = "Don't know what to cook? Then let us choose the dishes for you. Click on a random dish!",
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight(300)
                            ),
                            modifier = Modifier.padding(5.dp)
                        )

                    }
                    Spacer(modifier = Modifier.height(20.dp))


                    Button(
                        onClick = {
                            randomDish.value = null
                            coroutineScope.launch {
                                mainViewModel.dishRepository.getRandomDish(
                                    context,
                                    randomDish,
                                    isLoading,
                                    isError
                                )

                            }
                        },
                        colors = ButtonDefaults.buttonColors(Color.White)
                    ) {

                        Text(
                            text = "Get random dish!",
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight(900)
                            ),
                            modifier = Modifier.padding(
                                5.dp,
                            ),
                            color = Green
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    if (randomDish.value == null && !isLoading.value) {
                        Card(
                            shape = RoundedCornerShape(20.dp),
                            elevation = CardDefaults.cardElevation(20.dp),
                            modifier = Modifier.size(250.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.random_dish_card),
                                contentDescription = "",
                            )
                        }
                    } else if (randomDish.value != null) {
                        Card(
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier
                                .padding(5.dp)
                                .combinedClickable(
                                    onClick = {
                                        mainViewModel.dishRepository.getFullDishInfoById(
                                            context,
                                            randomDish.value!!.id,
                                            mainViewModel.selectedRecipe
                                        )
                                        navController.navigate("DetalInfo")
                                    },
                                    onLongClick = {
                                    },

                                    ),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            ),
                            elevation = CardDefaults.cardElevation(defaultElevation = 25.dp),
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                AsyncImage(
                                    model =
                                    randomDish.value!!.imageUrl,
                                    contentDescription = "dish",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.size(250.dp)
                                )
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth(0.4f)
                                        .padding(
                                            start = 5.dp,
                                            end = 5.dp,
                                            top = 10.dp,
                                            bottom = 10.dp
                                        ),
                                    text =
                                    randomDish.value!!.title,
                                    style = TextStyle(
                                        color = Color.Black,
                                        fontSize = 20.sp
                                    ),
                                )
                            }
                        }
                    } else if (isLoading.value) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 100.dp)
                            ,horizontalArrangement = Arrangement.Center
                            ,
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(100.dp),
                                color = Green,
                                strokeWidth = 8.dp
                            )
                        }
                    } else if (isError.value) {
                        Text(text = "ERROR")
                    }
                }
            }
            Spacer(modifier = Modifier.height(100.dp))

        }
    }
}

