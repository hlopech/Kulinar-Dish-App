package com.example.kulinar.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "Search")
data class SearchHistoryItemEntity(
    @PrimaryKey
    val search: String,
)