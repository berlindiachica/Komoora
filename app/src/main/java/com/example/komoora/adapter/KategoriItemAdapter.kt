package com.example.komoora.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.komoora.data.KategoriItemModel
import com.example.komoora.databinding.ItemKategoriBinding

class KategoriItemAdapter(
    private val itemList: List<KategoriItemModel>,
    private val onItemClick: (KategoriItemModel) -> Unit,
    private val onFavoriteClick: ((KategoriItemModel) -> Unit)? = null
) : RecyclerView.Adapter<KategoriItemAdapter.KategoriViewHolder>() {

    // Menyimpan nama destinasi yang sudah difavoritkan
    private var favoriteNames: Set<String> = emptySet()

    inner class KategoriViewHolder(
        val binding: ItemKategoriBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): KategoriViewHolder {

        val binding = ItemKategoriBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return KategoriViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: KategoriViewHolder,
        position: Int
    ) {
        val item = itemList[position]

        with(holder.binding) {

            // MENAMPILKAN NAMA DAN DESKRIPSI
            tvCategoryName.text = item.name
            tvCategoryDescription.text = item.description

            // MENGATUR TAMPILAN GAMBAR ATAU IKON
            if (item.isIcon) {

                imgCategory.setBackgroundColor(
                    Color.parseColor("#0D3A84")
                )

                imgCategory.scaleType =
                    ImageView.ScaleType.CENTER_INSIDE

                imgCategory.imageTintList =
                    ColorStateList.valueOf(Color.WHITE)

                val padding = (
                        42 * holder.itemView.resources
                            .displayMetrics.density
                        ).toInt()

                imgCategory.setPadding(
                    padding,
                    padding,
                    padding,
                    padding
                )

            } else {

                imgCategory.setBackgroundColor(
                    Color.TRANSPARENT
                )

                imgCategory.scaleType =
                    ImageView.ScaleType.CENTER_CROP

                imgCategory.imageTintList = null

                imgCategory.setPadding(0, 0, 0, 0)
            }

            // MENAMPILKAN GAMBAR MENGGUNAKAN GLIDE
            Glide.with(imgCategory).clear(imgCategory)
            imgCategory.setImageResource(item.imageResId)

            // MEMERIKSA STATUS FAVORITE
            val isFavorite = favoriteNames.contains(item.name)

            // MENGUBAH WARNA IKON HATI
            val heartColor = if (isFavorite) {
                Color.RED
            } else {
                Color.parseColor("#9AA4B2")
            }

            btnFavoriteCategory.imageTintList =
                ColorStateList.valueOf(heartColor)

            btnFavoriteCategory.contentDescription =
                if (isFavorite) {
                    "Hapus ${item.name} dari favorit"
                } else {
                    "Tambahkan ${item.name} ke favorit"
                }

            // KETIKA TOMBOL HATI DITEKAN
            btnFavoriteCategory.setOnClickListener {
                onFavoriteClick?.invoke(item)
            }

            // KETIKA KARTU DESTINASI DITEKAN
            root.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    // MEMPERBARUI DATA FAVORITE PADA ADAPTER
    fun setFavoriteNames(names: Set<String>) {
        favoriteNames = names
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = itemList.size
}
