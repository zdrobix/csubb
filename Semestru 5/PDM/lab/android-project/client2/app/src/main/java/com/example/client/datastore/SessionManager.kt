package com.example.client.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

class SessionManager(context: Context) {

    private val dataStore = context.dataStore

    companion object {
        private val TOKEN_KEY = stringPreferencesKey("jwt_token")
        private val USER_ID_KEY = stringPreferencesKey("user_id")
    }

    suspend fun saveSession(token: String, userId: String) {
        dataStore.edit {
            it[TOKEN_KEY] = token
            it[USER_ID_KEY] = userId
        }
    }

    fun getSession(): Flow<Pair<String?, String?>> {
        return dataStore.data.map {
            Pair(
                it[TOKEN_KEY],
                it[USER_ID_KEY]
            )
        }
    }

    suspend fun clearSession() {
        dataStore.edit {
            it.clear()
        }
    }
}