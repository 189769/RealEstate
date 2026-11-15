
package com.example.tenantmanagementsystem.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tenantmanagementsystem.databinding.ActivityRegisterBinding
import java.security.SecureRandom
import java.security.MessageDigest

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.registerButton.setOnClickListener {

            val fullName = binding.fullNameEditText.text.toString().trim()
            val email = binding.registerEmailEditText.text.toString().trim()
            val password = binding.registerPasswordEditText.text.toString()

            if (fullName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Enter a valid email address", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.length < 8) {
                Toast.makeText(this, "Password must have at least 8 characters", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val salt = ByteArray(16)
            SecureRandom().nextBytes(salt)

            val hash = MessageDigest.getInstance("SHA-256")
                .digest(salt + password.toByteArray())
                .joinToString("") { "%02x".format(it) }

            val preferences = getSharedPreferences("UserAccount", MODE_PRIVATE)

            preferences.edit()
                .putString("FULL_NAME", fullName)
                .putString("EMAIL", email)
                .putString("PASSWORD_SALT", salt.joinToString("") { "%02x".format(it) })
                .putString("PASSWORD_HASH", hash)
                .apply()

            Toast.makeText(this, "Account created. Please log in.", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, LoginActivity::class.java)
            intent.putExtra("EMAIL", email)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }

        binding.loginLinkTextView.setOnClickListener {
            finish()
        }
    }
}
