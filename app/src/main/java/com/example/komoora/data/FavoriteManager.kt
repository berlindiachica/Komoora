package com.example.komoora.data

import android.content.Context
import com.example.komoora.data.entity.FavoriteEntity

class FavoriteManager(context: Context) {

    private val favoriteDao = AppDatabase
        .getInstance(context.applicationContext)
        .favoriteDao()

    suspend fun isFavorite(key: String): Boolean {
        return favoriteDao.isFavorite(key)
    }

    suspend fun toggleFavorite(
        favorite: FavoriteEntity
    ): Boolean {

        val existingFavorite = favoriteDao.getByKey(
            favorite.destinationKey
        )

        return if (existingFavorite != null) {

            favoriteDao.delete(existingFavorite)
            false

        } else {

            favoriteDao.insert(favorite)

            favoriteDao.isFavorite(
                favorite.destinationKey
            )
        }
    }
}
