package com.burraco.app.ui.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BurracoButton(
    modifier: Modifier = Modifier,
    text: String = "",
    enabled: Boolean = true,
    large: Boolean = false,
    onClick: () -> Unit,
    content: @Composable () -> Unit = {},
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    // 1. Definisci l'animazione della scala
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f, // Rimpicciolisce al 95% quando premuto
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy, // Controlla il rimbalzo
            stiffness = Spring.StiffnessLow // Controlla la velocità della "molla"
        ),
        label = "BouncyButtonScale"
    )
    Box(
        modifier = modifier
    ) {
        Button(
            modifier = Modifier.graphicsLayer(
                scaleX = scale,
                scaleY = scale
            ),
            onClick = onClick,
            contentPadding = if (large) PaddingValues(
                horizontal = 22.dp,
                vertical = 8.dp
            ) else PaddingValues(
                horizontal = 17.dp,
                vertical = 5.dp
            ),
            shape = RoundedCornerShape(30.dp),
            interactionSource = interactionSource,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isPressed) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary,
                contentColor = if (isPressed) MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.onPrimary
            ),
            enabled = enabled
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (text.isNotBlank()) {
                    Text(
                        text = text,
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = if (large) {
                            30.sp
                        } else {
                            MaterialTheme.typography.bodyMedium.fontSize
                        },
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
                content()
            }
        }
    }
}