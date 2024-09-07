package com.example.kulinar.Search.UI

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kulinar.MainViewModel
import com.example.kulinar.Search.SearchViewModel
import com.example.kulinar.models.CategoryItemModel
import com.example.kulinar.ui.theme.CategoryItemText

@Composable
fun CategoryItem(
    item: CategoryItemModel,
    viewModel: SearchViewModel,
    context: Context,
    mainViewModel: MainViewModel
) {

    val excludedIngredients =
        mainViewModel.excludedIngredients.collectAsState(initial = emptyList())

    var excludedIngredientsString = ""

    excludedIngredients.value.forEach { it -> excludedIngredientsString += it.ingredient.toString()+"," }


    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        modifier = Modifier
            .padding(5.dp)
            .clickable {
                viewModel.showCategory.value = false
                viewModel.bgImage.value = item.imageUrl
                viewModel.isLoading.value = true
                viewModel.dishType.value = item.dishType

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
            }
    ) {
        Box(
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = painterResource(id = item.imageUrl),
                contentDescription = "",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.height(120.dp)
            )
            Text(
                text = item.title,
                style = TextStyle(
                    fontSize = 25.sp,
                    color = CategoryItemText,
                    fontWeight = FontWeight.Bold,
                )
            )
        }
    }
}
