package org.gulaii.app.di

import android.annotation.SuppressLint
import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import org.gulaii.app.data.repository.AuthRepository
import org.gulaii.app.data.repository.UserRepository
import org.gulaii.app.network.ApiService
import org.gulaii.app.data.dataStore  // ваш DataStore-расширение из DataStoreModule

object ServiceLocator {

  private const val TOKEN_KEY = "jwt"

  private lateinit var api: ApiService
  @SuppressLint("StaticFieldLeak")
  private lateinit var authRepo: AuthRepository
  private lateinit var userRepo: UserRepository

  /** Вызывается один раз в MainActivity.onCreate() */
  @OptIn(ExperimentalSerializationApi::class)
  fun init(appContext: Context) {
    if (this::api.isInitialized) return

    // 1) Логгер HTTP
    val logging = HttpLoggingInterceptor().apply {
      level = HttpLoggingInterceptor.Level.BASIC
    }

    // 2) Интерсептор для добавления JWT в заголовок
    val authInterceptor = Interceptor { chain ->
      val token = authRepo.cachedToken        // быстрый доступ
        ?: runBlocking { authRepo.currentToken() } // крайний случай
      val req = chain.request().newBuilder().apply {
        if (!token.isNullOrBlank()) header("Authorization", "Bearer $token")
      }.build()
      chain.proceed(req)
    }

    // 3) Собираем OkHttpClient
    val client = OkHttpClient.Builder()
      .addInterceptor(logging)
      .addInterceptor(authInterceptor)
      .build()

    // 4) Retrofit с Kotlinx-Serialization
    val json = Json { ignoreUnknownKeys = true }
    val retrofit = Retrofit.Builder()
      .baseUrl("http://10.0.2.2:8080/")
      .client(client)
      .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
      .build()

    api      = retrofit.create(ApiService::class.java)
    authRepo = AuthRepository(api, appContext)
    userRepo = UserRepository(api)
  }

  fun authRepo() = authRepo
  fun userRepo() = userRepo
}
