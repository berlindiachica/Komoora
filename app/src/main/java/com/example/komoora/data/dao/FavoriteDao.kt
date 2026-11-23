package com.example.komoora.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.komoora.data.entity.FavoriteEntity

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favorites ORDER BY savedAt DESC")
    suspend fun getAll(): List<FavoriteEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favorite: FavoriteEntity): Long

    @Delete
    suspend fun delete(favorite: FavoriteEntity)

    @Query(
        "SELECT EXISTS(SELECT 1 FROM favorites WHERE destinationKey = :key)"
    )
    suspend fun isFavorite(key: String): Boolean

    @Query(
        "SELECT * FROM favorites WHERE destinationKey = :key LIMIT 1"
    )
    suspend fun getByKey(key: String): FavoriteEntity?
}
