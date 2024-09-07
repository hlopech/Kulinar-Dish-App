package com.example.kulinar.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ExcludedIngredients")
data class ExcludedIngredientEntity(
    @PrimaryKey
    val ingredient: String,
)