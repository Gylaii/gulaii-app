package org.gulaii.app.network

import org.gulaii.app.data.model.AuthResponse
import org.gulaii.app.data.model.ProfileMetrics
import retrofit2.http.*

interface ApiService {

  // ------------ AUTH ------------
  @POST("api/auth/register")
  suspend fun register(@Body body: Map<String, String>): AuthResponse

  @POST("api/auth/login")
  suspend fun login(@Body body: Map<String, String>): AuthResponse

  // ------------ PROFILE ------------
  @GET("api/user/profile/metrics")
  suspend fun getMetrics(): ProfileMetrics

  @PUT("api/user/profile/metrics")
  suspend fun setMetrics(@Body metrics: ProfileMetrics): ProfileMetrics
}
