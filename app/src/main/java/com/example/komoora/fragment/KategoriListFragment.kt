
package com.example.komoora.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.komoora.R
import com.example.komoora.adapter.KategoriItemAdapter
import com.example.komoora.data.KategoriItemModel
import com.example.komoora.databinding.FragmentKategoriListBinding

class KategoriListFragment : Fragment() {

    private var _binding: FragmentKategoriListBinding? = null
    private val binding get() = _binding!!

    companion object {

        private const val ARG_TAB_INDEX = "tab_index"

        fun newInstance(position: Int): KategoriListFragment {

            val fragment = KategoriListFragment()

            val args = Bundle()
            args.putInt(ARG_TAB_INDEX, position)

            fragment.arguments = args

            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentKategoriListBinding.inflate(
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

        // Mengambil posisi kategori yang dipilih
        val tabIndex = arguments?.getInt(
            ARG_TAB_INDEX,
            0
        ) ?: 0

        // Mengambil daftar berdasarkan kategori
        val itemList = getCategoryItems(tabIndex)

        // Membuat adapter RecyclerView
        val kategoriAdapter = KategoriItemAdapter(
            itemList
        ) { selectedItem ->

            Toast.makeText(
                requireContext(),
                "Anda memilih ${selectedItem.name}",
                Toast.LENGTH_SHORT
            ).show()
        }

        // Menampilkan daftar dalam Grid 2 kolom
        binding.rvCategoryItems.apply {

            layoutManager = GridLayoutManager(
                requireContext(),
                2
            )

            adapter = kategoriAdapter
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
        super.onDestroyView()
        _binding = null
    }
}
