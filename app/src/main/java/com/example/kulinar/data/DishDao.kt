package com.example.kulinar.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
 
@Dao
interface DishDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(dishEntity: DishEntity)

    @Delete
    suspend fun delete(dishEntity: DishEntity)

    @Update
    suspend fun update(dishEntity: DishEntity)

    @Query("SELECT * FROM Dish")
    fun getAllFavorites(): Flow<List<DishEntity>>

    @Query("SELECT * FROM Dish WHERE title = :title")
    suspend fun getDishByTitle(title: String): DishEntity?

    @Query("SELECT * FROM Dish WHERE id = :id")
    suspend fun getDishById(id: Int): DishEntity?

    @Query("SELECT EXISTS(SELECT 1 FROM Dish WHERE title = :title)")
    suspend fun isDishExists(title: String): Boolean

}