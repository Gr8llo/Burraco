package com.burraco.app.ui.screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.burraco.app.data.entity.Player
import com.burraco.app.navigation.Screen
import com.burraco.app.ui.components.BurracoButton
import com.burraco.app.ui.components.PlayerCard
import com.burraco.app.ui.theme.BurracoBlack
import com.burraco.app.ui.theme.BurracoCardColor
import com.burraco.app.ui.theme.BurracoTheme
import com.burraco.app.ui.uiEvent.BurracoEvent
import com.burraco.app.ui.uiState.BurracoState


val offsetAmount: List<List<Int>> = listOf(
    listOf(0),     //1 giocatori
    listOf(-25, 25),     //2 giocatore
    listOf(-50, 0, 50),     //3 giocatori
    listOf(-65, -25, 25, 75),     //4 giocatori
)
val rotationAmount: List<List<Float>> = listOf(
    listOf(0f),     //1 giocatori
    listOf(-10f, 10f),     //2 giocatore
    listOf(-15f, -5f, 5f),     //3 giocatori
    listOf(-20f, -10f, 0f, 5f),     //4 giocatori
)

@Composable
fun TeamScreen(
    modifier: Modifier = Modifier,
    state: BurracoState,
    onEvent: (BurracoEvent) -> Unit,
    onNavigate: (String) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(top = 10.dp, bottom = 5.dp),
            text = "Squadre",
            fontSize = 80.sp,
            style = MaterialTheme.typography.titleLarge,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(24.dp))
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .weight(2f)
                .border(
                    width = 5.dp,
                    color = BurracoBlack,
                    shape = RoundedCornerShape(30.dp)
                ),
            color = MaterialTheme.colorScheme.surface,
            shadowElevation = 8.dp,
            shape = RoundedCornerShape(30.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                state.playersWithTeam.forEachIndexed { index, player ->
                    PlayerCard(
                        modifier = Modifier
                            .fillMaxWidth(0.4f)
                            .zIndex(index.toFloat())
                            .offset(x = offsetAmount[state.playersWithTeam.size - 1][index].dp)
                            .rotate(rotationAmount[state.playersWithTeam.size - 1][index]),
                        player = player,
                        color = if (index < 2) BurracoCardColor.RED else BurracoCardColor.BLUE,
                        onClick = {
                            if (player == state.playersWithTeam.last()) {
                                onEvent(BurracoEvent.OnTeamSelected(player))
                            }
                        },
                        animationEnable = true,
                        smallText = true
                    )
                }
            }
        }
        Spacer(modifier = Modifier.weight(0.1f))
        if (state.playersWithTeam.size < 4) {
            Row(
                modifier = Modifier
                    .weight(1f),
                horizontalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.weight(0.1f))  //per avere spazio sia all'inizio che alla fine
                state.playersWithoutTeam.forEach { player ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        contentAlignment = Alignment.Center
                    ) {
                        PlayerCard(
                            player = player,
                            onClick = {
                                onEvent(BurracoEvent.OnTeamSelected(player))
                            },
                            smallText = true,
                        )
                    }
                    Spacer(modifier = Modifier.weight(0.1f))
                }
            }
            Spacer(modifier = Modifier.weight(0.05f))
            Text(
                text = "Giocatori disponibili",
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.weight(0.02f))
            Text(
                text = "Clicca sul giocatore per aggiungere o togliere",
                style = MaterialTheme.typography.labelSmall,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.weight(0.43f))
        } else {
            Row(
                modifier = Modifier
                    .weight(1f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BurracoButton(
                    text = "Conferma Squadre",
                    onClick = {
                        onEvent(BurracoEvent.OnMatchTeamsConfirmed)
                        onNavigate(Screen.GameRoot.route)
                    },
                    large = true
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
fun TeamTest() {
    BurracoTheme {
        TeamScreen(
            state = BurracoState(
                playersWithoutTeam = listOf(
                    Player(name = "nonna"),
                    Player(name = "nonna"),
                    Player(name = "nonna"),
                    Player(name = "nonna"),
                ),
                playersWithTeam = listOf(
                    Player(name = "nonna"),
                    Player(name = "rita"),
                    Player(name = "samue"),
                    Player(name = "sonia")
                )
            ),
            onEvent = { TODO() },
            onNavigate = { TODO() }
        )
    }
}
