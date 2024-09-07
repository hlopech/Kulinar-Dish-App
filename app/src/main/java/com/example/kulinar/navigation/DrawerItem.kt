package com.example.kulinar.navigation

import com.example.kulinar.R

sealed class DrawerItem(val title: String, val iconId: Int, val route: String) {
    data object Setting : BottomItem("Setting", R.drawable.black_list_icon, "Setting")
    data object ShopList : BottomItem("ShopList", R.drawable.shop_list_drawer_icon, "ShopList")
}