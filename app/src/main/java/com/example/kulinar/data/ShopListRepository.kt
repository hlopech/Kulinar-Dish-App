package com.example.kulinar.data

import kotlinx.coroutines.flow.Flow

class ShopListRepository(val appDatabase: AppDatabase) {

    suspend fun insertShopListItem(shopListItemEntity: ShopListItemEntity) {
        return appDatabase.shopListItemDao().insert(shopListItemEntity)
    }

    suspend fun updateShopListItem(shopListItemEntity: ShopListItemEntity) =
        appDatabase.shopListItemDao().update(shopListItemEntity)

    suspend fun deleteShopListItem(shopListItemEntity: ShopListItemEntity) =
        appDatabase.shopListItemDao().delete(shopListItemEntity)

    fun getShopList(): Flow<List<ShopListItemEntity>> {
        return appDatabase.shopListItemDao().getShopList()
    }
}

