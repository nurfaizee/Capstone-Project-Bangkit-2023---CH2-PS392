package com.dicoding.bottomnavigationbar.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "login_data")

class LoginManager(context: Context) {

    private val dataStore = context.dataStore

    suspend fun saveLoginData(email: String, accessToken: String,username:String) {
        dataStore.edit { preferences ->
            preferences[STRING_KEY_EMAIL] = email
            preferences[STRING_KEY_USERNAME] = username
            preferences[STRING_KEY_ACCESS_TOKEN] = accessToken
        }
    }

    suspend fun clearLoginData() {
        dataStore.edit { preferences ->
            preferences.remove(STRING_KEY_EMAIL)
            preferences.remove(STRING_KEY_USERNAME)
            preferences.remove(STRING_KEY_ACCESS_TOKEN)
        }
    }

    val emailFlow: Flow<String?> = dataStore.data.map { preferences ->
        preferences[STRING_KEY_EMAIL]
    }
    val usernameFlow: Flow<String?> = dataStore.data.map { preferences ->
        preferences[STRING_KEY_USERNAME]
    }
    val accessTokenFlow: Flow<String?> = dataStore.data.map { preferences ->
        preferences[STRING_KEY_ACCESS_TOKEN]
    }

    val isUserLoggedIn: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[STRING_KEY_EMAIL] != null && preferences[STRING_KEY_ACCESS_TOKEN] != null
    }

    companion object {

        private val STRING_KEY_USERNAME = stringPreferencesKey("username")
        private val STRING_KEY_EMAIL = stringPreferencesKey("email")
        private val STRING_KEY_ACCESS_TOKEN = stringPreferencesKey("accessToken")
    }
}
