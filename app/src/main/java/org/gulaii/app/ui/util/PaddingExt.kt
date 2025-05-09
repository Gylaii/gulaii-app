package org.gulaii.app.ui.util

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.LayoutDirection.Ltr

operator fun PaddingValues.plus(other: PaddingValues): PaddingValues {
  return PaddingValues(
    start  = calculateLeftPadding(Ltr)   + other.calculateLeftPadding(Ltr),
    top    = calculateTopPadding()       + other.calculateTopPadding(),
    end    = calculateRightPadding(Ltr)  + other.calculateRightPadding(Ltr),
    bottom = calculateBottomPadding()    + other.calculateBottomPadding()
  )
}
