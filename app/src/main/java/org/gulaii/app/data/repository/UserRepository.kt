package org.gulaii.app.data.repository

import org.gulaii.app.data.model.ProfileMetrics
import org.gulaii.app.network.ApiService

class UserRepository(private val api: ApiService) {
  suspend fun getMetrics()    = api.getMetrics()
  suspend fun setMetrics(m: ProfileMetrics) = api.setMetrics(m)
}
