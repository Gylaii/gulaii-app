package org.gulaii.app.data.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.gulaii.app.data.model.ProfileMetrics
import org.gulaii.app.network.ApiService

class UserRepository(private val api: ApiService) {

  private val _metrics = MutableStateFlow(ProfileMetrics())
  val   metrics: StateFlow<ProfileMetrics> = _metrics.asStateFlow()

  suspend fun setMetrics(m: ProfileMetrics) : ProfileMetrics {
    _metrics.value = m
    return api.setMetrics(m).also {
      _metrics.value = it
    }
  }

  suspend fun fetchMetrics(): ProfileMetrics =
    api.getMetrics().also { _metrics.value = it }
}
