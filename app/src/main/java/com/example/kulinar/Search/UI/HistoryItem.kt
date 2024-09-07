package com.example.kulinar.Search.UI

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kulinar.MainViewModel
import com.example.kulinar.R
import com.example.kulinar.Search.SearchViewModel
import com.example.kulinar.data.SearchHistoryItemEntity


@Composable
fun HistoryItem(
    mainViewModel: MainViewModel,
    item: SearchHistoryItemEntity,
    viewModel: SearchViewModel,
    isSearchBarFocused: MutableState<Boolean>,
    context: Context,
) {

    val excludedIngredients =
        mainViewModel.excludedIngredients.collectAsState(initial = emptyList())

    var excludedIngredientsString = ""

    excludedIngredients.value.forEach { it -> excludedIngredientsString += it.ingredient.toString() + "," }


    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(modifier = Modifier
            .fillMaxWidth(0.9f)
            .clickable {
                viewModel.searchText.value = item.search
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
                viewModel.showCategory.value = false

                isSearchBarFocused.value = false

            }) {
            Icon(
                painter = painterResource(id = R.drawable.history_item_icon),
                contentDescription = ""
            )
            Text(
                text = item.search,
                modifier = Modifier.padding(start = 15.dp),
                style = TextStyle(fontSize = 20.sp)
            )

        }
        IconButton(onClick = {
            mainViewModel.deleteSearchHistoryItem(item)
            viewModel.searchText.value += " "
            viewModel.searchText.value.trim()

        }) {
            Icon(
                painter = painterResource(id = R.drawable.history_item_delete_icon),
                contentDescription = "",
                tint = Color.Red
            )
        }
    }
}