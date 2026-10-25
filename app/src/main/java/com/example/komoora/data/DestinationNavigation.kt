package com.example.komoora.data

import android.content.Context
import android.content.Intent
import com.example.komoora.activity.DetailActivity

object DestinationNavigation {

    fun openDetail(context: Context, destinationId: String) {
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra("destination_id", destinationId)
        context.startActivity(intent)
    }
}