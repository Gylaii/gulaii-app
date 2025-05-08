package org.gulaii.app.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import org.gulaii.app.network.ApiService
import org.gulaii.app.data.dataStore

class AuthRepository(
  private val api: ApiService,
  private val context: Context
) {
  private val TOKEN = stringPreferencesKey("jwt")

  /** Токен сразу после логина/регистрации */
  @Volatile var cachedToken: String? = null
    private set

  suspend fun register(email: String, pass: String, name: String): String {
    val token = api.register(mapOf(
      "email" to email, "password" to pass, "name" to name
    )).token
    saveToken(token); return token
  }

  suspend fun login(email: String, pass: String): String {
    val token = api.login(mapOf("email" to email, "password" to pass)).token
    saveToken(token); return token
  }

  suspend fun currentToken(): String? {
    cachedToken?.let { return it }
    val ds = context.dataStore.data.map { it[TOKEN] }.first()
    cachedToken = ds
    return ds
  }

  private suspend fun saveToken(token: String) {
    cachedToken = token
    context.dataStore.edit { it[TOKEN] = token }
  }
}
