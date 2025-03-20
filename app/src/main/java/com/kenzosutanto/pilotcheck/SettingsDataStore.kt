package com.kenzosutanto.pilotcheck

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "settings")

class SettingsDataStore(context: Context) {
    private val dataStore = context.dataStore

    companion object {
        val IS_IMPERIAL = booleanPreferencesKey("is_imperial")
    }

    val isImperial: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[IS_IMPERIAL] ?: false  // Default to Metric
    }

    suspend fun setUnitSystem(isImperial: Boolean) {
        dataStore.edit { settings ->
            settings[IS_IMPERIAL] = isImperial
        }
    }
}
