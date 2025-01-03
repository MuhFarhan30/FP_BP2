package com.example.fp_bp2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "onCreate started")
        setContentView(R.layout.section1)

        safeFindViewById<View>(R.id.nav_wishlist)?.setOnClickListener {
            navigateTo(WishlistActivity::class.java)
        }

        val btnHome = findViewById<ImageView>(R.id.nav_home)
        btnHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Navigasi ke ProfileActivity
        setupNavigation(R.id.nav_profile, ProfileActivity::class.java)

        enableEdgeToEdge()
        setupEdgeToEdgePadding()
        setupNavigation(R.id.imgMenuItem1, ActivityProductDetails::class.java)
        setupNavigation(R.id.nav_wishlist, WishlistActivity::class.java)
    }

    private fun setupEdgeToEdgePadding() {
        val mainView = safeFindViewById<View>(R.id.main)
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView) { view, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
            Log.d("MainActivity", "Edge-to-edge padding applied.")
        } else {
            Log.e("MainActivity", "Main view not found. Please ensure the layout includes a view with ID 'main'.")
        }
    }

    private fun setupNavigation(viewId: Int, activityClass: Class<*>) {
        val view = safeFindViewById<View>(viewId)
        if (view != null) {
            view.setOnClickListener {
                navigateTo(activityClass)
            }
            Log.d("MainActivity", "Navigation setup for viewId: $viewId to ${activityClass.simpleName}")
        } else {
            Log.e("MainActivity", "View with ID $viewId not found. Please ensure it exists in the layout.")
        }
    }

    private fun navigateTo(activityClass: Class<*>) {
        try {
            val intent = Intent(this, activityClass)
            startActivity(intent)
            Log.d("MainActivity", "Navigating to ${activityClass.simpleName}")
        } catch (e: Exception) {
            Log.e("MainActivity", "Failed to navigate to ${activityClass.simpleName}", e)
        }
    }

    private fun <T : View> safeFindViewById(viewId: Int): T? {
        return try {
            findViewById(viewId)
        } catch (e: Exception) {
            Log.e("MainActivity", "Failed to find view with ID: $viewId", e)
            null
        }
    }
}
