package com.example.komoora.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.komoora.R
import com.example.komoora.adapter.KategoriItemAdapter
import com.example.komoora.data.AppDatabase
import com.example.komoora.data.FavoriteManager
import com.example.komoora.data.KategoriItemModel
import com.example.komoora.data.entity.FavoriteEntity
import com.example.komoora.databinding.FragmentKategoriListBinding
import kotlinx.coroutines.launch
import java.util.Locale

class KategoriListFragment : Fragment() {

    private var _binding: FragmentKategoriListBinding? = null
    private val binding get() = _binding!!

    private lateinit var favoriteManager: FavoriteManager
    private var kategoriAdapter: KategoriItemAdapter? = null

    companion object {
        private const val ARG_TAB_INDEX = "tab_index"

        fun newInstance(position: Int): KategoriListFragment {
            return KategoriListFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_TAB_INDEX, position)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentKategoriListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tabIndex = arguments?.getInt(ARG_TAB_INDEX) ?: 0
        val itemList = getCategoryItems(tabIndex)
        favoriteManager = FavoriteManager(requireContext())

        // Klik kartu tetap seperti sebelumnya; klik hati khusus mengubah favorit.
        val adapter = KategoriItemAdapter(
            itemList = itemList,
            onItemClick = { selectedItem ->
                Toast.makeText(
                    requireContext(),
                    "Anda memilih ${selectedItem.name}",
                    Toast.LENGTH_SHORT
                ).show()
            },
            onFavoriteClick = { selectedItem ->
                toggleFavorite(selectedItem, tabIndex)
            }
        )
        kategoriAdapter = adapter

        binding.rvCategoryItems.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            this.adapter = adapter
        }
    }

    // Dipanggil lagi ketika pengguna kembali dari halaman Favorites.
    override fun onResume() {
        super.onResume()
        if (_binding != null && ::favoriteManager.isInitialized) {
            refreshFavoriteCategory()
        }
    }

    private fun toggleFavorite(item: KategoriItemModel, tabIndex: Int) {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val category = when (tabIndex) {
                    0 -> "Nature"
                    1 -> "Hotel"
                    2 -> "Culinary"
                    3 -> "Culture"
                    4 -> "Souvenir"
                    else -> "Nature"
                }

                // Kunci stabil: Labuan Bajo di Home dan kategori sama-sama labuan_bajo.
                val key = item.name.trim()
                    .lowercase(Locale.ROOT)
                    .replace(Regex("[^a-z0-9]+"), "_")
                    .trim('_')

                val imageName = resources.getResourceEntryName(item.imageResId)
                val favorite = FavoriteEntity(
                    destinationKey = key,
                    destinationName = item.name,
                    location = item.description,
                    category = category,
                    imageName = imageName
                )

                val isSaved = favoriteManager.toggleFavorite(favorite)
                Toast.makeText(
                    requireContext(),
                    if (isSaved) "${item.name} disimpan ke Favorites"
                    else "${item.name} dihapus dari Favorites",
                    Toast.LENGTH_SHORT
                ).show()
                refreshFavoriteCategory()
            } catch (e: Exception) {
                Toast.makeText(
                    requireContext(),
                    "Gagal memperbarui Favorites",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun refreshFavoriteCategory() {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val favoriteNames = AppDatabase.getInstance(requireContext())
                    .favoriteDao()
                    .getAll()
                    .map { it.destinationName }
                    .toSet()

                kategoriAdapter?.setFavoriteNames(favoriteNames)
            } catch (e: Exception) {
                Toast.makeText(
                    requireContext(),
                    "Gagal memuat status favorit",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    // Data setiap kategori Komoora
    private fun getCategoryItems(
        tabIndex: Int
    ): List<KategoriItemModel> {

        return when (tabIndex) {

            // NATURE
            0 -> listOf(

                KategoriItemModel(
                    "Labuan Bajo",
                    "Manggarai Barat, Flores",
                    R.drawable.recommend_labuan_bajo
                ),

                KategoriItemModel(
                    "Pulau Komodo",
                    "Taman Nasional Komodo",
                    R.drawable.recommend_komodo
                ),

                KategoriItemModel(
                    "Oenesu Waterfall",
                    "Kupang, NTT",
                    R.drawable.recommend_waterfall
                ),

                KategoriItemModel(
                    "Bolok Crystal Cave",
                    "Kupang Barat, NTT",
                    R.drawable.recommend_cave
                )
            )

            // HOTEL
            1 -> listOf(

                KategoriItemModel(
                    "Lucia Cottages",
                    "Akomodasi kawasan Labuan Bajo",
                    R.drawable.hotellucia
                ),

                KategoriItemModel(
                    "Bintang Flores Hotel",
                    "Pilihan penginapan di Flores",
                    R.drawable.hotelbintang
                ),

                KategoriItemModel(
                    "Sylvia Hotel Premier Kupang",
                    "Akomodasi Kota Kupang",
                    R.drawable.hotelsylvia
                ),

                KategoriItemModel(
                    "Casa Kandara",
                    "Penginapan kawasan Sumba",
                    R.drawable.hotelcasa
                )
            )

            // CULINARY
            2 -> listOf(

                KategoriItemModel(
                    "Se'i Sapi",
                    "Kuliner daging asap khas NTT",
                    R.drawable.culinersei
                ),

                KategoriItemModel(
                    "Jagung Bose",
                    "Olahan jagung khas NTT",
                    R.drawable.culinerbose
                ),

                KategoriItemModel(
                    "Catemak Jagung",
                    "Hidangan jagung dan kacang",
                    R.drawable.culinercatemak
                ),

                KategoriItemModel(
                    "Kolo",
                    "Olahan nasi dalam bambu",
                    R.drawable.culinerkolo
                )
            )

            // CULTURE
            3 -> listOf(

                KategoriItemModel(
                    "Desa Adat Wae Rebo",
                    "Manggarai, Flores",
                    R.drawable.recommend_waerebo
                ),

                KategoriItemModel(
                    "Tari Caci",
                    "Tradisi budaya Manggarai",
                    R.drawable.budayataricaci
                ),

                KategoriItemModel(
                    "Tenun Ikat Flores",
                    "Kerajinan tradisional Flores",
                    R.drawable.budayatenunikat
                ),

                KategoriItemModel(
                    "Rumah Adat Sumba",
                    "Arsitektur tradisional Sumba",
                    R.drawable.budayarumahadatsumba
                )
            )

            // SOUVENIR
            4 -> listOf(

                KategoriItemModel(
                    "Kain Tenun Ikat",
                    "Oleh-oleh khas NTT",
                    R.drawable.budayatenunikat
                ),

                KategoriItemModel(
                    "Kopi Flores",
                    "Produk kopi dari Flores",
                    R.drawable.kopiflores
                ),

                KategoriItemModel(
                    "Miniatur Sasando",
                    "Kerajinan khas NTT",
                    R.drawable.miniatursasando
                ),

                KategoriItemModel(
                    "Kerajinan Anyaman",
                    "Produk kerajinan tradisional",
                    R.drawable.anyaman
                )
            )

            else -> emptyList()
        }
    }

    override fun onDestroyView() {
        binding.rvCategoryItems.adapter = null
        kategoriAdapter = null
        _binding = null
        super.onDestroyView()
    }
}