package com.example.komoora.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.komoora.R
import com.example.komoora.data.TourPackage

class TourPackageAdapter(
    context: Context,
    private val tourPackages: List<TourPackage>
) : ArrayAdapter<TourPackage>(
    context,
    0,
    tourPackages
) {

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {

        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_tour_package, parent, false)

        val tour = tourPackages[position]

        val image = view.findViewById<ImageView>(R.id.ivTourImage)
        val name = view.findViewById<TextView>(R.id.tvTourName)
        val location = view.findViewById<TextView>(R.id.tvTourLocation)
        val rating = view.findViewById<TextView>(R.id.tvTourRating)

        image.setImageResource(tour.imageResId)
        name.text = tour.name
        location.text = tour.location
        rating.text = "★ ${tour.rating}"

        return view
    }
}