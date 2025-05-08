package org.gulaii.app.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
  val token: String,
  val message: String,
  @SerialName("correlationId") val correlationId: String
)
