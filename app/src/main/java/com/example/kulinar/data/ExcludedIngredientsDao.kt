package com.example.kulinar.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ExcludedIngredientsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(excludedIngredientEntity: ExcludedIngredientEntity)

    @Delete
    suspend fun delete(excludedIngredientEntity: ExcludedIngredientEntity)

    @Update
    suspend fun update(excludedIngredientEntity: ExcludedIngredientEntity)


    @Query("SELECT * FROM ExcludedIngredients")
    fun getExcludedIngredients(): Flow<List<ExcludedIngredientEntity>>
}