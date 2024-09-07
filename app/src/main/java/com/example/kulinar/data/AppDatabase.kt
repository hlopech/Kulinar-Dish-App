package com.example.kulinar.data


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.kulinar.data.Converts.StringListConverter

@Database(
    entities = [DishEntity::class, SearchHistoryItemEntity::class, ShopListItemEntity::class, ExcludedIngredientEntity::class],
    version = 2
)
@TypeConverters(StringListConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun dishDao(): DishDao
    abstract fun searchHistoryDao(): SearchHistoryDao
    abstract fun shopListItemDao(): ShopListItemDao
    abstract fun excludedIngredientEntity(): ExcludedIngredientsDao

    companion object {

        @Volatile
        private var Instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AppDatabase::class.java, "dish_database")
                    .fallbackToDestructiveMigration().build().also { Instance = it }
            }
        }
    }
}