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

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var isFavorite = false

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

        // Toolbar dan menu titik tiga
        binding.toolbar.title = ""
        binding.toolbar.inflateMenu(R.menu.home_menu)

        binding.toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
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

        // Tombol favorit
        binding.btnFavoriteLabuanBajo.setOnClickListener {
            isFavorite = !isFavorite

            binding.btnFavoriteLabuanBajo.imageTintList =
                ColorStateList.valueOf(
                    if (isFavorite) Color.RED else Color.GRAY
                )

            binding.btnFavoriteLabuanBajo.contentDescription =
                if (isFavorite) {
                    "Hapus dari favorit"
                } else {
                    "Tambahkan ke favorit"
                }
        }
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}