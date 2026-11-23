package com.example.komoora.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.komoora.R
import com.example.komoora.data.entity.FavoriteEntity
import com.example.komoora.databinding.ItemFavoriteBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class FavoriteAdapter(
    private val favorites: List<FavoriteEntity>,
    private val onDelete: (FavoriteEntity) -> Unit
) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    class FavoriteViewHolder(
        val binding: ItemFavoriteBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteViewHolder {

        val binding = ItemFavoriteBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: FavoriteViewHolder,
        position: Int
    ) {
        val favorite = favorites[position]

        with(holder.binding) {

            tvFavoriteName.text = favorite.destinationName
            tvFavoriteLocation.text = favorite.location
            tvFavoriteCategory.text = favorite.category

            val context = holder.itemView.context

            val imageResId = context.resources.getIdentifier(
                favorite.imageName,
                "drawable",
                context.packageName
            )

            if (imageResId != 0) {
                imgFavorite.setImageResource(imageResId)
            } else {
                imgFavorite.setImageResource(R.drawable.ic_nature)
            }

            btnDeleteFavorite.setOnClickListener {

                MaterialAlertDialogBuilder(context)
                    .setTitle("Hapus dari Favorites?")
                    .setMessage(
                        "Apakah kamu yakin ingin menghapus " +
                                "${favorite.destinationName} dari daftar favorit?"
                    )
                    .setPositiveButton("Hapus") { _, _ ->
                        onDelete(favorite)
                    }
                    .setNegativeButton("Batal", null)
                    .show()
            }
        }
    }

    override fun getItemCount(): Int = favorites.size
}
