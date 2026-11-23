package com.example.komoora.data.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "favorites",
    indices = [Index(value = ["destinationKey"], unique = true)]
)
data class FavoriteEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val destinationKey: String,

    val destinationName: String,

    val location: String,

    val category: String,

    val imageName: String,

    val savedAt: Long = System.currentTimeMillis()
)
