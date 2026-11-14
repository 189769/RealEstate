package com.example.realestatemanagementsystem.ui.auth

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.core.widget.doAfterTextChanged
import com.example.realestatemanagementsystem.R
import com.google.android.material.button.MaterialButton

class RegisterActivity : AuthActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showForm(R.layout.activity_register)
        val name = field(R.id.name_layout)
        val email = field(R.id.email_layout)
        val password = field(R.id.password_layout)
        val confirmation = field(R.id.confirm_password_layout)
        password.editText?.doAfterTextChanged { confirmation.error = null }
        fun submit() {
            name.error = if (value(name).isBlank()) getString(R.string.error_required) else null
            val validEmail = validateEmail(email)
            val validPassword = validatePassword(password)
            confirmation.error = when {
                value(confirmation).isBlank() -> getString(R.string.error_required)
                value(confirmation) != value(password) -> getString(R.string.error_password_match)
                else -> null
            }
            if (name.error == null && validEmail && validPassword && confirmation.error == null) {
                showBackendNotice()
            } else focusFirstError(name, email, password, confirmation)
        }
        findViewById<MaterialButton>(R.id.register_button).setOnClickListener { submit() }
        confirmation.editText?.setOnEditorActionListener { _, action, _ ->
            if (action == EditorInfo.IME_ACTION_DONE) { submit(); true } else false
        }
        findViewById<MaterialButton>(R.id.login_link).setOnClickListener { finish() }
    }
}
