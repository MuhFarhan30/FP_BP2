package com.example.fp_bp2

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {

    private var toolbarTitle: TextView? = null
    private var userName: TextView? = null
    private var userEmail: TextView? = null
    private var wishListOption: TextView? = null
    private var logoutOption: TextView? = null
    private var profileImage: ImageView? = null
    private var navHome: ImageView? = null
    private var navWishlist: ImageView? = null
    private var navShop: ImageView? = null
    private var navSearch: ImageView? = null
    private var navProfile: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // Initialize views
        toolbarTitle = findViewById(R.id.toolbarTitle)
        userName = findViewById(R.id.userName)
        userEmail = findViewById(R.id.userEmail)
        wishListOption = findViewById(R.id.wishlistOption)
        logoutOption = findViewById(R.id.logoutOption)
        profileImage = findViewById(R.id.profileImage)

        navHome = findViewById(R.id.nav_home)
        navWishlist = findViewById(R.id.nav_wishlist)
        navShop = findViewById(R.id.nav_shop)
        navSearch = findViewById(R.id.nav_search)
        navProfile = findViewById(R.id.nav_profile)

        // Set listeners for navigation items
        setNavigationListeners()

        // Set listeners for options
        wishListOption?.setOnClickListener {
            Toast.makeText(this, "Wishlist clicked", Toast.LENGTH_SHORT).show()
        }
        logoutOption?.setOnClickListener {
            Toast.makeText(this, "Logout clicked", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setNavigationListeners() {
        navHome?.setOnClickListener {
            Toast.makeText(this, "Home clicked", Toast.LENGTH_SHORT).show()
        }
        navWishlist?.setOnClickListener {
            Toast.makeText(this, "Wishlist clicked", Toast.LENGTH_SHORT).show()
        }
        navShop?.setOnClickListener {
            Toast.makeText(this, "Shop clicked", Toast.LENGTH_SHORT).show()
        }
        navSearch?.setOnClickListener {
            Toast.makeText(this, "Search clicked", Toast.LENGTH_SHORT).show()
        }
        navProfile?.setOnClickListener {
            Toast.makeText(this, "Profile clicked", Toast.LENGTH_SHORT).show()
        }
    }
}
