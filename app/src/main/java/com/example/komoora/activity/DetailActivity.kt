package com.example.komoora.activity

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.komoora.data.DestinationData
import com.example.komoora.databinding.ActivityDetailPageBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailPageBinding
    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val destinationId = intent.getStringExtra("destination_id")
        val destination = DestinationData.findById(destinationId)
            ?: DestinationData.destinations.first()

        // Isi detail sesuai destinasi yang dipilih
        binding.imgDetailBackground.setImageResource(destination.imageResId)
        binding.tvDestinationName.text = destination.name
        binding.tvPrice.text = destination.price
        binding.tvLocation.text = destination.location
        binding.tvRating.text = destination.rating
        binding.tvDescription.text = destination.description

        // Tombol kembali
        binding.tvBack.setOnClickListener {
            finish()
        }

        // Tombol favorit
        binding.tvFavorite.setOnClickListener {
            isFavorite = !isFavorite

            binding.tvFavorite.imageTintList =
                ColorStateList.valueOf(
                    if (isFavorite) Color.RED else Color.GRAY
                )

            binding.tvFavorite.contentDescription =
                if (isFavorite) {
                    "Hapus dari favorit"
                } else {
                    "Tambahkan ke favorit"
                }
        }
    }
}