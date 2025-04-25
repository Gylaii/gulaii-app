package org.gulaii.app.ui.screens.initialScreen

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import org.gulaii.app.R
import org.gulaii.app.ui.composables.PillButton
import org.gulaii.app.ui.theme.AccentBlue  // FIXME: remove later
import org.gulaii.app.ui.theme.GulaiiTheme  // FIXME: remove later
import org.gulaii.app.ui.theme.SecondaryBlue  // FIXME: remove later
import org.gulaii.app.ui.theme.SoftBlue  // FIXME: remove later

@Composable
fun InitialScreenView(
  modifier: Modifier = Modifier,
  viewModel: InitialScreenViewModel = viewModel(),
  onNext: () -> Unit = {}
) {
  val headerText by viewModel.headerText.collectAsState()
  val scaleAnim = remember { Animatable(1f) }
  val scope = rememberCoroutineScope()

  Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
    Image(
      painter = painterResource(id = R.drawable.initial_image), // переименуйте файл, как нужно
      contentDescription = null,      // декоративное изображение
      modifier = Modifier.fillMaxSize(),
      contentScale = ContentScale.Crop // растягиваем по всему экрану
    )
    Box(
      modifier = Modifier
        .padding(innerPadding)
        .fillMaxSize()
        .padding(24.dp)
    ) {
      Column(
        modifier = Modifier.align(Alignment.Center),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Text(
          text = headerText,
          style = MaterialTheme.typography.bodyLarge,
          fontSize = 80.sp,
          color = SoftBlue,
          modifier = Modifier.padding(top = 450.dp)
        )
      }

      PillButton(
        modifier = Modifier
          .align(Alignment.BottomCenter)
          .padding(bottom = 20.dp)
          .height(60.dp)
          .width(200.dp)
          .scale(scaleAnim.value),
        isEnabled = true,
        buttonColor = ButtonDefaults.buttonColors(SecondaryBlue),
        clickAction = {
          scope.launch {
            scaleAnim.animateTo(
              targetValue = 1.3f,
              animationSpec = tween(100)
            )
            scaleAnim.animateTo(
              targetValue = 1f,
              animationSpec = spring(
                dampingRatio = Spring.DampingRatioLowBouncy
              )
            )
            onNext()
          }
        }
      ) {
        Text(
          text = "Welcome!",
          color = AccentBlue,
          style = MaterialTheme.typography.bodyMedium,
          fontSize = 24.sp
        )
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
fun InitialScreenViewPreview() {
  GulaiiTheme {
    InitialScreenView()
  }
}
