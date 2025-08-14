package com.burraco.app.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.burraco.app.ui.theme.BurracoBlack
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

@Composable
fun PlayerCard(
    modifier: Modifier = Modifier,
    color: Color = BurracoBlack,
    borderWidth: Dp = 2.dp,
    clickable: Boolean = false,
    onClick: () -> Unit = {},
    player: String
) {
    OutlinedCard(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = color
        ),
        border = BorderStroke(borderWidth, color),
        modifier = Modifier
            .width(210.dp)
            .height(60.dp)
            .clickable(
                enabled = clickable,
                onClick = onClick
            ),
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = player,
                modifier = Modifier
                    .padding(5.dp),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}