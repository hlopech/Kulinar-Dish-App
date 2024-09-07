package com.example.kulinar.data.mapper

import com.example.kulinar.data.DishEntity
import com.example.kulinar.data.ShopListItemEntity
import com.example.kulinar.models.DishUI

fun DishEntity.toUI(): DishUI {
    return DishUI(
        id = id,
        title = title,
        readyInMinutes = readyInMinutes,
        imageUrl = imageUrl,
        summary = summary,
        instructions = instructions,
        dishType = dishType,
        ingredients = ingredients
    )
}

fun DishUI.toEntity(): DishEntity {
    return DishEntity(
        id = id,
        title = title,
        readyInMinutes = readyInMinutes,
        imageUrl = imageUrl,
        summary = summary,
        instructions = instructions,
        dishType = dishType,
        ingredients = ingredients
    )
}

fun DishUI.toShopListItem(): ShopListItemEntity {
    return ShopListItemEntity(
        title = title,
        readyInMinutes = readyInMinutes,
        imageUrl = imageUrl,
        ingredients = ingredients
    )
}