package com.example.komoora.fragment

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.example.komoora.data.DestinationNavigation
import com.example.komoora.databinding.FragmentExplorationBinding

class ExploreFragment : Fragment() {

    private var _binding: FragmentExplorationBinding? = null
    private val binding get() = _binding!!

    private var selectedCategory = "All"
    private var searchText = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExplorationBinding.inflate(
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


        // Filter kategori
        binding.btnAll.setOnClickListener {
            selectCategory("All")
        }

        binding.btnBeach.setOnClickListener {
            selectCategory("Beach")
        }

        binding.btnIsland.setOnClickListener {
            selectCategory("Island")
        }

        binding.btnVillage.setOnClickListener {
            selectCategory("Village")
        }

        binding.btnHotel.setOnClickListener {
            selectCategory("Hotel")
        }

        // Pencarian destinasi
        binding.etSearchDestination.addTextChangedListener(
            object : TextWatcher {

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) = Unit

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    searchText = s?.toString()?.trim().orEmpty()
                    filterDestinations()
                }

                override fun afterTextChanged(
                    s: Editable?
                ) = Unit
            }
        )

        // Tombol favorit pada kartu XML
        setupFavoriteButton(binding.btnFavoriteKomodo)
        setupFavoriteButton(binding.btnFavoritePinkBeach)
        setupFavoriteButton(binding.btnFavoriteWaterfall)
        setupFavoriteButton(binding.btnFavoriteWaerebo)
        setupFavoriteButton(binding.btnFavoriteLabuanBajo)
        setupFavoriteButton(binding.btnFavoriteCave)

        selectCategory("All")

        // Kartu Komodo Island
        binding.cardKomodoIsland.setOnClickListener {
            DestinationNavigation.openDetail(requireContext(), "komodo_island")
        }

        // Kartu Pink Beach
        binding.cardPinkBeach.setOnClickListener {
            DestinationNavigation.openDetail(requireContext(), "pink_beach")
        }

        // Kartu Oenesu Waterfall
        binding.cardOenesuWaterfall.setOnClickListener {
            DestinationNavigation.openDetail(requireContext(), "oenesu_waterfall")
        }

        // Kartu Waerebo Village
        binding.cardWaereboVillage.setOnClickListener {
            DestinationNavigation.openDetail(requireContext(), "waerebo_village")
        }

        // Kartu Labuan Bajo
        binding.cardLabuanBajo.setOnClickListener {
            DestinationNavigation.openDetail(requireContext(), "labuan_bajo")
        }

        // Kartu Bolok Crystal Cave
        binding.cardBolokCrystalCave.setOnClickListener {
            DestinationNavigation.openDetail(requireContext(), "bolok_crystal_cave")
        }
    }

    private fun selectCategory(category: String) {
        selectedCategory = category

        val buttons = listOf(
            binding.btnAll,
            binding.btnBeach,
            binding.btnIsland,
            binding.btnVillage,
            binding.btnHotel
        )

        val categories = listOf(
            "All",
            "Beach",
            "Island",
            "Village",
            "Hotel"
        )

        buttons.forEachIndexed { index, button ->
            val isSelected = categories[index] == category

            button.backgroundTintList =
                ColorStateList.valueOf(
                    if (isSelected) {
                        Color.rgb(13, 58, 132)
                    } else {
                        Color.rgb(220, 220, 220)
                    }
                )

            button.setTextColor(
                if (isSelected) {
                    Color.WHITE
                } else {
                    Color.rgb(85, 120, 168)
                }
            )
        }

        filterDestinations()
    }

    private fun filterDestinations() {
        val destinations = listOf(
            binding.cardKomodoIsland to
                    ("Komodo Island Manggarai Barat" to "Island"),

            binding.cardPinkBeach to
                    ("Pink Beach Manggarai Barat" to "Beach"),

            binding.cardOenesuWaterfall to
                    ("Oenesu Waterfall Kupang " to "Nature"),

            binding.cardWaereboVillage to
                    ("Waerebo Village Manggarai Barat" to "Village"),

            binding.cardLabuanBajo to
                    ("Waikuri Lagoon Manggarai Barat" to "Nature"),

            binding.cardBolokCrystalCave to
                    ("Bolok Crystal Cave Kupang Barat" to "Nature")
        )

        destinations.forEach { (card, destinationInfo) ->
            val name = destinationInfo.first
            val category = destinationInfo.second

            val matchesCategory =
                selectedCategory == "All" ||
                        category.equals(selectedCategory, ignoreCase = true)

            val matchesSearch =
                name.contains(searchText, ignoreCase = true)

            card.visibility =
                if (matchesCategory && matchesSearch) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
        }
    }

    private fun setupFavoriteButton(button: ImageButton) {
        var isFavorite = false

        button.imageTintList =
            ColorStateList.valueOf(Color.LTGRAY)

        button.setOnClickListener {
            isFavorite = !isFavorite

            button.imageTintList =
                ColorStateList.valueOf(
                    if (isFavorite) Color.RED else Color.LTGRAY
                )

            button.contentDescription =
                if (isFavorite) {
                    "Hapus dari favorit"
                } else {
                    "Tambahkan ke favorit"
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
