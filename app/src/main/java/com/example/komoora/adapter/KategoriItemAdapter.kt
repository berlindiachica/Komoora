
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
    private val onItemClick: (KategoriItemModel) -> Unit
) : RecyclerView.Adapter<KategoriItemAdapter.KategoriViewHolder>() {

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

            // Menampilkan nama dan deskripsi
            tvCategoryName.text = item.name
            tvCategoryDescription.text = item.description

            // Mengatur tampilan gambar atau ikon
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

            // Menampilkan gambar menggunakan Glide
            Glide.with(holder.itemView.context)
                .load(item.imageResId)
                .into(imgCategory)

            // Ketika kartu ditekan
            root.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    override fun getItemCount(): Int = itemList.size
}
