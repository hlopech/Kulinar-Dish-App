package com.example.kulinar.ShopList

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.kulinar.data.ShopListItemEntity

class ShopListViewModel:ViewModel() {
    val selectedShopList: MutableState<ShopListItemEntity?> = mutableStateOf(null)
}