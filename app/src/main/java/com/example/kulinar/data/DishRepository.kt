package com.example.kulinar.data

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.kulinar.API_KEY
import com.example.kulinar.data.mapper.toEntity
import com.example.kulinar.data.mapper.toUI
import com.example.kulinar.models.DishPreviewUI
import com.example.kulinar.models.DishUI
import kotlinx.coroutines.flow.Flow
import org.json.JSONObject
import java.lang.Exception



class DishRepository(val appDatabase: AppDatabase) {

    suspend fun insertDish(dishUI: DishUI) {
        return appDatabase.dishDao().insert(dishUI.toEntity())
    }


    suspend fun deleteDish(dishUI: DishUI) = appDatabase.dishDao().delete(dishUI.toEntity())

    fun getAllFavoritesDishes(): Flow<List<DishEntity>> {
        return appDatabase.dishDao().getAllFavorites()
    }

    suspend fun getDishByTitle(title: String): DishUI? {
        return appDatabase.dishDao().getDishByTitle(title)?.toUI()
    }

    suspend fun isDishExists(title: String): Boolean {
        return appDatabase.dishDao().isDishExists(title)
    }

    fun getRandomDish(
        context: Context,
        randomDish: MutableState<DishUI?>,
        isLoading: MutableState<Boolean>,
        isError: MutableState<Boolean>,
    ) {
        isLoading.value = true
        val url = buildString {
            append("https://api.spoonacular.com/recipes/random?number=1&apiKey=$API_KEY")
        }
        val queue = Volley.newRequestQueue(context)


        val sRequest = StringRequest(
            Request.Method.GET, url,
            { response ->

                try {

                    val dishJSONObject =
                        JSONObject(response).getJSONArray("recipes").getJSONObject(0)

                    val ingredientsJSONArray = dishJSONObject.getJSONArray("extendedIngredients")

                    var ingredients = ArrayList<String>()

                    for (i in 0 until ingredientsJSONArray.length()) {
                        ingredients.add(ingredientsJSONArray.getJSONObject(i).getString("original"))
                    }

                    var dishTypes = ArrayList<String>()

                    for (i in 0 until dishJSONObject.getJSONArray("dishTypes").length()) {
                        dishTypes.add(dishJSONObject.getJSONArray("dishTypes")[i].toString())
                    }

                    randomDish.value = DishUI(
                        dishJSONObject.getInt("id"),
                        dishJSONObject.getString("title"),
                        dishJSONObject.getInt("readyInMinutes"),
                        dishJSONObject.getString("image"),
                        dishJSONObject.getString("summary"),
                        dishJSONObject.getString("instructions"),
                        dishTypes,
                        ingredients
                    )


                } catch (e: Exception) {
                    Log.e("MyLog", "Error parsing JSON: ${e.message}")
                    isLoading.value = false
                    isError.value = true
                }
            }, { error ->
                Log.d("MyError", error.toString())
            })
        queue.add(sRequest)


    }

    fun getFullDishInfoById(
        context: Context, id: Int, dish: MutableState<DishUI?>,
    ) {
        val url = buildString {
            append("https://api.spoonacular.com/recipes/$id/information?includeNutrition=false&addTasteData=false&addWinePairing=false&apiKey=$API_KEY")
        }
        val queue = Volley.newRequestQueue(context)


        val sRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                try {

                    val dishJSONObject = JSONObject(response)
                    val ingredientsJSONArray = dishJSONObject.getJSONArray("extendedIngredients")
                    var ingredients = ArrayList<String>()

                    for (i in 0 until ingredientsJSONArray.length()) {
                        ingredients.add(ingredientsJSONArray.getJSONObject(i).getString("original"))
                    }

                    var dishTypes = ArrayList<String>()

                    for (i in 0 until dishJSONObject.getJSONArray("dishTypes").length()) {
                        dishTypes.add(dishJSONObject.getJSONArray("dishTypes")[i].toString())
                    }

                    dish.value = DishUI(
                        dishJSONObject.getInt("id"),
                        dishJSONObject.getString("title"),
                        dishJSONObject.getInt("readyInMinutes"),
                        dishJSONObject.getString("image"),
                        dishJSONObject.getString("summary"),
                        dishJSONObject.getString("instructions"),
                        dishTypes,
                        ingredients
                    )

                } catch (e: Exception) {
                    Log.e("MyLog", "Error parsing JSON: ${e.message}")
                }
            }, { error ->
                Log.d("MyError", error.toString())
            })
        queue.add(sRequest)

    }

    fun searchDish(
        context: Context,
        searchText: MutableState<String>,
        dishType: MutableState<String>,
        isLoading: MutableState<Boolean>,
        isError: MutableState<Boolean>,
        dishes: MutableState<List<DishPreviewUI>>,
        isNoResult: MutableState<Boolean?>,
        excludedIngredients: String,
    ) {

        val url = buildString {

            append("https://api.spoonacular.com/recipes/complexSearch?apiKey=$API_KEY&number=30&excludeIngredients=$excludedIngredients")
            if (dishType.value.isNotEmpty()) {
                append("&type=${dishType.value}")
            }
            if (searchText.value.isNotEmpty()) {
                append("&query=${searchText.value}")
            }
        }
        val queue = Volley.newRequestQueue(context)
        val res = ArrayList<DishPreviewUI>()

        val sRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                isLoading.value = false
                isError.value = false

                try {
                    val dishesJsonArray = JSONObject(response).getJSONArray("results")
                    if (dishesJsonArray.length() == 0) {
                        isNoResult.value = true
                    } else {
                        isNoResult.value = false

                    }
                    for (i in 0 until dishesJsonArray.length()) {
                        val recipeJsonObject =
                            dishesJsonArray.getJSONObject(i)

                        res.add(
                            DishPreviewUI(
                                recipeJsonObject.getInt("id"),
                                recipeJsonObject.getString("title"),
                                recipeJsonObject.getString("image"),
                            )
                        )
                    }
                } catch (e: Exception) {
                    Log.e("MyLog", "Error parsing JSON: ${e.message}")
                }

                dishes.value = res

            }, { error ->
                Log.d("MyError", error.toString())
                isLoading.value = false
                isError.value = true


            })
        queue.add(sRequest)

    }

    suspend fun getDishById(id: Int): DishUI? {
        return appDatabase.dishDao().getDishById(id)?.toUI()
    }

    suspend fun updateDish(dishUI: DishUI) = appDatabase.dishDao().update(dishUI.toEntity())


}