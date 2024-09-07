package com.example.kulinar.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchHistoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert (searchHistoryItem: SearchHistoryItemEntity)

    @Delete
    suspend fun delete (searchHistoryItem: SearchHistoryItemEntity)

    @Update
    suspend fun update (searchHistoryItem: SearchHistoryItemEntity)


    @Query("SELECT * FROM Search")
    fun getAllSearchHistoryItems () :Flow<List<SearchHistoryItemEntity>>
}