package org.gulaii.app.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeParseException


@Composable
fun DateTimeSection(
  dateTime: LocalDateTime,
  onDateChange: (LocalDateTime) -> Unit
) {
  var isEditing by remember { mutableStateOf(false) }
  var editedDateTime by remember { mutableStateOf(dateTime) }

  val toggleEditing = {
    isEditing = !isEditing
    if (!isEditing) {
      onDateChange(editedDateTime)
    }
  }

  fun formatDate(date: String): String {
    val parts = date.split("-")
    return if (parts.size == 3) {
      val month = parts[1].padStart(2, '0')
      val day = parts[2].padStart(2, '0')
      "${parts[0]}-$month-$day"
    } else {
      date
    }
  }

  fun formatTime(time: String): String {
    val parts = time.split(":")
    return if (parts.size == 2) {
      val hour = parts[0].padStart(2, '0')
      val minute = parts[1].padStart(2, '0')
      "$hour:$minute"
    } else {
      time
    }
  }

  OutlinedCard(
    modifier = Modifier
      .fillMaxWidth()
      .clickable { if (isEditing) toggleEditing() }
  ) {
    Row(
      Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Column(Modifier.weight(1f)) {
        CustomTextField(
          label = "Дата",
          value = editedDateTime.toLocalDate().toString(),
          onValueChange = { newValue ->
            try {
              val formattedDate = formatDate(newValue)
              val newDate = LocalDate.parse(formattedDate)
              editedDateTime = LocalDateTime.of(newDate, editedDateTime.toLocalTime())
            } catch (_: DateTimeParseException) {
            }
          },
          enabled = isEditing,
          modifier = Modifier.fillMaxWidth()
        )

        CustomTextField(
          label = "Время",
          value = editedDateTime.toLocalTime().withSecond(0).withNano(0).toString(),
          onValueChange = { newTime ->
            try {
              val formattedTime = formatTime(newTime)
              val newLocalTime = LocalTime.parse(formattedTime)
              editedDateTime = LocalDateTime.of(editedDateTime.toLocalDate(), newLocalTime)
            } catch (_: DateTimeParseException) {
            }
          },
          enabled = isEditing,
          modifier = Modifier.fillMaxWidth()
        )
      }

      Icon(
        imageVector = if (isEditing) Icons.Filled.Check else Icons.Filled.Edit,
        contentDescription = if (isEditing) "Сохранить" else "Редактировать",
        modifier = Modifier
          .clickable { toggleEditing() }
          .padding(start = 16.dp)
          .size(24.dp)
      )
    }
  }
}
