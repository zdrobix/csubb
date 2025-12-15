package com.example.client.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.client.datastore.SessionManager
import com.example.client.model.LoginRequest
import com.example.client.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val sessionManager = SessionManager(getApplication())
    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            try {
                val response = RetrofitClient.instance.login(LoginRequest(username, password))
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    if (loginResponse?.token != null && loginResponse.userId != null) {
                        RetrofitClient.authInterceptor.setToken(loginResponse.token)
                        RetrofitClient.authInterceptor.setUserId(Integer.parseInt(loginResponse.userId))
                        sessionManager.saveSession(loginResponse.token, loginResponse.userId)
                        _loginState.value = LoginState.Success(loginResponse.token, loginResponse.userId)
                    } else {
                        _loginState.value = LoginState.Error("Login failed: No token or user ID received")
                    }
                } else {
                    _loginState.value = LoginState.Error("Login failed: ${response.message()}")
                }
            } catch (e: Exception) {
                _loginState.value = LoginState.Error("Login failed: ${e.message}")
            }
        }
    }
}

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val token: String, val userId: String) : LoginState()
    data class Error(val message: String) : LoginState()
}