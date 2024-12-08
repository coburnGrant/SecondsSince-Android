package com.example.secondssince.data

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class UserPreferencesRepository(
    private val dataStore: DataStore<Preferences>
) {
    val notifyOnSpecialDay: Flow<Boolean> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.e(TAG, "Error reading preferences.", exception)
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[NOTIFY_ON_SPECIAL_DAY_KEY] ?: false
        }

    suspend fun saveNotifyOnSpecialDayPreference(notify: Boolean) {
        dataStore.edit { preferences ->
            preferences[NOTIFY_ON_SPECIAL_DAY_KEY] = notify
        }
    }

    val notifyOnSpecialMoment: Flow<Boolean> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.e(TAG, "Error reading preferences.", exception)
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[NOTIFY_ON_SPECIAL_MOMENT_KEY] ?: false
        }

    suspend fun saveNotifyOnSpecialMomentPreference(notify: Boolean) {
        dataStore.edit { preferences ->
            preferences[NOTIFY_ON_SPECIAL_MOMENT_KEY] = notify
        }
    }

    // Function to save time in "HH:mm" format
    suspend fun saveSelectedTime(hour: Int, minute: Int) {
        val timeString = "%02d:%02d".format(hour, minute)
        dataStore.edit { preferences ->
            preferences[SELECTED_TIME_KEY] = timeString
        }
    }

    // Function to get the saved time, default to 8:00
    fun getSelectedTime(): Flow<Pair<Int, Int>> {
        return dataStore.data
            .map { preferences ->
                val timeString = preferences[SELECTED_TIME_KEY] ?: "08:00"
                val (hour, minute) = timeString.split(":").map { it.toInt() }
                Pair(hour, minute)
            }
    }

    private companion object {
        val NOTIFY_ON_SPECIAL_DAY_KEY = booleanPreferencesKey("notify_on_special_day")
        val NOTIFY_ON_SPECIAL_MOMENT_KEY = booleanPreferencesKey("notify_on_special_moment")
        val SELECTED_TIME_KEY = stringPreferencesKey("selected_notify_time")

        //        val SPECIAL_DAY_NOTIFY_TIME =
        const val TAG = "UserPreferencesRepo"
    }
}