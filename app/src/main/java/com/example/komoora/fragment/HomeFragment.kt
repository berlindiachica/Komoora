package com.example.komoora.fragment

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.komoora.data.DestinationNavigation
import com.example.komoora.R
import com.example.komoora.activity.LoginActivity
import com.example.komoora.databinding.FragmentHomeBinding
import com.example.komoora.activity.TutorialActivity
import kotlin.jvm.java
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.komoora.activity.KategoriActivity
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.komoora.data.AppDatabase
import com.example.komoora.data.entity.FavoriteEntity
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var isFavorite = false
    private lateinit var db: AppDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(
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
        db = AppDatabase.getInstance(requireContext())

        val originalToolbarHeight = binding.toolbar.layoutParams.height

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _, insets ->

            val statusBarHeight = insets.getInsets(
                WindowInsetsCompat.Type.statusBars()
            ).top

            binding.toolbar.layoutParams.height =
                originalToolbarHeight + statusBarHeight

            binding.toolbar.setPadding(
                0,
                statusBarHeight,
                0,
                0
            )

            binding.toolbar.requestLayout()

            insets
        }

        ViewCompat.requestApplyInsets(binding.root)

        // Toolbar dan menu titik tiga
        // Toolbar dan menu Guide serta Logout
        binding.toolbar.title = ""
        binding.toolbar.inflateMenu(R.menu.home_menu)

        binding.toolbar.menu
            .findItem(R.id.action_tutorial)
            ?.icon
            ?.mutate()
            ?.setTint(Color.WHITE)

        binding.toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {

                R.id.action_tutorial -> {
                    val intent = Intent(
                        requireContext(),
                        TutorialActivity::class.java
                    )
                    startActivity(intent)
                    true
                }

                R.id.action_logout -> {
                    tampilkanDialogLogout()
                    true
                }

                else -> false
            }
        }

        // Membuka halaman DetailActivity
        binding.cardLabuanBajo.setOnClickListener {
            DestinationNavigation.openDetail(
                requireContext(),
                "labuan_bajo"
            )
        }

//        // Tombol favorit
//        binding.btnFavoriteLabuanBajo.setOnClickListener {
//            isFavorite = !isFavorite
//
//            binding.btnFavoriteLabuanBajo.imageTintList =
//                ColorStateList.valueOf(
//                    if (isFavorite) Color.RED else Color.GRAY
//                )
//
//            binding.btnFavoriteLabuanBajo.contentDescription =
//                if (isFavorite) {
//                    "Hapus dari favorit"
//                } else {
//                    "Tambahkan ke favorit"
//                }
//        }

        binding.btnFavoriteLabuanBajo.isEnabled = false

        binding.btnFavoriteLabuanBajo.setOnClickListener {
            toggleFavoriteLabuanBajo()
        }

        // Nature
        binding.btnNature.setOnClickListener {
            bukaKategori(0)
        }

        // Hotel
        binding.btnHotel.setOnClickListener {
            bukaKategori(1)
        }

        // Culinary
        binding.btnCulinary.setOnClickListener {
            bukaKategori(2)
        }

        // Culture
        binding.btnCulture.setOnClickListener {
            bukaKategori(3)
        }

        // Souvenir
        binding.btnSouvenir.setOnClickListener {
            bukaKategori(4)
        }

    }

    private fun updateFavoriteIcon() {

        val color = if (isFavorite) {
            Color.RED
        } else {
            Color.GRAY
        }

        binding.btnFavoriteLabuanBajo.imageTintList =
            ColorStateList.valueOf(color)

        binding.btnFavoriteLabuanBajo.contentDescription =
            if (isFavorite) {
                "Hapus dari favorit"
            } else {
                "Tambahkan ke favorit"
            }
    }

    private fun checkFavoriteStatus() {

        binding.btnFavoriteLabuanBajo.isEnabled = false

        viewLifecycleOwner.lifecycleScope.launch {

            try {
                // Memeriksa data Labuan Bajo di Room
                isFavorite = db.favoriteDao().isFavorite(
                    "labuan_bajo"
                )

                // Memperbarui warna ikon
                updateFavoriteIcon()

            } catch (e: Exception) {

                Toast.makeText(
                    requireContext(),
                    "Gagal memeriksa status favorit",
                    Toast.LENGTH_SHORT
                ).show()

            } finally {
                binding.btnFavoriteLabuanBajo.isEnabled = true
            }
        }
    }

    private fun toggleFavoriteLabuanBajo() {

        binding.btnFavoriteLabuanBajo.isEnabled = false

        viewLifecycleOwner.lifecycleScope.launch {

            try {
                // Memeriksa data yang ada di Room
                val favorite = db.favoriteDao().getByKey(
                    "labuan_bajo"
                )

                if (favorite == null) {

                    // Jika belum disimpan, tambahkan
                    val newFavorite = FavoriteEntity(
                        destinationKey = "labuan_bajo",
                        destinationName = "Labuan Bajo",
                        location = "Manggarai Barat, Flores",
                        category = "Nature",
                        imageName = "recommend_labuan_bajo"
                    )

                    db.favoriteDao().insert(newFavorite)

                    Toast.makeText(
                        requireContext(),
                        "Labuan Bajo ditambahkan ke Favorites",
                        Toast.LENGTH_SHORT
                    ).show()

                } else {

                    // Jika sudah disimpan, hapus
                    db.favoriteDao().delete(favorite)

                    Toast.makeText(
                        requireContext(),
                        "Labuan Bajo dihapus dari Favorites",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                // Membaca kembali status sebenarnya dari Room
                isFavorite = db.favoriteDao().isFavorite(
                    "labuan_bajo"
                )

                updateFavoriteIcon()

            } catch (e: Exception) {

                Toast.makeText(
                    requireContext(),
                    "Gagal memperbarui Favorites",
                    Toast.LENGTH_SHORT
                ).show()

            } finally {
                binding.btnFavoriteLabuanBajo.isEnabled = true
            }
        }
    }




    // Membuka halaman kategori sesuai tombol
    private fun bukaKategori(position: Int) {

        val intent = Intent(
            requireContext(),
            KategoriActivity::class.java
        )

        // Mengirim posisi kategori
        intent.putExtra(
            "selected_tab",
            position
        )

        startActivity(intent)
    }
    // Dialog konfirmasi Logout
    private fun tampilkanDialogLogout() {
        AlertDialog.Builder(requireContext())
            .setTitle("Logout")
            .setMessage("Apakah kamu yakin ingin keluar?")
            .setPositiveButton("Ya") { _, _ ->

                // Menghapus status login
                val sharedPref = requireContext()
                    .getSharedPreferences(
                        "user_pref",
                        Context.MODE_PRIVATE
                    )

                sharedPref.edit()
                    .putBoolean("isLogin", false)
                    .apply()

                // Kembali ke LoginActivity
                val intent = Intent(
                    requireContext(),
                    LoginActivity::class.java
                )

                intent.flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or
                            Intent.FLAG_ACTIVITY_CLEAR_TASK

                startActivity(intent)
            }
            .setNegativeButton("Tidak") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    override fun onResume() {
        super.onResume()

        if (_binding != null) {
            checkFavoriteStatus()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}