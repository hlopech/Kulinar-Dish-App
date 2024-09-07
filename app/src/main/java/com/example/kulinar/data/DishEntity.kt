package com.example.kulinar.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Dish")
data class DishEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val readyInMinutes: Int,
    val imageUrl: String,
    val summary: String,
    val instructions: String,
    val dishType: List<String>,
    val ingredients: List<String>
)