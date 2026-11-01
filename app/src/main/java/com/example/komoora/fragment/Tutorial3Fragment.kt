package com.example.komoora.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.komoora.R
import com.example.komoora.databinding.FragmentTutorialPageBinding

class Tutorial3Fragment : Fragment() {

    private var _binding: FragmentTutorialPageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTutorialPageBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imgTutorial.setImageResource(R.drawable.recommend_waterfall)
        binding.tvTutorialTitle.text = "Plan Your Journey"
        binding.tvTutorialDescription.text =
            "Save your favorite destinations and use the booking feature to plan your next adventure."
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}