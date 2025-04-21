package org.gulaii.app.ui.screens.onboardingScreen

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import org.gulaii.app.R
import org.gulaii.app.ui.composables.PillButton
import org.gulaii.app.ui.theme.AccentBlue
import org.gulaii.app.ui.theme.GulaiiTheme
import org.gulaii.app.ui.theme.PrimaryBlue
import org.gulaii.app.ui.theme.SecondaryBlue

@SuppressLint("ResourceAsColor")
@Composable
fun OnboardingView(
  modifier: Modifier = Modifier,
  viewModel: OnboardingViewModel = viewModel(),
  onFinished: () -> Unit = {}
) {
  val page by viewModel.currentPage.collectAsState()
  val scaleAnim = remember { Animatable(1f) }
  val scope = rememberCoroutineScope()


  Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
    Image(
      painter = painterResource(id = R.drawable.onboarding_image),
      contentDescription = "background",
      modifier = Modifier.fillMaxSize(),
      contentScale = ContentScale.Crop,
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
        AnimatedContent(
          targetState = page.header,
          transitionSpec = {
            (slideInHorizontally { -it } + fadeIn(tween(700))) togetherWith
              (slideOutHorizontally { -it } + fadeOut(tween(500)))
          },
          label = "header"
        ) { header ->
          Text(
            text = header,
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 26.sp,
            color = AccentBlue,
            modifier = Modifier.padding(top = 300.dp)
          )
        }

        Spacer(Modifier.height(20.dp))


        AnimatedContent(
          targetState = page.body,
          transitionSpec = {
            (slideInHorizontally { it } + fadeIn(tween(900))) togetherWith
              (slideOutHorizontally { it } + fadeOut(tween(900)))
          },
          label = "body"
        ) { body ->
          Text(
            text = body,
            style = MaterialTheme.typography.bodyMedium.copy(
              textIndent = TextIndent(firstLine = 22.sp)
            ),
            fontSize = 20.sp,
            color = PrimaryBlue

          )
        }
      }

      PillButton(
        modifier = Modifier
          .align(Alignment.BottomEnd)
          .padding(bottom = 20.dp, end = 30.dp)
          .height(60.dp)
          .width(60.dp)
          .scale(scaleAnim.value),
        isEnabled = true,
        buttonColor = ButtonDefaults.buttonColors(SecondaryBlue),
        clickAction = {
          scope.launch {
            scaleAnim.animateTo(
              targetValue = 1.5f,
              animationSpec = tween(100)
            )
            scaleAnim.animateTo(
              targetValue = 1f,
              animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy
              )
            )
          }
          val finished = viewModel.onButtonClick()
          if (finished) onFinished()
        }
      ) {
        Icon(
          painter = painterResource(R.drawable.arrow_forward),
          contentDescription = stringResource(id = R.string.arrow_forward_description),
          modifier = Modifier.scale(2.5F),
          tint = AccentBlue
        )
      }
    }
  }
}

@Preview
@Composable
fun OnboardingViewPreview() {
  GulaiiTheme {
    OnboardingView()
  }
}
