package com.burraco.app.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.burraco.app.ui.theme.BurracoRed
import kotlinx.coroutines.delay

@Composable
fun TemporaryMessage(
    modifier: Modifier = Modifier,
    message: String,
    visibility: Boolean,
    resetVisibility: ()->Unit,
) {

    var visible by remember { mutableStateOf(false) }

    if(visibility) {
        LaunchedEffect(Unit) {
            visible = true
            delay(2000)
            visible = false
            delay(500)
            resetVisibility()
        }
    }

    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(durationMillis = 1000)
    )

        Text(
            text = message,
            color = BurracoRed,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 10.dp).graphicsLayer(alpha = alpha)
        )
}