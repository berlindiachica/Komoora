
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
import com.google.android.material.chip.Chip

class ExploreFragment : Fragment() {

    private var _binding: FragmentExplorationBinding? = null
    private val binding get() = _binding!!

    // Menyimpan kategori dan teks pencarian yang dipilih
    private var selectedCategory = "All"
    private var searchText = ""

    // Daftar Chip kategori
    private lateinit var categoryChips: List<Chip>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Menggunakan ViewBinding
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

        // 1. Inisialisasi ChipGroup
        categoryChips = listOf(
            binding.chipAll,
            binding.chipBeach,
            binding.chipIsland,
            binding.chipNature,
            binding.chipVillage
        )

        // 2. Mengatur warna Chip
        updateChipColors()

        // 3. Mengatur pilihan kategori menggunakan ChipGroup
        binding.chipGroupFilter.setOnCheckedStateChangeListener { group, checkedIds ->

            val selectedChipId = checkedIds.firstOrNull()

            if (selectedChipId != null) {

                val selectedChip = group.findViewById<Chip>(
                    selectedChipId
                )

                selectedCategory = selectedChip.text.toString()

                updateChipColors()

                filterDestinations()
            }
        }

        // 4. Fitur pencarian destinasi
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

        // 5. Tombol favorit pada setiap kartu
        setupFavoriteButton(binding.btnFavoriteKomodo)
        setupFavoriteButton(binding.btnFavoritePinkBeach)
        setupFavoriteButton(binding.btnFavoriteWaterfall)
        setupFavoriteButton(binding.btnFavoriteWaerebo)
        setupFavoriteButton(binding.btnFavoriteLabuanBajo)
        setupFavoriteButton(binding.btnFavoriteCave)

        // 6. Menampilkan semua destinasi saat pertama dibuka
        filterDestinations()

        // 7. Navigasi ke DetailPage masing-masing destinasi

        // Komodo Island
        binding.cardKomodoIsland.setOnClickListener {
            DestinationNavigation.openDetail(
                requireContext(),
                "komodo_island"
            )
        }

        // Pink Beach
        binding.cardPinkBeach.setOnClickListener {
            DestinationNavigation.openDetail(
                requireContext(),
                "pink_beach"
            )
        }

        // Oenesu Waterfall
        binding.cardOenesuWaterfall.setOnClickListener {
            DestinationNavigation.openDetail(
                requireContext(),
                "oenesu_waterfall"
            )
        }

        // Waerebo Village
        binding.cardWaereboVillage.setOnClickListener {
            DestinationNavigation.openDetail(
                requireContext(),
                "waerebo_village"
            )
        }

        // Labuan Bajo
        binding.cardLabuanBajo.setOnClickListener {
            DestinationNavigation.openDetail(
                requireContext(),
                "labuan_bajo"
            )
        }

        // Bolok Crystal Cave
        binding.cardBolokCrystalCave.setOnClickListener {
            DestinationNavigation.openDetail(
                requireContext(),
                "bolok_crystal_cave"
            )
        }
    }

    // 8. Mengatur warna Chip berdasarkan pilihan
    private fun updateChipColors() {

        categoryChips.forEach { chip ->

            val isSelected = chip.text.toString() == selectedCategory

            chip.chipBackgroundColor = ColorStateList.valueOf(
                if (isSelected) {
                    Color.rgb(13, 58, 132)
                } else {
                    Color.rgb(220, 220, 220)
                }
            )

            chip.setTextColor(
                if (isSelected) {
                    Color.WHITE
                } else {
                    Color.rgb(85, 120, 168)
                }
            )
        }
    }

    // 9. Filter destinasi berdasarkan kategori dan pencarian
    private fun filterDestinations() {

        val destinations = listOf(

            binding.cardKomodoIsland to
                    ("Komodo Island Manggarai Barat" to "Island"),

            binding.cardPinkBeach to
                    ("Pink Beach Manggarai Barat" to "Beach"),

            binding.cardOenesuWaterfall to
                    ("Oenesu Waterfall Kupang" to "Nature"),

            binding.cardWaereboVillage to
                    ("Waerebo Village Manggarai Barat" to "Village"),

            binding.cardLabuanBajo to
                    ("Labuan Bajo Lagoon Manggarai Barat" to "Nature"),

            binding.cardBolokCrystalCave to
                    ("Bolok Crystal Cave Kupang Barat" to "Nature")
        )

        destinations.forEach { (card, destinationInfo) ->

            val name = destinationInfo.first
            val category = destinationInfo.second

            // Memeriksa kategori yang dipilih
            val matchesCategory =
                selectedCategory == "All" ||
                        category.equals(selectedCategory, ignoreCase = true)

            // Memeriksa teks pencarian
            val matchesSearch =
                name.contains(searchText, ignoreCase = true)

            // Menampilkan atau menyembunyikan kartu
            card.visibility =
                if (matchesCategory && matchesSearch) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
        }
    }

    // 10. Mengatur tombol favorit
    private fun setupFavoriteButton(button: ImageButton) {

        var isFavorite = false

        button.imageTintList =
            ColorStateList.valueOf(Color.LTGRAY)

        button.setOnClickListener {

            isFavorite = !isFavorite

            button.imageTintList =
                ColorStateList.valueOf(
                    if (isFavorite) {
                        Color.RED
                    } else {
                        Color.LTGRAY
                    }
                )

            button.contentDescription =
                if (isFavorite) {
                    "Hapus dari favorit"
                } else {
                    "Tambahkan ke favorit"
                }
        }
    }

    // 11. Membersihkan ViewBinding ketika Fragment dihancurkan
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
