package com.burraco.app.ui.util

import android.content.res.Configuration
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Modifier.adjustedModifier(): Modifier {
    val configuration = LocalConfiguration.current
    return if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
        this.navigationBarsPadding()
    else
        this.padding(WindowInsets.displayCutout.asPaddingValues()).navigationBarsPadding()
}

@Composable
fun Modifier.absoluteSafeOffsetY(offset: Offset): Modifier {
    val y: Dp = with(LocalDensity.current) { offset.y.toDp() } + 30.dp
    val max = (LocalConfiguration.current.screenHeightDp.dp * 3)/4
    return if(y>max) this.absoluteOffset(y=max) else this.absoluteOffset(y=y)
}

//extension function -> estende una classe senza modificare direttamente il codice
fun Modifier.blurIfSupported(radius: Dp): Modifier {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        this.blur(radius)
    } else {
        this // fallback: nessun blur
    }
}

fun Modifier.fakeBlur(): Modifier {
    return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
        this.background(Color(0x9E5B5858)) // grigio semi-trasparente
    } else {
        this // fallback: nessun blur
    }
}

@Composable
fun Modifier.adaptiveNavigationBarPadding(thresholdDp: Int = 25): Modifier {
    val bottomPaddingDp = WindowInsets.navigationBars
        .asPaddingValues()
        .calculateBottomPadding() // <-- già Dp, non serve conversione

    val sides = if (bottomPaddingDp > thresholdDp.dp) {
        // Con navigation bar "alta"
        WindowInsetsSides.Top + WindowInsetsSides.Start + WindowInsetsSides.End + WindowInsetsSides.Bottom
    } else {
        // Con gesture bar "bassa"
        WindowInsetsSides.Top + WindowInsetsSides.Start + WindowInsetsSides.End
    }

    return this.windowInsetsPadding(
        WindowInsets.systemBars.only(sides)
    )
}