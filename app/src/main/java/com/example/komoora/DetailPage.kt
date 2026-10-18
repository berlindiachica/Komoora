package com.example.komoora

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.komoora.databinding.ActivityDetailPageBinding

class DetailPage : AppCompatActivity() {
    private lateinit var binding: ActivityDetailPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvBack.setOnClickListener {
            finish()
        }
    }
}