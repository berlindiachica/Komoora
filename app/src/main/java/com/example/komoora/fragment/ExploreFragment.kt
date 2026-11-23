
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
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.komoora.data.FavoriteManager
import com.example.komoora.data.entity.FavoriteEntity
import kotlinx.coroutines.launch
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
    private lateinit var favoriteManager: FavoriteManager
    private var favoriteButtons: List<Pair<ImageButton, FavoriteEntity>> = emptyList()

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

        // 5. Seluruh tombol hati terhubung ke Room Database
        favoriteManager = FavoriteManager(requireContext())
        favoriteButtons = listOf(
            binding.btnFavoriteKomodo to FavoriteEntity(
                destinationKey = "pulau_komodo",
                destinationName = "Pulau Komodo",
                location = "Taman Nasional Komodo",
                category = "Nature",
                imageName = "recommend_komodo"
            ),
            binding.btnFavoritePinkBeach to FavoriteEntity(
                destinationKey = "pink_beach",
                destinationName = "Pink Beach",
                location = "Manggarai Barat, Flores",
                category = "Nature",
                // Sesuaikan dengan nama drawable foto Pink Beach di fragment_exploration.xml
                imageName = "pink_beach"
            ),
            binding.btnFavoriteWaterfall to FavoriteEntity(
                destinationKey = "oenesu_waterfall",
                destinationName = "Oenesu Waterfall",
                location = "Kupang, NTT",
                category = "Nature",
                imageName = "recommend_waterfall"
            ),
            binding.btnFavoriteWaerebo to FavoriteEntity(
                destinationKey = "desa_adat_wae_rebo",
                destinationName = "Desa Adat Wae Rebo",
                location = "Manggarai, Flores",
                category = "Culture",
                imageName = "recommend_waerebo"
            ),
            binding.btnFavoriteLabuanBajo to FavoriteEntity(
                destinationKey = "labuan_bajo",
                destinationName = "Labuan Bajo",
                location = "Manggarai Barat, Flores",
                category = "Nature",
                imageName = "recommend_labuan_bajo"
            ),
            binding.btnFavoriteCave to FavoriteEntity(
                destinationKey = "bolok_crystal_cave",
                destinationName = "Bolok Crystal Cave",
                location = "Kupang Barat, NTT",
                category = "Nature",
                imageName = "recommend_cave"
            )
        )
        favoriteButtons.forEach { (button, destination) ->
            setupFavoriteButton(button, destination)
        }

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

    // 10. Menyimpan dan menghapus favorit lewat Room
    private fun setupFavoriteButton(
        button: ImageButton,
        destination: FavoriteEntity
    ) {
        button.isEnabled = false
        updateHeart(button, false)

        button.setOnClickListener {
            button.isEnabled = false
            viewLifecycleOwner.lifecycleScope.launch {
                try {
                    val saved = favoriteManager.toggleFavorite(destination)
                    updateHeart(button, saved)
                    Toast.makeText(
                        requireContext(),
                        if (saved) "${destination.destinationName} disimpan ke Favorites"
                        else "${destination.destinationName} dihapus dari Favorites",
                        Toast.LENGTH_SHORT
                    ).show()
                    refreshExploreHearts()
                } catch (e: Exception) {
                    Toast.makeText(
                        requireContext(),
                        "Gagal memperbarui Favorites",
                        Toast.LENGTH_SHORT
                    ).show()
                } finally {
                    button.isEnabled = true
                }
            }
        }
    }

    private fun updateHeart(button: ImageButton, saved: Boolean) {
        button.imageTintList = ColorStateList.valueOf(
            if (saved) Color.RED else Color.LTGRAY
        )
        button.contentDescription =
            if (saved) "Hapus dari favorit" else "Tambahkan ke favorit"
    }

    // Sinkronkan dengan favorit dari Home, Kategori, dan halaman Favorites
    private fun refreshExploreHearts() {
        if (_binding == null || !::favoriteManager.isInitialized) return
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val keys = com.example.komoora.data.AppDatabase
                    .getInstance(requireContext())
                    .favoriteDao()
                    .getAll()
                    .map { it.destinationKey }
                    .toSet()
                favoriteButtons.forEach { (button, destination) ->
                    updateHeart(button, destination.destinationKey in keys)
                    button.isEnabled = true
                }
            } catch (e: Exception) {
                Toast.makeText(
                    requireContext(),
                    "Gagal memuat status favorit",
                    Toast.LENGTH_SHORT
                ).show()
                favoriteButtons.forEach { (button, _) -> button.isEnabled = true }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (_binding != null && ::favoriteManager.isInitialized) {
            refreshExploreHearts()
        }
    }

    // 11. Membersihkan ViewBinding ketika Fragment dihancurkan
    override fun onDestroyView() {
        favoriteButtons = emptyList()
        _binding = null
        super.onDestroyView()
    }
}
