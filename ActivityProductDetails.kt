package com.example.fp_bp2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fp_bp2.models.WishlistItem

class ActivityProductDetails : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)

        val favoriteButton = findViewById<ImageView>(R.id.btn_favorite)
        val sizeSelector = findViewById<LinearLayout>(R.id.sizeSelector)
        val additionalTextView = findViewById<TextView>(R.id.additionalTextView)
        val buyNowButton = findViewById<TextView>(R.id.buyNow)
        val navHome = findViewById<ImageView>(R.id.nav_home)
        val navWishlist = findViewById<ImageView>(R.id.nav_wishlist)

        val productName = "Nike Sneakers"
        val productDescription = "High-quality sneakers for men"
        val productPrice = 1.80
        val productImageResId = R.drawable.sepatu1
        var selectedSize = "UK 40" // Default size

        val formattedPrice = productPrice

        val dbHelper = DatabaseHelper(this)

        favoriteButton?.setOnClickListener {
            handleFavoriteButtonClick(
                dbHelper,
                productName,
                productDescription,
                formattedPrice,
                selectedSize,
                productImageResId
            )
            navigateToActivity(WishlistActivity::class.java)
        }

        favoriteButton.setOnClickListener {
            val newItem = WishlistItem(
                name = "Sepatu1",
                quantity = 1,
                description = "Sepatu olahraga berkualitas tinggi.",
                price = 1.80,
                size = 40,
                imageResId = R.drawable.sepatu1
            )

            if (newItem.size !in 39..43) {
                Toast.makeText(this, "Ukuran default tidak valid.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val result = dbHelper.insertWishlistItem(newItem)
            if (result != -1L) {
                Toast.makeText(this, "Item berhasil ditambahkan ke wishlist!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Item sudah ada di wishlist.", Toast.LENGTH_SHORT).show()
            }
        }


        setupSizeSelector(sizeSelector) { size ->
            selectedSize = size
            Toast.makeText(this, "$selectedSize selected", Toast.LENGTH_SHORT).show()
        }

        additionalTextView?.setOnClickListener {
            Toast.makeText(this, "Full description coming soon!", Toast.LENGTH_SHORT).show()
        }

        buyNowButton?.setOnClickListener {
            startCheckoutActivity(productName, formattedPrice, selectedSize)
        }

        navHome?.setOnClickListener {
            navigateToActivity(MainActivity::class.java)
        }

        navWishlist?.setOnClickListener {
            navigateToActivity(WishlistActivity::class.java)
        }
    }

    private fun handleFavoriteButtonClick(
        dbHelper: DatabaseHelper,
        productName: String,
        productDescription: String,
        productPrice: Double,
        selectedSize: String,
        productImageResId: Int
    ) {
        if (productName.isEmpty() || productDescription.isEmpty() || selectedSize.isEmpty()) {
            Toast.makeText(this, "Invalid product data!", Toast.LENGTH_SHORT).show()
            Log.e("ActivityProductDetails", "Invalid product data")
            return
        }

        try {
            val wishlistItem = WishlistItem(
                name = productName,
                quantity = 1,
                description = "$productDescription (Size: $selectedSize)",
                price = productPrice,
                size = selectedSize.toIntOrNull(),
                imageResId = productImageResId
            )

            val result = dbHelper.insertWishlistItem(wishlistItem)

            if (result != -1L) {
                Toast.makeText(this, "Added to favorites!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Error adding to favorites!", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Log.e("ActivityProductDetails", "Error adding to favorites: ${e.message}")
            Toast.makeText(this, "Error adding to favorites!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupSizeSelector(sizeSelector: LinearLayout?, onSizeSelected: (String) -> Unit) {
        sizeSelector?.let {
            for (i in 0 until it.childCount) {
                val child = it.getChildAt(i)
                if (child is TextView) {
                    child.setOnClickListener { view ->
                        val size = (view as TextView).text.toString()
                        onSizeSelected(size)
                    }
                }
            }
        }
    }

    private fun startCheckoutActivity(
        productName: String,
        productPrice: Double,
        productSize: String
    ) {
        try {
            val intent = Intent(this, CheckoutActivity::class.java).apply {
                putExtra("PRODUCT_NAME", productName)
                putExtra("PRODUCT_PRICE", productPrice.toString())
                putExtra("PRODUCT_SIZE", productSize)
            }
            startActivity(intent)
        } catch (e: Exception) {
            Log.e("ActivityProductDetails", "Error starting CheckoutActivity: ${e.message}")
            Toast.makeText(this, "Error starting checkout!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateToActivity(destination: Class<*>) {
        try {
            val intent = Intent(this, destination)
            startActivity(intent)
        } catch (e: Exception) {
            Log.e("ActivityProductDetails", "Error navigating to activity: ${e.message}")
            Toast.makeText(this, "Error navigating to activity!", Toast.LENGTH_SHORT).show()
        }
    }
}
