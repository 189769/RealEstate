
package com.example.tenantmanagementsystem.ui.auth

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tenantmanagementsystem.databinding.ActivityLoginBinding
import com.example.tenantmanagementsystem.ui.main.MainActivity
import java.security.MessageDigest

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Pre-fill email after registration
        val registeredEmail = intent.getStringExtra("EMAIL")
        if (registeredEmail != null) {
            binding.emailEditText.setText(registeredEmail)
        }

        binding.loginButton.setOnClickListener {

            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                    this,
                    "Please enter your email and password",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val preferences =
                getSharedPreferences("UserAccount", MODE_PRIVATE)

            val savedEmail = preferences.getString("EMAIL", null)
            val savedSalt = preferences.getString("PASSWORD_SALT", null)
            val savedHash = preferences.getString("PASSWORD_HASH", null)

            if (savedEmail == null ||
                savedSalt == null ||
                savedHash == null
            ) {
                Toast.makeText(
                    this,
                    "No account found. Please register first.",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val salt = try {
                savedSalt.chunked(2)
                    .map { it.toInt(16).toByte() }
                    .toByteArray()
            } catch (e: IllegalArgumentException) {
                Toast.makeText(
                    this,
                    "Account data error. Please register again.",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val enteredHash = MessageDigest.getInstance("SHA-256")
                .digest(salt + password.toByteArray())
                .joinToString("") { "%02x".format(it) }

            if (email.equals(savedEmail, ignoreCase = true) &&
                MessageDigest.isEqual(
                    enteredHash.toByteArray(),
                    savedHash.toByteArray()
                )
            ) {
                val mainIntent =
                    Intent(this, MainActivity::class.java)

                mainIntent.putExtra("EMAIL", email)
                startActivity(mainIntent)
                finish()

            } else {
                Toast.makeText(
                    this,
                    "Invalid email or password",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.registerTextView.setOnClickListener {
            startActivity(
                Intent(this, RegisterActivity::class.java)
            )
        }

        binding.helpTextView.setOnClickListener {
            val helpIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.strathmore.edu")
            )
            startActivity(helpIntent)
        }
    }
}
