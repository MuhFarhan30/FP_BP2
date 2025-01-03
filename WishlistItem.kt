package com.example.fp_bp2.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class WishlistItem(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val description: String? = null,
    val price: Double = 1.80,
    val imageResId: Int? = null,
    var size: Int? = null,
    var quantity: Int = 1,
    var isFavorite: Boolean = false
) : Parcelable {
    init {
        require(name.isNotBlank()) { "Nama item tidak boleh kosong." }

        require(quantity >= 1) { "Quantity harus minimal 1." }

        size?.let {
            require(it in 39..43) { "Ukuran harus di antara 39 dan 43." }
        }
        require(price > 0) { "Harga harus lebih besar dari 0." }
    }

    fun getTotalPrice(): Double = if (quantity > 0) price * quantity else 0.0

    fun getFormattedPrice(): String = "$${String.format("%.2f", price)}"
}