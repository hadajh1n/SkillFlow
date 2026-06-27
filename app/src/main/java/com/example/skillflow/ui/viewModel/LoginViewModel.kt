package com.example.skillflow.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skillflow.core.utils.EmailValidator
import com.example.skillflow.data.repository.AppRepository
import com.example.skillflow.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

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

    fun login(onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.courseApi.getCourses()

                AppRepository.setCourse(response.courses)
                onSuccess()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}