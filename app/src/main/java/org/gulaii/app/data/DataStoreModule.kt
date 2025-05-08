package org.gulaii.app.data

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.core.DataStore

private const val DATASTORE_NAME = "user_prefs"

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
  name = DATASTORE_NAME
)
