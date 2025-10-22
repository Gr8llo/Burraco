package com.burraco.app.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.burraco.app.R
import com.burraco.app.data.entity.Player
import com.burraco.app.ui.theme.BurracoCardColor
import com.burraco.app.ui.theme.BurracoRed

@Composable
fun PlayerCard(
    modifier: Modifier = Modifier,
    player: Player,
    onClick: (Player) -> Unit,
    color: BurracoCardColor = BurracoCardColor.BLACK,
    showRemovePlayer: Boolean = false,
    onDelete: (Player) -> Unit = {},
    smallText: Boolean = false,
) {
    val infiniteTransition = rememberInfiniteTransition(label = "shake_animation")
    val shakeAngle by infiniteTransition.animateFloat(
        initialValue = -8f,
        targetValue = 8f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 250,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse // Inverte la direzione dell'animazione ad ogni ciclo
        ),
        label = "shake_angle"
    )

    val interactionSource = remember { MutableInteractionSource() }

    val card = when (color) {
        BurracoCardColor.BLUE -> R.drawable.blue_card
        BurracoCardColor.RED -> R.drawable.red_card
        else -> R.drawable.card
    }

    Box(
        modifier = modifier
            .clickable { onClick(player) }
            .aspectRatio(0.69f),  //card AspectRatio
        contentAlignment = Alignment.Center,
    ) {
        if (showRemovePlayer) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.bin_icon),
                contentDescription = "Rimuovi giocatore",
                tint = BurracoRed,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 9.dp, end = 8.dp)
                    .size(40.dp)
                    .clickable(
                        onClick = { onDelete(player) },
                        interactionSource = interactionSource,
                        indication = null
                    )
                    .zIndex(2f)
                    .graphicsLayer(rotationZ = shakeAngle)
            )
        }

        Image(
            painter = painterResource(card),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit,
        )

        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        )
        {
            Image(
                painter = painterResource(R.drawable.spade),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth(0.8f),
                contentScale = ContentScale.Crop,
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                modifier = Modifier
                    .padding(horizontal = 10.dp),
                text = player.name.replaceFirstChar { it.uppercase() },
                style = if (smallText) MaterialTheme.typography.bodyMedium.copy(fontSize = 17.sp) else MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
        }
    }
}
