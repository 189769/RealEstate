package com.example.realestatemanagementsystem.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import com.example.realestatemanagementsystem.R
import com.google.android.material.button.MaterialButton

class LoginActivity : AuthActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showForm(R.layout.activity_login)
        val email = field(R.id.email_layout)
        val password = field(R.id.password_layout)
        fun submit() {
            val validEmail = validateEmail(email)
            val validPassword = validatePassword(password)
            if (validEmail && validPassword) showBackendNotice()
            else focusFirstError(email, password)
        }
        findViewById<MaterialButton>(R.id.login_button).setOnClickListener { submit() }
        password.editText?.setOnEditorActionListener { _, action, _ ->
            if (action == EditorInfo.IME_ACTION_DONE) { submit(); true } else false
        }
        findViewById<MaterialButton>(R.id.register_link).setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}
