package com.example.fp_bp2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SignUpActivity : AppCompatActivity() {

    // Deklarasi variabel untuk UI
    private lateinit var emailField: EditText
    private lateinit var passwordField: EditText
    private lateinit var confirmPasswordField: EditText
    private lateinit var signupButton: Button
    private lateinit var backButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        // Inisialisasi elemen UI
        emailField = findViewById(R.id.emailField)
        passwordField = findViewById(R.id.passwordField)
        confirmPasswordField = findViewById(R.id.confirmPasswordField)
        signupButton = findViewById(R.id.signupButton)
        backButton = findViewById(R.id.backButton)

        // Tombol kembali
        backButton.setOnClickListener {
            finish()
        }

        // Tombol sign up
        signupButton.setOnClickListener {
            val email = emailField.text.toString().trim()
            val password = passwordField.text.toString().trim()
            val confirmPassword = confirmPasswordField.text.toString().trim()

            // Validasi input pengguna
            when {
                email.isEmpty() -> {
                    emailField.error = "Email tidak boleh kosong"
                    emailField.requestFocus()
                }
                !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    emailField.error = "Format email tidak valid"
                    emailField.requestFocus()
                }
                password.isEmpty() -> {
                    passwordField.error = "Password tidak boleh kosong"
                    passwordField.requestFocus()
                }
                password.length < 6 -> {
                    passwordField.error = "Password harus terdiri dari minimal 6 karakter"
                    passwordField.requestFocus()
                }
                confirmPassword.isEmpty() -> {
                    confirmPasswordField.error = "Konfirmasi password tidak boleh kosong"
                    confirmPasswordField.requestFocus()
                }
                password != confirmPassword -> {
                    confirmPasswordField.error = "Password tidak cocok"
                    confirmPasswordField.requestFocus()
                }
                else -> {
                    // Pendaftaran berhasil (simulasi)
                    Toast.makeText(
                        this,
                        "Pendaftaran berhasil! Email: $email",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }
            }
        }
    }
}
