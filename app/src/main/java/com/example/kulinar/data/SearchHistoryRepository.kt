package com.example.kulinar.data

import kotlinx.coroutines.flow.Flow

class SearchHistoryRepository(val appDatabase: AppDatabase) {

    suspend fun insertSearchHistoryItem(searchHistoryItemEntity: SearchHistoryItemEntity) {
        return appDatabase.searchHistoryDao().insert(searchHistoryItemEntity)
    }

    suspend fun updateSearchHistoryItem(searchHistoryItemEntity: SearchHistoryItemEntity) =
        appDatabase.searchHistoryDao().update(searchHistoryItemEntity)

    suspend fun deleteSearchHistoryItem(searchHistoryItemEntity: SearchHistoryItemEntity) =
        appDatabase.searchHistoryDao().delete(searchHistoryItemEntity)

    fun getAllSearchHistoryItems(): Flow<List<SearchHistoryItemEntity>> {
        return appDatabase.searchHistoryDao().getAllSearchHistoryItems()
    }
}


