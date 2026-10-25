package com.example.komoora

import android.content.Context
import android.content.Intent

object DestinationNavigation {

    fun openDetail(context: Context, destinationId: String) {
        val intent = Intent(context, DetailPage::class.java)
        intent.putExtra("destination_id", destinationId)
        context.startActivity(intent)
    }
}