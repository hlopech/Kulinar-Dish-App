package com.example.kulinar.ShopList.UI

import android.content.Context
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.kulinar.MainViewModel
import com.example.kulinar.ShopList.ShopListViewModel
import com.example.kulinar.data.ShopListItemEntity
import com.example.kulinar.ui.theme.Green

//@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopLIstItem(
    item: ShopListItemEntity,
    mainViewModel: MainViewModel,
    context: Context,
    viewModel: ShopListViewModel,
    navController: NavController
) {

    val sheetState = rememberModalBottomSheetState()
    var isSheetOpen = remember {
        mutableStateOf(false)
    }

    Card(
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth()
            .clickable {
                isSheetOpen.value = true
            },
        elevation = CardDefaults.cardElevation(10.dp),

        shape = RoundedCornerShape(20.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = item.imageUrl,
                contentDescription = "",
                modifier = Modifier
            )
            Spacer(modifier = Modifier.width(20.dp))
            Column(verticalArrangement = Arrangement.Top) {

                Text(
                    text = item.title,
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight(30)),
                )

            }
            Spacer(modifier = Modifier.width(20.dp))


        }

    }

    if (isSheetOpen.value) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = {
                isSheetOpen.value = false
            }) {

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = item.title,
                    style = TextStyle(
                        fontSize = 25.sp,
                        fontWeight = FontWeight(30),
                        color = Green
                    ),

                    )
                Spacer(modifier = Modifier.height(70.dp))
                Card(
                    elevation = CardDefaults.cardElevation(20.dp),
                    colors = CardDefaults.cardColors(Green),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Spacer(modifier = Modifier.height(30.dp))
                        Text(
                            text = "Shop list",
                            style = TextStyle(fontSize = 25.sp, fontWeight = FontWeight(900)),
                            color = Color.White,
                        )
                        Spacer(modifier = Modifier.height(30.dp))

                        LazyColumn(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            itemsIndexed(item.ingredients) { index, label ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth(0.9f)
                                        .padding(bottom = 10.dp),
                                    elevation = CardDefaults.cardElevation(20.dp),
                                    colors = CardDefaults.cardColors(Color.White),
                                    shape = RoundedCornerShape(20.dp),
                                ) {

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {

                                        Text(
                                            text = label,
                                            modifier = Modifier.padding(start = 20.dp),
                                        )
                                        IconButton(onClick = {

                                            mainViewModel.deleteIngredient(item, label, isSheetOpen)


                                        }) {

                                            Icon(
                                                imageVector = Icons.Default.Check,
                                                contentDescription = "",
                                                tint = Color.Green
                                            )
                                        }
                                    }
                                }

                            }
                        }

                    }
                }
            }

        }

    }
}

