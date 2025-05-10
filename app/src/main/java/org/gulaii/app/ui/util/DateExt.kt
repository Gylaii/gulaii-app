package org.gulaii.app.ui.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun LocalDate.dateLabel(): String = when (this) {
  LocalDate.now() -> "Сегодня"
  LocalDate.now().minusDays(1) -> "Вчера"
  else -> format(DateTimeFormatter.ofPattern("dd.MM.yy"))
}
