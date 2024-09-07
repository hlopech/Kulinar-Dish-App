package com.example.kulinar.data

import android.content.Context
import androidx.compose.runtime.MutableState
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.kulinar.API_KEY
import com.example.kulinar.models.IngredientUI
import kotlinx.coroutines.flow.Flow
import org.json.JSONArray
import java.lang.Exception


class ExcludedIngredientsRepository(val appDatabase: AppDatabase) {

    suspend fun insertExcludedIngredient(excludedIngredientEntity: ExcludedIngredientEntity) {
        return appDatabase.excludedIngredientEntity().insert(excludedIngredientEntity)
    }

    suspend fun updateExcludedIngredient(excludedIngredientEntity: ExcludedIngredientEntity) =
        appDatabase.excludedIngredientEntity().update(excludedIngredientEntity)

    suspend fun deleteExcludedIngredient(excludedIngredientEntity: ExcludedIngredientEntity) =
        appDatabase.excludedIngredientEntity().delete(excludedIngredientEntity)

    fun getExcludedIngredients(): Flow<List<ExcludedIngredientEntity>> {
        return appDatabase.excludedIngredientEntity().getExcludedIngredients()
    }


    fun searchIngredients(
        query: String,
        context: Context,
        ingredientsList: MutableState<MutableList<IngredientUI>>,
        isLoading: MutableState<Boolean>,

        ) {

        val url = buildString {
            append("https://api.spoonacular.com/food/ingredients/autocomplete?query=${query}&number=10&metaInformation=false&apiKey=$API_KEY")
        }
        val queue = Volley.newRequestQueue(context)

        val sRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                try {

                    val ingredientsJSONArray = JSONArray(response)

                    for (i in 0 until ingredientsJSONArray.length()) {
                        ingredientsList.value.add(
                            IngredientUI(
                                ingredientsJSONArray.getJSONObject(
                                    i
                                ).getString("name"),
                                false
                            )
                        )
                    }
                    isLoading.value = false

                } catch (e: Exception) {
                }
            }, { error ->
            })
        queue.add(sRequest)


    }


}


