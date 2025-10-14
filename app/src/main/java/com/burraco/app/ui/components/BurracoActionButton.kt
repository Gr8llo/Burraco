package com.burraco.app.ui.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.FloatingActionButtonElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BurracoActionButton(
    modifier: Modifier = Modifier,
    text: String = "",
    elevation: Dp? = null,
    onClick: () -> Unit,
    icon: @Composable () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    FloatingActionButton(
        modifier = modifier,
        onClick = onClick,
        shape = RoundedCornerShape(15.dp),
        interactionSource = interactionSource,
        containerColor = if (isPressed) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary,
        contentColor = if (isPressed) MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.onPrimary,
        elevation = if(elevation != null) FloatingActionButtonDefaults.elevation(16.dp) else  FloatingActionButtonDefaults.elevation(10.dp),
    ) {
        icon()
        if (text.isNotBlank()) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                fontSize = 24.sp,
            )
        }
    }
}