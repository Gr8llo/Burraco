package com.burraco.app.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.burraco.app.R
import com.burraco.app.navigation.Screen
import com.burraco.app.ui.components.BurracoButton
import com.burraco.app.ui.theme.BurracoTheme
import com.burraco.app.ui.uiEvent.GameEvent
import com.burraco.app.ui.uiState.GameState
import com.burraco.app.ui.util.adjustedModifier

@Composable
fun WinnerScreen(
    modifier: Modifier = Modifier,
    state: GameState,
    onEvent: (GameEvent) -> Unit,
    onNavigate: (String) -> Unit,
) {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isVisible = true
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier.padding(top = 10.dp, bottom = 5.dp),
            text = if(state.teams.size == 2) "Vincitori" else "Vincitore",
            fontSize = 80.sp,
            style = MaterialTheme.typography.titleLarge,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
        Spacer(Modifier.weight(0.3f))
        Image(
            painter = painterResource(id = R.drawable.suits_celebrating),
            contentDescription = "Illustrazione di semi di carte che giocano",
            modifier = Modifier
                .fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
        Spacer(Modifier.weight(0.1f))
        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn(animationSpec = tween(durationMillis = 2000)) +
                    slideInVertically(
                        initialOffsetY = { it / 2 },
                        animationSpec = tween(durationMillis = 1500)
                    )
        ) {
            Text(
                modifier = Modifier.padding(top = 10.dp, bottom = 5.dp, end = 10.dp, start = 10.dp),
                text = state.winner?.name ?: "Nessuno",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 50.sp
                ),
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                textAlign = TextAlign.Center
            )
        }
        Spacer(Modifier.weight(0.6f))
        BurracoButton(
            enabled = !state.pointsError,
            text = "Torna alla Home",
            onClick = { onNavigate(Screen.SetupRoot.route) },
            large = true
        )
    }
}

@Preview(
    showBackground = true, showSystemUi = true,
    device = "spec:parent=pixel_5,navigation=buttons"
)
@Composable
fun WinnerPreview() {
    BurracoTheme {
        WinnerScreen(
            state = GameState(),
            onEvent = {},
            onNavigate = {},
            modifier = Modifier.background(MaterialTheme.colorScheme.background).adjustedModifier()
        )
    }

}