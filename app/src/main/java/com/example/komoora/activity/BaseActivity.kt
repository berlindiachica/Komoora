package com.example.komoora.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.komoora.fragment.BookingFragment
import com.example.komoora.fragment.ExploreFragment
import com.example.komoora.fragment.FavoritesFragment
import com.example.komoora.fragment.HomeFragment
import com.example.komoora.fragment.ProfileFragment
import com.example.komoora.R
import com.example.komoora.databinding.ActivityBaseBinding

class BaseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            replaceFragment(HomeFragment())
        }

        binding.bottomNavView.setOnItemSelectedListener { item ->
            val fragment = when (item.itemId) {
                R.id.nav_home -> HomeFragment()
                R.id.nav_explore -> ExploreFragment()
                R.id.nav_booking -> BookingFragment()
                R.id.nav_favorites -> FavoritesFragment()
                R.id.nav_profile -> ProfileFragment()
                else -> null
            }

            if (fragment != null) {
                replaceFragment(fragment)
                true
            } else {
                false
            }
        }

        binding.bottomNavView.selectedItemId = R.id.nav_home
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}