package com.example.komoora

import androidx.annotation.DrawableRes

data class Destination(
    val id: String,
    val name: String,
    val location: String,
    val price: String,
    val rating: String,
    val reviewCount: String,
    val description: String,
    @DrawableRes val imageResId: Int
)

object DestinationData {

    val destinations = listOf(
        Destination(
            id = "labuan_bajo",
            name = "Labuan Bajo",
            location = "Manggarai Barat, Nusa Tenggara Timur",
            price = "$ 40",
            rating = "4.8",
            reviewCount = "1.2k",
            description = "Labuan Bajo merupakan destinasi wisata unggulan di Nusa Tenggara Timur. Tempat ini terkenal dengan pemandangan laut yang indah, gugusan pulau yang menawan, serta perairan biru yang jernih. Labuan Bajo juga menjadi titik awal perjalanan untuk menjelajahi pulau-pulau di sekitarnya.",
            imageResId = R.drawable.recommend_labuan_bajo
        ),
        Destination(
            id = "komodo_island",
            name = "Komodo Island",
            location = "Manggarai Barat, Nusa Tenggara Timur",
            price = "$ 50",
            rating = "4.8",
            reviewCount = "980",
            description = "Pulau Komodo terkenal sebagai habitat asli komodo dan memiliki pemandangan alam yang memukau. Pengunjung dapat mengikuti tur dengan pemandu untuk mengenal komodo serta menikmati keindahan alam pulau.",
            imageResId = R.drawable.recommend_komodo
        ),
        Destination(
            id = "pink_beach",
            name = "Pink Beach",
            location = "Manggarai Barat, Nusa Tenggara Timur",
            price = "$ 35",
            rating = "4.7",
            reviewCount = "850",
            description = "Pink Beach terkenal dengan pasir pantainya yang berwarna merah muda dan air laut yang jernih. Destinasi ini cocok untuk menikmati pemandangan pantai, snorkeling, dan menjelajahi keindahan bawah laut.",
            imageResId = R.drawable.pink_beach
        ),
        Destination(
            id = "oenesu_waterfall",
            name = "Oenesu Waterfall",
            location = "Kupang, Nusa Tenggara Timur",
            price = "$ 15",
            rating = "4.6",
            reviewCount = "420",
            description = "Air Terjun Oenesu menawarkan suasana alam yang sejuk dengan aliran air bertingkat dan lingkungan yang hijau. Tempat ini cocok untuk menikmati pemandangan alam dan bersantai.",
            imageResId = R.drawable.recommend_waterfall
        ),
        Destination(
            id = "waerebo_village",
            name = "Waerebo Village",
            location = "Manggarai, Nusa Tenggara Timur",
            price = "$ 30",
            rating = "4.8",
            reviewCount = "620",
            description = "Desa Waerebo dikenal dengan rumah adat Mbaru Niang dan suasana pegunungan yang asri. Pengunjung dapat mengenal budaya lokal serta menikmati perjalanan menuju desa tradisional ini.",
            imageResId = R.drawable.recommend_waerebo
        ),
        Destination(
            id = "bolok_crystal_cave",
            name = "Bolok Crystal Cave",
            location = "Kupang Barat, Nusa Tenggara Timur",
            price = "$ 15",
            rating = "4.6",
            reviewCount = "300",
            description = "Bolok Crystal Cave menjadi pilihan untuk menjelajahi pesona alam di kawasan Kupang. Periksa akses dan kondisi kunjungan sebelum merencanakan perjalanan.",
            imageResId = R.drawable.recommend_cave
        )
    )

    fun findById(id: String?): Destination? {
        return destinations.find { it.id == id }
    }

    fun findByName(name: String?): Destination? {
        return destinations.find {
            it.name.equals(name?.trim(), ignoreCase = true)
        }
    }
}