package org.gulaii.app.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ProfileMetrics(
  val height: Int?  = null,
  val weight: Int?  = null,
  val goal: String? = null,
  val activityLevel: String? = null
)
