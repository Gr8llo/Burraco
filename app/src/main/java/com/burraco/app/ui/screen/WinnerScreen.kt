package com.burraco.app.ui.screen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.burraco.app.R
import com.burraco.app.model.Team
import com.burraco.app.navigation.Screen
import com.burraco.app.ui.components.AnimationPlayer
import com.burraco.app.ui.components.BurracoButton
import com.burraco.app.ui.uiEvent.GameEvent
import com.burraco.app.ui.uiState.GameState
import com.burraco.app.viewmodel.BurracoViewModel
import kotlinx.coroutines.delay
import com.burraco.app.ui.util.*

@Composable
fun WinnerScreen(
    modifier: Modifier = Modifier,
    state: GameState,
    onEvent: (GameEvent) -> Unit,
    onNavigate: (String) -> Unit,
) {
//    val focusManager = LocalFocusManager.current
//    var isVisibleButtonReset by rememberSaveable { mutableStateOf(false) }
//
//    Box(modifier = modifier.fillMaxSize()) {
//
//        // Sfondo: schermata di gioco
//        GameScreen(
//            gameScreenViewModel = gameScreenViewModel,
//            modifier = modifier.blurIfSupported(10.dp)
//        )
//
//        // Overlay simulato (senza blur nativo)
//        Box(
//            modifier = modifier
//                .fillMaxSize()
//                .fakeBlur()
//                .clickable(
//                    enabled = true,
//                    onClick = { focusManager.clearFocus() }) // blocca interazioni sotto
//                .padding(bottom = 30.dp),
//            contentAlignment = Alignment.Center
//        ) {
//            Column(
//                modifier = Modifier.fillMaxSize().absoluteOffset(y = (-20).dp),
//                verticalArrangement = Arrangement.Center,
//                horizontalAlignment = Alignment.CenterHorizontally,
//            ) {
//
//                val teams by burracoViewModel.teams.collectAsState()
//                var isVisibleScoreboard by rememberSaveable { mutableStateOf(false) }
//
//                AnimationPlayer(
//                    animation = R.raw.trophy,
//                    onEnd = {isVisibleScoreboard = true},
//                    modifier = Modifier.size(400.dp)
//                )
//
//                Scoreboard(
//                    scoreboard = teams.toMutableList(),
//                    isVisible = isVisibleScoreboard,
//                    canReset = { state -> isVisibleButtonReset = state }
//                )
//            }
//            if (isVisibleButtonReset) {
//                BurracoButton(
//                    modifier = Modifier.align(Alignment.BottomCenter),
//                    text = "Nuova Partita",
//                    onClick = {
//                        burracoViewModel.resetTeam()
//                        onNavigate(Screen.Home.route)
//                    },
//                )
//            }
//        }
//    }
}
//
////extension function modifier-> definite in ScoreEntryScreen
//
//@Composable
//fun Scoreboard(
//    isVisible: Boolean,
//    scoreboard: MutableList<Team>,
//    canReset: (Boolean) -> Unit
//) {
//    scoreboard.sortByDescending { it.totalScore }
//
//    var visibleCount by remember { mutableStateOf(0) }
//
//    // Avvia la sequenza quando la scoreboard diventa visibile
//    LaunchedEffect(isVisible) {
//        if (isVisible) {
//            for (i in scoreboard.indices) {
//                visibleCount++
//                delay(1000)
//            }
//            canReset(true)
//        }
//    }
//
//
//    Column(
//        modifier = Modifier.wrapContentHeight().absoluteOffset(y = (-60).dp),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally,
//    ) {
//
//        scoreboard.forEachIndexed { index, team ->
//            val pos = index + 1
//            val (color, fontSize) = when (pos) {
//                1 -> Color(0xFFFFD700) to 36.sp
//                2 -> Color(0xFFC0C0C0) to 32.sp
//                3 -> Color(0xFFCD7F32) to 28.sp
//                else -> Color.White to 24.sp
//            }
//
//            val alpha by animateFloatAsState(
//                targetValue = if (index < visibleCount) 1f else 0f,
//                animationSpec = tween(durationMillis = 1500)
//            )
//
//            val translationY by animateFloatAsState(
//                targetValue = if (index < visibleCount) 1f else 0f,
//                animationSpec = tween(durationMillis = 1500)
//            )
//
//            val name = if(team.name.length > 6) team.name.take(4) + "..." else team.name
//
//            Text(
//                text = "$pos. $name - ${team.totalScore}",
//                style = MaterialTheme.typography.titleLarge.copy(
//                    fontSize = fontSize,
//                    color = color
//                ),
//                modifier = Modifier.graphicsLayer(alpha = alpha, translationY = (translationY*(-50))),
//            )
//            Spacer(Modifier.height(10.dp))
//        }
//    }
//}