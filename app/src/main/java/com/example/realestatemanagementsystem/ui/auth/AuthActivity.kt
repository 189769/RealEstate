package com.example.realestatemanagementsystem.ui.auth

import android.util.Patterns
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doAfterTextChanged
import com.example.realestatemanagementsystem.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout

/** Shared UI validation only; this does not authenticate or persist credentials. */
abstract class AuthActivity : AppCompatActivity() {
    protected fun showForm(layout: Int) {
        enableEdgeToEdge()
        setContentView(layout)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.auth_root)) { view, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val keyboard = insets.getInsets(WindowInsetsCompat.Type.ime())
            view.setPadding(bars.left, bars.top, bars.right, maxOf(bars.bottom, keyboard.bottom))
            insets
        }
    }

    protected fun field(id: Int): TextInputLayout = findViewById<TextInputLayout>(id).also { field ->
        field.editText?.doAfterTextChanged { field.error = null }
    }

    protected fun value(field: TextInputLayout): String = field.editText?.text?.toString().orEmpty()

    protected fun validateEmail(field: TextInputLayout): Boolean {
        val email = value(field).trim()
        field.error = when {
            email.isEmpty() -> getString(R.string.error_required)
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> getString(R.string.error_email)
            else -> null
        }
        return field.error == null
    }

    protected fun validatePassword(field: TextInputLayout): Boolean {
        val password = value(field)
        field.error = when {
            password.isBlank() -> getString(R.string.error_required)
            password.length < 8 -> getString(R.string.error_password)
            else -> null
        }
        return field.error == null
    }

    protected fun focusFirstError(vararg fields: TextInputLayout) {
        fields.firstOrNull { it.error != null }?.editText?.requestFocus()
    }

    protected fun showBackendNotice() {
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.validation_complete)
            .setMessage(R.string.backend_notice)
            .setPositiveButton(android.R.string.ok, null)
            .show()
    }
}
