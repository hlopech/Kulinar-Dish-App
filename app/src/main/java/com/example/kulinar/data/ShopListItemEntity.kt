package com.example.kulinar.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ShopListItem")
data class ShopListItemEntity(
    @PrimaryKey
    val title: String,
    val readyInMinutes: Int,
    val imageUrl: String,
    val ingredients: List<String>

)