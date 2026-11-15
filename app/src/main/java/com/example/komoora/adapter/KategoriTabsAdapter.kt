
package com.example.komoora.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.komoora.fragment.KategoriListFragment

class KategoriTabsAdapter(
    activity: FragmentActivity
) : FragmentStateAdapter(activity) {

    // Jumlah tab kategori
    override fun getItemCount(): Int = 5

    // Fragment yang ditampilkan sesuai posisi tab
    override fun createFragment(position: Int): Fragment {

        return when (position) {
            0 -> KategoriListFragment.newInstance(0)
            1 -> KategoriListFragment.newInstance(1)
            2 -> KategoriListFragment.newInstance(2)
            3 -> KategoriListFragment.newInstance(3)
            4 -> KategoriListFragment.newInstance(4)

            else -> throw IllegalStateException(
                "Posisi tab tidak valid"
            )
        }
    }
}
