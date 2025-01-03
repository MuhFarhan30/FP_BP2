package com.example.fp_bp2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fp_bp2.adapters.WishlistAdapter
import com.example.fp_bp2.models.WishlistItem

class WishlistActivity : AppCompatActivity() {
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var recyclerView: RecyclerView
    private lateinit var wishlistAdapter: WishlistAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wishlist)

        val btnHome = findViewById<ImageView>(R.id.home)
        btnHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        databaseHelper = DatabaseHelper(this)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val wishlistItems = databaseHelper.getWishlistItems().toMutableList()
        wishlistAdapter = WishlistAdapter(wishlistItems) { item, selectedSize ->

            if (selectedSize != null) {
                databaseHelper.updateWishlistItem(item)
            } else {
                Toast.makeText(this, "Harap pilih ukuran yang valid untuk ${item.name}.", Toast.LENGTH_SHORT).show()
            }
        }
        recyclerView.adapter = wishlistAdapter

        val proceedButton: Button = findViewById(R.id.proceedButton)
        proceedButton.setOnClickListener {
            if (wishlistItems.isNotEmpty()) {
                proceedToPayment(wishlistItems)
            } else {
                Toast.makeText(this, "Wishlist Anda kosong.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun proceedToPayment(items: List<WishlistItem>) {
        Toast.makeText(this, "Menuju ke halaman pembayaran...", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, CheckoutActivity::class.java)
        intent.putParcelableArrayListExtra("wishlist_items", ArrayList(items))
        startActivity(intent)
    }
}
