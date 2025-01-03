package com.example.fp_bp2

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var emailField: EditText
    private lateinit var passwordField: EditText
    private lateinit var loginButton: Button
    private lateinit var forgotPassword: TextView
    private lateinit var signUpLink: TextView
    private lateinit var toggleVisibility: ImageView
    private lateinit var progressBar: ProgressBar
    private var isPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        emailField = findViewById(R.id.emailField)
        passwordField = findViewById(R.id.passwordField)
        loginButton = findViewById(R.id.loginButton)
        forgotPassword = findViewById(R.id.forgotPassword)
        signUpLink = findViewById(R.id.signUpLink)
        toggleVisibility = findViewById(R.id.toggleVisibility)
        progressBar = findViewById(R.id.progressBar)

        fun isValidCredentials(email: String, password: String): Boolean {
            if (email == "@user" && password == "user123") return true

            val emailContainsNumber = email.any { it.isDigit() }
            val emailContainsAt = email.contains("@")
            return emailContainsNumber && emailContainsAt && password.length >= 6
        }


        toggleVisibility.setOnClickListener {
            if (isPasswordVisible) {
                passwordField.transformationMethod = PasswordTransformationMethod.getInstance()
            } else {
                passwordField.transformationMethod = HideReturnsTransformationMethod.getInstance()
            }
            isPasswordVisible = !isPasswordVisible
            passwordField.setSelection(passwordField.text.length)
        }

        loginButton.setOnClickListener {
            val email = emailField.text.toString().trim()
            val password = passwordField.text.toString().trim()

            Log.d("LoginValidation", "Email: $email, Password: $password")

            if (email.isEmpty()) {
                emailField.error = "Email is required"
                emailField.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                passwordField.error = "Password is required"
                passwordField.requestFocus()
                return@setOnClickListener
            }

            progressBar.visibility = View.VISIBLE

            Handler(Looper.getMainLooper()).postDelayed({
                progressBar.visibility = View.GONE
                if (isValidCredentials(email, password)) {
                    Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show()
                }
            }, 2000)
        }

        forgotPassword.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }

        signUpLink.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }
}
