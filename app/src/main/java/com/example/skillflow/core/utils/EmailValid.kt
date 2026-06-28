package com.example.skillflow.core.utils

import android.util.Patterns

object EmailValidator {

    fun isValid(email: String): Boolean {

        if (email.isBlank()) return false
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) return true

        return email.matches(
            Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z]{2,}$")
        )
    }
}