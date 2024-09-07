package com.example.kulinar.Favorites.UI


import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.kulinar.MainViewModel
import com.example.kulinar.models.DishUI


@SuppressLint("CoroutineCreationDuringComposition", "UnrememberedMutableState")
@kotlin.OptIn(ExperimentalFoundationApi::class)
@Composable
fun DishFavoriteListItem(
    item: DishUI,
    mainViewModel: MainViewModel,
    context: Context,
    navController: NavController
) {


    Card(
        modifier = Modifier
            .padding(5.dp)
            .combinedClickable(
                onClick = {
                    mainViewModel.selectedRecipe.value = item
                    navController.navigate("DetalInfo")
                },
                onLongClick = {
                },

                ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(5.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 25.dp),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .width(200.dp)
                    .padding(top = 7.dp, bottom = 7.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

            }

            AsyncImage(
                model =
                item.imageUrl,
                contentDescription = "dish",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(200.dp)
            )
            Text(
                modifier = Modifier
                    .width(130.dp)
                    .padding(start = 5.dp, end = 5.dp, top = 10.dp, bottom = 10.dp),
                text =
                item.title,
                style = TextStyle(color = Color.Black, fontSize = 20.sp),

                )

        }
    }
}
