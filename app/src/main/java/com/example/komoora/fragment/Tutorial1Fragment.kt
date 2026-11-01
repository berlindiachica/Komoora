package com.example.komoora.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.komoora.R
import com.example.komoora.databinding.FragmentTutorialPageBinding

class Tutorial1Fragment : Fragment() {

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

        binding.imgTutorial.setImageResource(R.drawable.recommend_labuan_bajo)
        binding.tvTutorialTitle.text = "Welcome to Komoora"
        binding.tvTutorialDescription.text =
            "Discover beautiful destinations and explore the amazing tourism of East Nusa Tenggara."
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}