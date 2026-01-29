package com.example.client.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.client.utils.ConnectionState
import com.example.client.utils.observeConnectivityAsFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.map

class ConnectivityViewModel(context: Context) : ViewModel() {
    val connectionState: StateFlow<ConnectionState> = context.observeConnectivityAsFlow()
        .stateIn(viewModelScope, kotlinx.coroutines.flow.SharingStarted.Eagerly, ConnectionState.Unavailable)
}

class ConnectivityViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ConnectivityViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ConnectivityViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}