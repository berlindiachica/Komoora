package com.example.komoora.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.komoora.adapter.TutorialFragmentAdapter
import com.example.komoora.databinding.ActivityTutorialBinding
import com.example.komoora.fragment.Tutorial1Fragment
import com.example.komoora.fragment.Tutorial2Fragment
import com.example.komoora.fragment.Tutorial3Fragment

class TutorialActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTutorialBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTutorialBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragmentsList = listOf(
            Tutorial1Fragment(),
            Tutorial2Fragment(),
            Tutorial3Fragment()
        )

        val adapter = TutorialFragmentAdapter(this, fragmentsList)

        binding.tutorialViewPager.adapter = adapter

        binding.dotIndicator.attachTo(binding.tutorialViewPager)
    }
}