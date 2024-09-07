import android.annotation.SuppressLint
import android.text.Html
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Menu
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.kulinar.MainViewModel
import com.example.kulinar.R
import com.example.kulinar.models.DishUI
import com.example.kulinar.ui.theme.FavorietesTopAppBarBg
import com.example.kulinar.ui.theme.Green
import com.example.kulinar.ui.theme.GreenLight
import com.example.kulinar.ui.theme.OrangeItem
import com.example.kulinar.ui.theme.RedItem
import com.example.kulinar.ui.theme.YellowItem
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun DetailedDishInfoScreen(
    drawerState: DrawerState,
    mainViewModel: MainViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val dish: MutableState<DishUI?> = remember {
        mutableStateOf(null)
    }

    val isFav: MutableState<Boolean?> = remember {
        mutableStateOf(null)
    }

    coroutineScope.launch {
        dish.value = mainViewModel.selectedRecipe.value
    }
    coroutineScope.launch {
        isFav.value = dish.value?.title?.let { mainViewModel.isDishFavorite(it) }

    }


    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()



    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
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
                    IconButton(onClick = {
                        if (dish.value != null) {
                            mainViewModel.addFavoriteDish(dish.value!!)

                            if (isFav.value == true) {
                                Toast.makeText(
                                    context,
                                    "Dish deleted from  favorite",
                                    Toast.LENGTH_SHORT
                                ).show()

                            } else if (isFav.value == false) {
                                Toast.makeText(
                                    context,
                                    "Dish add in  favorite",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        } else {
                            Toast.makeText(context, "Waiting loading", Toast.LENGTH_SHORT)
                                .show()
                        }
                        if (isFav.value != null) {
                            isFav.value = !isFav.value!!
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.FavoriteBorder, contentDescription = "",
                            modifier = Modifier.size(35.dp),
                            tint = Color.White
                        )
                    }
                    IconButton(onClick = {
                        if (dish.value != null) {
                            mainViewModel.addDishToShopList(dish.value!!)
                            Toast.makeText(context, "Dish added in shop list", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            Toast.makeText(context, "Waiting loading", Toast.LENGTH_SHORT)
                                .show()
                        }


                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.shop_list_drawer_icon),
                            contentDescription = "",
                            modifier = Modifier.size(35.dp),
                            tint = Color.White

                        )
                    }

                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = FavorietesTopAppBarBg,
                    titleContentColor = Color.White
                ),
                scrollBehavior = scrollBehavior,

                )
        },
    ) {
        Image(
            painter = painterResource(id = R.drawable.search_default_bg),
            contentDescription = "",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillHeight
        )
        Column(

            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {

            Spacer(modifier = Modifier.height(70.dp))


            Row(
                modifier = Modifier
                    .padding(start = 20.dp, top = 30.dp)
                    .clickable {
                        mainViewModel.selectedRecipe.value = null
                        navController.popBackStack()
                    }, verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "",
                    modifier = Modifier.size(30.dp)
                )
                Text(text = "Back", fontSize = 20.sp)
            }


            Spacer(modifier = Modifier.height(20.dp))
            if (dish.value != null) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,

                    ) {

                    Text(
                        text = dish.value?.title!!,
                        style = TextStyle(fontSize = 35.sp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp),

                        )

                    Spacer(modifier = Modifier.height(40.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp),
                        horizontalArrangement = Arrangement.End
                    ) {

                        Row {
                            Text(
                                text = "${dish.value?.readyInMinutes!!} min.",
                                fontSize = 26.sp,
                                fontWeight = FontWeight(900),
                                color = when {
                                    dish.value?.readyInMinutes!! > 60 -> RedItem
                                    dish.value?.readyInMinutes!! <= 30 -> GreenLight
                                    else -> YellowItem
                                }
                            )

                            Icon(
                                painter = painterResource(id = R.drawable.cooking_time_icon),
                                contentDescription = "time",
                                modifier = Modifier.size(35.dp),
                                tint = when {
                                    dish.value?.readyInMinutes!! > 60 -> RedItem
                                    dish.value?.readyInMinutes!! <= 30 -> GreenLight
                                    else -> YellowItem
                                }
                            )

                        }


                    }
                    Spacer(modifier = Modifier.height(10.dp))

                    AsyncImage(
                        model = dish.value?.imageUrl!!, contentDescription = "",
                        modifier = Modifier.size(400.dp),
                        contentScale = ContentScale.FillHeight

                    )

                    Spacer(modifier = Modifier.height(20.dp))
                    Column(
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier
                            .padding(start = 20.dp, end = 20.dp)
                            .fillMaxSize(),
                    ) {

                        FlowRow() {

                            dish.value?.dishType!!.forEach { item ->
                                Card(
                                    colors = CardDefaults.cardColors(OrangeItem),

                                    elevation = CardDefaults.cardElevation(defaultElevation = 20.dp),
                                    modifier = Modifier.padding(2.dp)
                                ) {
                                    Text(
                                        text = item,
                                        fontSize = 20.sp,
                                        color = Color.White,
                                        fontWeight = FontWeight(900),
                                        modifier = Modifier.padding(5.dp)

                                    )
                                }

                            }
                        }


                        Spacer(modifier = Modifier.height(20.dp))



                        Text(
                            text = "Summary",
                            style = TextStyle(fontSize = 25.sp, fontWeight = FontWeight(900)),
                            color = Green
                        )


                        Text(
                            text = Html.fromHtml(dish.value?.summary!!, Html.FROM_HTML_MODE_LEGACY)
                                .toString(),

                            )

                        Spacer(modifier = Modifier.height(20.dp))



                        Text(
                            text = "Ingredients: ",
                            style = TextStyle(fontSize = 25.sp, fontWeight = FontWeight(900)),
                            color = Green,
                            modifier = Modifier
                        )


                        dish.value?.ingredients!!.forEach { item ->
                            Text(
                                text = item,
                                fontSize = 15.sp,
                                fontWeight = FontWeight(900),
                                modifier = Modifier.padding(start = 5.dp)
                            )

                        }

                        Spacer(modifier = Modifier.height(20.dp))


                        Text(
                            text = "Instructions",
                            style = TextStyle(fontSize = 25.sp, fontWeight = FontWeight(900)),
                            color = Green
                        )
                        Text(
                            text = Html.fromHtml(
                                dish.value?.instructions!!,
                                Html.FROM_HTML_MODE_LEGACY
                            )
                                .toString(),
                        )

                    }
                }

            } else if (dish.value == null) {
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
            }
            Spacer(modifier = Modifier.height(120.dp))

        }
    }
}
