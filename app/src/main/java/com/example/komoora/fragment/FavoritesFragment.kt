package com.example.komoora.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.komoora.adapter.FavoriteAdapter
import com.example.komoora.data.AppDatabase
import com.example.komoora.data.entity.FavoriteEntity
import com.example.komoora.databinding.FragmentFavoritesBinding
import kotlinx.coroutines.launch
import android.content.res.ColorStateList
import android.graphics.Color
import com.google.android.material.chip.Chip

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private lateinit var db: AppDatabase
    private lateinit var adapter: FavoriteAdapter

    private val favorites = mutableListOf<FavoriteEntity>()

    private val allFavorites = mutableListOf<FavoriteEntity>()

    private var selectedCategory = "All"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFavoritesBinding.inflate(
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

        // Menghubungkan Room Database
        db = AppDatabase.getInstance(requireContext())

        // Membuat adapter Favorites
        adapter = FavoriteAdapter(favorites) { favorite ->
            deleteFavorite(favorite)
        }

        // Menampilkan data secara vertikal
        binding.rvFavorites.apply {
            layoutManager = LinearLayoutManager(
                requireContext()
            )
            adapter = this@FavoritesFragment.adapter
        }

        setupCategoryFilter()
    }

    // Memuat ulang favorit ketika halaman dibuka
    override fun onResume() {
        super.onResume()

        if (_binding != null) {
            fetchFavorites()
        }
    }

    // Membaca data dari database

    private fun fetchFavorites() {

        viewLifecycleOwner.lifecycleScope.launch {

            try {
                // Mengambil semua data dari Room Database
                val result = db.favoriteDao().getAll()

                // Menyimpan semua data untuk filter
                allFavorites.clear()
                allFavorites.addAll(result)

                // Menampilkan sesuai kategori pilihan
                showFilteredFavorites()

            } catch (e: Exception) {

                Toast.makeText(
                    requireContext(),
                    "Gagal memuat data Favorites",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun setupCategoryFilter() {

        val categories = listOf(
            "All",
            "Nature",
            "Hotel",
            "Culinary",
            "Culture",
            "Souvenir"
        )

        val blue = Color.parseColor("#0D3A84")
        val lightBlue = Color.parseColor("#E8F0FF")

        // Warna chip ketika dipilih dan tidak dipilih
        val backgroundColors = ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_checked),
                intArrayOf()
            ),
            intArrayOf(blue, lightBlue)
        )

        val textColors = ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_checked),
                intArrayOf()
            ),
            intArrayOf(Color.WHITE, blue)
        )

        binding.chipGroupFavorites.apply {
            isSingleSelection = true
            isSelectionRequired = true
            removeAllViews()
        }

        categories.forEach { category ->

            val chip = Chip(requireContext()).apply {

                text = category
                isCheckable = true
                isCheckedIconVisible = false

                chipBackgroundColor = backgroundColors
                setTextColor(textColors)

                chipStrokeWidth = 0f

                setOnClickListener {
                    selectedCategory = category
                    showFilteredFavorites()
                }
            }

            binding.chipGroupFavorites.addView(chip)

            if (category == selectedCategory) {
                chip.isChecked = true
            }
        }
    }

    private fun showFilteredFavorites() {

        // Menampilkan data sesuai kategori
        val filteredData = if (selectedCategory == "All") {
            allFavorites.toList()
        } else {
            allFavorites.filter {
                it.category.equals(
                    selectedCategory,
                    ignoreCase = true
                )
            }
        }

        // Memperbarui isi RecyclerView
        favorites.clear()
        favorites.addAll(filteredData)

        adapter.notifyDataSetChanged()

        // Jumlah seluruh destinasi yang disimpan
        binding.tvFavoriteCount.text =
            "${allFavorites.size} saved destinations"

        // Judul sesuai kategori yang dipilih
        binding.tvSavedTitle.text =
            if (selectedCategory == "All") {
                "Saved Destinations (${favorites.size})"
            } else {
                "$selectedCategory Favorites (${favorites.size})"
            }

        // Tampilkan empty state jika kategori kosong
        val isEmpty = favorites.isEmpty()

        binding.layoutEmpty.visibility =
            if (isEmpty) View.VISIBLE else View.GONE

        binding.rvFavorites.visibility =
            if (isEmpty) View.GONE else View.VISIBLE
    }



    // Menghapus data favorit
    private fun deleteFavorite(favorite: FavoriteEntity) {

        viewLifecycleOwner.lifecycleScope.launch {

            try {
                db.favoriteDao().delete(favorite)

                Toast.makeText(
                    requireContext(),
                    "${favorite.destinationName} dihapus dari Favorites",
                    Toast.LENGTH_SHORT
                ).show()

                // Refresh daftar setelah menghapus
                fetchFavorites()

            } catch (e: Exception) {

                Toast.makeText(
                    requireContext(),
                    "Gagal menghapus favorit",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onDestroyView() {

        // Melepaskan RecyclerView dari adapter
        binding.rvFavorites.adapter = null

        _binding = null
        super.onDestroyView()
    }
}
