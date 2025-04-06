package org.gulaii.app.ui.composables

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PillButtonTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun pillButton_clickAction_isTriggered() {
        var clicked = false

        composeTestRule.setContent {
            PillButton(
                isEnabled = true,
                buttonText = "Click Me",
                clickAction = { clicked = true }
            )
        }

        // Ищем кнопку по тексту и кликаем
        composeTestRule.onNodeWithText("Click Me").performClick()

        // Проверяем, что clickAction был вызван
        assert(clicked)
    }
}
