package com.example.komoora

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.komoora.databinding.FragmentBookingBinding

class BookingFragment : Fragment() {

    private var _binding: FragmentBookingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookingBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Simple List Item
        val destinations = listOf(
            "Komodo Island",
            "Pink Beach",
            "Oenesu Waterfall",
            "Waerebo Village",
            "Waikuri Lagoon"
        )

        val simpleAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            destinations
        )

        binding.listSimpleDestinations.adapter = simpleAdapter

        binding.listSimpleDestinations.setOnItemClickListener {
                _, _, position, _ ->

            val destination = DestinationData.findByName(
                destinations[position]
            )

            destination?.let {
                DestinationNavigation.openDetail(
                    requireContext(),
                    it.id
                )
            }
        }

        // 2. Custom Adapter
        val tourPackages = listOf(
            TourPackage(
                id = "komodo_island",
                name = "Komodo Island",
                location = "Manggarai Barat",
                rating = "4.8",
                imageResId = R.drawable.recommend_komodo
            ),
            TourPackage(
                id = "pink_beach",
                name = "Pink Beach",
                location = "Manggarai Barat",
                rating = "4.7",
                imageResId = R.drawable.pink_beach
            ),
            TourPackage(
                id = "oenesu_waterfall",
                name = "Oenesu Waterfall",
                location = "Kupang",
                rating = "4.6",
                imageResId = R.drawable.recommend_waterfall
            ),
            TourPackage(
                id = "waerebo_village",
                name = "Waerebo Village",
                location = "Manggarai",
                rating = "4.8",
                imageResId = R.drawable.recommend_waerebo
            ),
            TourPackage(
                id = "labuan_bajo",
                name = "Labuan Bajo",
                location = "Manggarai Barat",
                rating = "4.7",
                imageResId = R.drawable.recommend_labuan_bajo
            )
        )

        val customAdapter = TourPackageAdapter(
            requireContext(),
            tourPackages
        )

        binding.listTourPackages.adapter = customAdapter

        binding.listTourPackages.setOnItemClickListener {
                _, _, position, _ ->

            val destination = tourPackages[position]

            DestinationNavigation.openDetail(
                requireContext(),
                destination.id
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}