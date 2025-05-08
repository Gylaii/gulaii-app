package org.gulaii.app.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.gulaii.app.ui.theme.GulaiiTheme

@Composable
fun OtpInputTextField(
  otpLength: Int,
  onUpdateOtpValuesByIndex: (Int, String) -> Unit,
  onOtpInputComplete: () -> Unit,
  modifier: Modifier = Modifier,
  otpValues: List<String> = List(otpLength) { "" },
  isError: Boolean = false,
) {
  val focusRequesters = List(otpLength) { FocusRequester() }
  val focusManager = LocalFocusManager.current
  val keyboardController = LocalSoftwareKeyboardController.current

  Row(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 8.dp),
    horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally)
  ) {
    otpValues.forEachIndexed { index, value ->
      OutlinedTextField(
        modifier = Modifier
          .weight(1f)
          .padding(6.dp)
          .focusRequester(focusRequesters[index])
          .onKeyEvent { keyEvent ->
            if (keyEvent.key == Key.Backspace) {
              if (otpValues[index].isEmpty() && index > 0) {
                onUpdateOtpValuesByIndex(index, "")
                focusRequesters[index - 1].requestFocus()
              } else {
                onUpdateOtpValuesByIndex(index, "")
              }
              true
            } else {
              false
            }
          },
        value = value,
        onValueChange = { newValue ->
          if (newValue.length == otpLength) {
            for (i in otpValues.indices) {
              onUpdateOtpValuesByIndex(
                i,
                if (i < newValue.length && newValue[i].isDigit()) newValue[i].toString() else ""
              )
            }

            keyboardController?.hide()
            onOtpInputComplete()
          } else if (newValue.length <= 1) {
            onUpdateOtpValuesByIndex(index, newValue)
            if (newValue.isNotEmpty()) {
              if (index < otpLength - 1) {
                focusRequesters[index + 1].requestFocus()
              } else {
                keyboardController?.hide()
                focusManager.clearFocus()
                onOtpInputComplete()
              }
            }
          } else {
            if (index < otpLength - 1) focusRequesters[index + 1].requestFocus()
          }
        },
        keyboardOptions = KeyboardOptions(
          keyboardType = KeyboardType.Number,
          imeAction = if (index == otpLength - 1) ImeAction.Done else ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
          onNext = {
            if (index < otpLength - 1) {
              focusRequesters[index + 1].requestFocus()
            }
          },
          onDone = {
            keyboardController?.hide()
            focusManager.clearFocus()
            onOtpInputComplete()
          }
        ),
        shape = MaterialTheme.shapes.small,
        isError = isError,
        textStyle = TextStyle(
          textAlign = TextAlign.Center,
          fontSize = MaterialTheme.typography.bodyLarge.fontSize
        )
      )

      LaunchedEffect(value) {
        if (otpValues.all { it.isNotEmpty() }) {
          focusManager.clearFocus()
          onOtpInputComplete()
        }
      }
    }
  }

  LaunchedEffect(Unit) {
    focusRequesters.first().requestFocus()
  }
}


@Composable
@Preview
fun OtpInputTextFieldPreview() {
  val otpValues =
    remember { mutableStateListOf<String>("", "", "", "") }

  GulaiiTheme {
    OtpInputTextField(
      otpValues = otpValues,
      otpLength = 4,
      onOtpInputComplete = { /* TODO: Make api calls or anything after validation */ },
      onUpdateOtpValuesByIndex = { index, value ->
        otpValues[index] = value
      })
  }
}
