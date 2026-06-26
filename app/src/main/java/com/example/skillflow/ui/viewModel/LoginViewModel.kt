package com.example.skillflow.ui.viewModel

import androidx.lifecycle.ViewModel
import com.example.skillflow.core.utils.EmailValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel : ViewModel() {

    private val _email = MutableStateFlow("")
    private val _password = MutableStateFlow("")

    private val _isLoginEnabled = MutableStateFlow(false)
    val isLoginEnabled: StateFlow<Boolean> = _isLoginEnabled.asStateFlow()

    fun onEmailChanged(email: String) {
        _email.value = email
        updateLoginButtonState()
    }

    fun onPasswordChanged(password: String) {
        _password.value = password
        updateLoginButtonState()
    }

    private fun updateLoginButtonState() {
        val isEmailValid = EmailValidator.isValid(_email.value)
        val isPasswordValid = _password.value.isNotBlank()

        _isLoginEnabled.value = isEmailValid && isPasswordValid
    }

    fun login() {

    }
}