package com.example.kulinar.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ShopListItemDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(shopListItemEntity: ShopListItemEntity)

    @Delete
    suspend fun delete(shopListItemEntity: ShopListItemEntity)

    @Update
    suspend fun update(shopListItemEntity: ShopListItemEntity)

    @Query("SELECT * FROM ShopListItem")
    fun getShopList(): Flow<List<ShopListItemEntity>>

}