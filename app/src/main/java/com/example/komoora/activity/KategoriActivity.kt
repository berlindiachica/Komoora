package com.example.komoora.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.komoora.R
import com.example.komoora.adapter.KategoriTabsAdapter
import com.example.komoora.databinding.ActivityKategoriBinding
import com.google.android.material.tabs.TabLayoutMediator
class KategoriActivity : AppCompatActivity() {

    private lateinit var binding: ActivityKategoriBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityKategoriBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mengatur jarak dari status bar dan navigation bar
        ViewCompat.setOnApplyWindowInsetsListener(
            binding.root
        ) { view, insets ->

            val systemBars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars()
            )

            view.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )

            insets
        }

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Kategori Wisata"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val tabsAdapter = KategoriTabsAdapter(this)

        binding.viewPager.adapter = tabsAdapter


        TabLayoutMediator(
            binding.tabLayout,
            binding.viewPager
        ) { tab, position ->

            when (position) {
                0 -> tab.text = "Nature"
                1 -> tab.text = "Hotel"
                2 -> tab.text = "Culinary"
                3 -> tab.text = "Culture"
                4 -> tab.text = "Souvenir"
            }

        }.attach()


        // Membuka tab sesuai tombol yang diklik dari Home
        val selectedTab = intent.getIntExtra(
            "selected_tab",
            0
        ).coerceIn(0, 4)

        binding.viewPager.setCurrentItem(
            selectedTab,
            false
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}