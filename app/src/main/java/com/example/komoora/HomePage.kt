
package com.example.komoora

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.komoora.databinding.ActivityHomePageBinding

class HomePage : AppCompatActivity() {

    private lateinit var binding: ActivityHomePageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Toolbar
        setSupportActionBar(binding.toolbar)

        supportActionBar?.apply {
            title = "Komoora"
            setDisplayHomeAsUpEnabled(false)
        }

        // Membuka DetailActivity ketika card Labuan Bajo ditekan
        binding.cardLabuanBajo.setOnClickListener {
            val intent = Intent(this, DetailPage::class.java)
            startActivity(intent)
        }
    }

    // Menampilkan menu titik tiga pada Toolbar
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return true
    }

    // Menangani pilihan menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                tampilkanDialogLogout()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    // Dialog konfirmasi Logout
    private fun tampilkanDialogLogout() {
        AlertDialog.Builder(this)
            .setTitle("Logout")
            .setMessage("Apakah kamu yakin ingin keluar?")
            .setPositiveButton("Ya") { _, _ ->

                // Menghapus status login
                val sharedPref = getSharedPreferences(
                    "user_pref",
                    MODE_PRIVATE
                )

                sharedPref.edit()
                    .putBoolean("isLogin", false)
                    .apply()

                // Kembali ke LoginPage
                val intent = Intent(this, LoginPage::class.java)

                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TASK

                startActivity(intent)
                finish()
            }
            .setNegativeButton("Tidak") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}
