package com.example.skillflow.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skillflow.core.utils.EmailValidator
import com.example.skillflow.data.repository.AppRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.UnknownHostException

sealed class LoginUIState {

    object Standard : LoginUIState()
    object Loading : LoginUIState()
    object Success : LoginUIState()
    data class Error(val message: String) : LoginUIState()
}

class LoginViewModel(
    private val repository: AppRepository
) : ViewModel() {

    private val _email = MutableStateFlow("")
    private val _password = MutableStateFlow("")

    private val _uiState = MutableStateFlow<LoginUIState>(LoginUIState.Standard)
    val uiState: StateFlow<LoginUIState> = _uiState.asStateFlow()

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
            _uiState.value = LoginUIState.Loading

            val result = repository.loadCourses()

            if (result.isSuccess) {
                _uiState.value = LoginUIState.Success
                onSuccess()
            } else {
                result.exceptionOrNull()?.let { e ->
                    val errorMessage = when (e) {
                        is UnknownHostException -> "Нет подключения к интернету"
                        is HttpException -> "Ошибка сервера: ${e.code()}"
                        else -> e.localizedMessage ?: "Неизвестная ошибка"
                    }
                    _uiState.value = LoginUIState.Error(errorMessage)
                }
            }
        }
    }
}