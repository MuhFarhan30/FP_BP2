package com.example.fp_bp2

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ForgotPasswordActivity : AppCompatActivity() {
    private var emailField: EditText? = null
    private var phoneNumberField: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgotpassword)

        val backButton = findViewById<ImageButton>(R.id.backButton)
        emailField = findViewById<EditText>(R.id.emailField)
        phoneNumberField = findViewById<EditText>(R.id.phoneNumberField)
        val backToLogin = findViewById<TextView>(R.id.backToLogin)
        val confirmButton = findViewById<Button>(R.id.confirmButton)

        backButton.setOnClickListener { v: View? -> finish() }
        backToLogin.setOnClickListener { v: View? -> finish() }
        confirmButton.setOnClickListener { v: View? -> handleForgotPassword() }
    }

    private fun handleForgotPassword() {
        val email = emailField!!.text.toString().trim { it <= ' ' }
        val phoneNumber = phoneNumberField!!.text.toString().trim { it <= ' ' }

        if (email.isEmpty() && phoneNumber.isEmpty()) {
            Toast.makeText(this, "Please enter email or phone number", Toast.LENGTH_SHORT).show()
            return
        }

        Toast.makeText(this, "Password reset instructions sent!", Toast.LENGTH_SHORT).show()
    }
}