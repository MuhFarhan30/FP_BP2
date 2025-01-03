package com.example.fp_bp2

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CheckoutActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_PRODUCT_NAME = "PRODUCT_NAME"
        const val EXTRA_PRODUCT_PRICE = "PRODUCT_PRICE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        val productNameTextView = findViewById<TextView>(R.id.itemName)
        val productPriceTextView = findViewById<TextView>(R.id.itemPrice)

        val productName = intent.getStringExtra(EXTRA_PRODUCT_NAME) ?: "Product sepatu1"
        val productPrice = intent.getDoubleExtra(EXTRA_PRODUCT_PRICE, 0.0)

        productNameTextView.text = productName
        productPriceTextView.text = "₹${String.format("%.2f", productPrice)}"

        val btnHome = findViewById<ImageView>(R.id.nav_home)
        val wishlistButton = findViewById<ImageView>(R.id.nav_wishlist)

        btnHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        wishlistButton.setOnClickListener {
            val intent = Intent(this, WishlistActivity::class.java)
            startActivity(intent)
        }
    }
}
