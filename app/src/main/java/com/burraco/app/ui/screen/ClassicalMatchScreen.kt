package com.burraco.app.ui.screen

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.burraco.app.R
import com.burraco.app.model.Team
import com.burraco.app.navigation.GameScreen
import com.burraco.app.navigation.Screen
import com.burraco.app.ui.components.BurracoActionButton
import com.burraco.app.ui.components.BurracoButton
import com.burraco.app.ui.components.GridGradient
import com.burraco.app.ui.components.PointsInputField
import com.burraco.app.ui.theme.BurracoBlack
import com.burraco.app.ui.theme.BurracoGold
import com.burraco.app.ui.theme.BurracoRed
import com.burraco.app.ui.theme.BurracoTheme
import com.burraco.app.ui.uiEvent.GameEvent
import com.burraco.app.ui.uiState.GameState
import com.burraco.app.ui.util.adjustedModifier

@Composable
fun ClassicalMatchScreen(
    modifier: Modifier = Modifier,
    state: GameState,
    onEvent: (GameEvent) -> Unit,
    onNavigate: (String) -> Unit,
) {
    val columnState = rememberLazyListState()
    val localFocusManager = LocalFocusManager.current

    BackHandler(state.currentRound > 0) { }  //non fa tornare indietro se è già stato giocato un round

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    localFocusManager.clearFocus()
                })
            },
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f)
                .align(Alignment.TopCenter),
            state = columnState
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    state.teams.forEach { team ->
                        Text(
                            modifier = Modifier
                                .weight(1f)
                                .padding(5.dp),
                            text = team.name,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                HorizontalDivider(color = BurracoBlack, thickness = 2.dp)
            }

            if (state.currentRound > 0) {
                items(if (!state.showInputField) state.currentRound else state.currentRound - 1) { roundIndex ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        state.teams.forEach { team ->
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(vertical = 8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = team.scores[roundIndex].first.toString(),
                                        style = MaterialTheme.typography.labelSmall
                                    )
                                    Spacer(modifier = Modifier.height(5.dp))
                                    Text(
                                        text = team.scores[roundIndex].second.toString(),
                                        style = MaterialTheme.typography.labelSmall
                                    )
                                }
                                //per il +
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 15.dp),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.End
                                ) {
                                    Text(
                                        text = "+",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                    //place holder
                                    Spacer(modifier = Modifier.height(5.dp))
                                    Text(
                                        text = " ",
                                        textAlign = TextAlign.Center,
                                        style = MaterialTheme.typography.labelSmall
                                    )
                                }
                            }
                        }
                    }

                    HorizontalDivider(
                        color = if (roundIndex == state.firstRoundTo1000Index) BurracoRed.copy(
                            alpha = 0.5f
                        ) else BurracoBlack.copy(alpha = 0.5f),
                        thickness = 2.dp
                    )
                }
            }
            if (state.showInputField) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 5.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        state.teams.forEach { team ->
                            PointsInputField(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(5.dp),
                                team = team,
                                currentRound = state.currentRound,
                                onValueChage = { points, pointsError ->
                                    onEvent(
                                        GameEvent.ChangeTemporaryPoints(
                                            team,
                                            points,
                                            pointsError
                                        )
                                    )
                                }
                            )
                        }
                    }
                }
            } else {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 5.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        state.teams.forEach { team ->
                            Column(
                                modifier = Modifier
                                    .weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = team.totalScore.toString(),
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                    ),
                                    color = when {
                                        (team.totalScore < 0) -> BurracoRed
                                        (team.name == state.winner?.name) -> BurracoGold
                                        else -> BurracoBlack
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f)
                .align(Alignment.TopCenter),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            VerticalDivider(
                modifier = Modifier
                    .fillMaxHeight(),
                thickness = 3.dp,
                color = BurracoBlack
            )
            if (state.teams.size == 3) {
                VerticalDivider(
                    modifier = Modifier
                        .fillMaxHeight(),
                    thickness = 3.dp,
                    color = BurracoBlack
                )
            }
        }
        if (columnState.canScrollBackward) {
            GridGradient(modifier = Modifier.align(Alignment.TopCenter), reversed = true)
        }
        if (columnState.canScrollForward) {
            GridGradient(modifier = Modifier.align(Alignment.BottomCenter))
        }

        if (!state.showInputField) {
            BottomBar(
                modifier = Modifier.align(Alignment.BottomCenter),
                onEvent = onEvent,
                onNavigate = onNavigate,
                showConfirmGame = state.showConfirmGame
            )
        } else {
            BurracoButton(
                modifier = Modifier.align(Alignment.BottomCenter),
                enabled = !state.pointsError,
                text = "Conferma",
                onClick = { onEvent(GameEvent.ConfirmPoints) },
                large = true
            )
        }
    }
}

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    onEvent: (GameEvent) -> Unit,
    onNavigate: (String) -> Unit,
    showConfirmGame: Boolean,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Surface(
            modifier = Modifier
                .weight(0.70f)
                .fillMaxWidth()
                .border(
                    width = 2.dp,
                    color = BurracoBlack,
                    shape = RoundedCornerShape(30.dp)
                ),
            color = MaterialTheme.colorScheme.surface,
            shadowElevation = 8.dp,
            shape = RoundedCornerShape(30.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 14.dp, vertical = 6.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BurracoButton(
                    modifier = Modifier,
                    onClick = {
                        onNavigate(Screen.SetupRoot.route)
                    },
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.home_icon),
                        contentDescription = "Elimina giocatore selezionato",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }

                BurracoButton(
                    onClick = { onNavigate(GameScreen.Rules.route) },
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.info_icon),
                        contentDescription = "Aggiungi nuovo giocatore",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }

                BurracoButton(
                    onClick = {
                        onEvent(GameEvent.EditPreviousRound)
                    },
                    enabled = true,
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.edit_note_icon),
                        contentDescription = "Aggiungi nuovo giocatore",
                        tint = MaterialTheme.colorScheme.onPrimary,
                    )
                }
            }
        }
        Spacer(modifier = Modifier.weight(0.1f))

        //animazione bottone con trofeo
        val infiniteTransition = rememberInfiniteTransition(label = "PulseTransition")
        val scale by infiniteTransition.animateFloat(
            initialValue = 1.0f,
            targetValue = 1.2f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 800, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "PulseScale"
        )

        if (showConfirmGame) {
            BurracoActionButton(
                modifier = Modifier
                    .weight(0.2f)
                    .scale(scale),
                onClick = {
                    onEvent(GameEvent.OnWin)
                    onNavigate(GameScreen.Winner.route)
                }
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.trophy_icon),
                    contentDescription = "Conferma vittoria",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.scale(scale)
                )
            }
        } else {
            BurracoActionButton(
                modifier = Modifier.weight(0.2f),
                onClick = { onEvent(GameEvent.InsertNextRound) },
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.add_icon),
                    contentDescription = "Aggiungi nuovo giocatore",
                    tint = MaterialTheme.colorScheme.onPrimary,
                )
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun ClassicGamePreview() {
    BurracoTheme {
        ClassicalMatchScreen(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .adjustedModifier(),
            state = GameState().copy(
                // Aggiungi dati di esempio per vedere l'anteprima
                teams = listOf(
                    Team(
                        name = "Zia Sonia",
                        scores = listOf(
                            Pair(1090, 1000),
                            Pair(100, 1000),
                            Pair(100, 1000),
                        )
                    ),
                    Team(
                        name = "Nonna & Nonno",
                        scores = listOf(
                            Pair(100, 1000),
                            Pair(100, 1000),
                            Pair(100, 1000),
                        )
                    ),
                    Team(
                        name = "Nonna & Nonno",
                        scores = listOf(
                            Pair(100, 1000),
                            Pair(100, 1000),
                            Pair(100, 1000),
                        )
                    ),
                ),
                currentRound = 2,
                firstRoundTo1000Index = 1,
                showInputField = false,
                winner = Team(
                    name = "Zia Sonia",
                ),
            ),
            onEvent = {},
            onNavigate = {},
        )
    }
}

