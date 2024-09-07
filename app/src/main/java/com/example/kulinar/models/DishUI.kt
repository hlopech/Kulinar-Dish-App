package com.example.kulinar.models

data class DishUI(
    val id: Int,
    val title: String,
    val readyInMinutes: Int,
    val imageUrl: String,
    val summary : String,
    val instructions:String,
    val dishType:List<String>,
    val ingredients: List<String>

) {

}
