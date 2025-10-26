package com.burraco.app.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.burraco.app.R
import com.burraco.app.navigation.SetupScreen
import com.burraco.app.ui.components.BurracoButton
import com.burraco.app.ui.uiEvent.BurracoEvent
import com.burraco.app.ui.uiState.BurracoState
import com.burraco.app.ui.uiState.SortType


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    state: BurracoState,
    onEvent: (BurracoEvent) -> Unit,
    onNavigate: (String) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp, vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(0.1f))
        Text(
            text = "Burraco",
            style = MaterialTheme.typography.titleLarge,
        )
        Box(
            modifier = Modifier.weight(0.8f), //weight lavora sullo spazio rimanente dopo aver considerato il peso degli elementi che non hanno il modificatore weight
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = painterResource(id = R.drawable.card),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.suits_playing),
                    contentDescription = "Illustrazione di semi di carte che giocano",
                    modifier = Modifier
                        .weight(
                            1f,
                            fill = false
                        )  //weight in verticale Colonna + fill=false -> Riserva lo spazio, ma non si "stira" per riempirlo
                        .fillMaxWidth(0.8f),   //questo ovviamente sulla larghezza
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.weight(0.1f))

                if (state.showResume) {
                    BurracoButton(
                        text = "Riprendi Partita",
                        enabled = true,
                        onClick = { TODO() },
                        large = true,
                        modifier = Modifier.weight(0.2f)
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                }
                BurracoButton(
                    text = "Nuova Partita",
                    enabled = true,
                    onClick = {
                        onEvent(BurracoEvent.OnNewGame)
                        onNavigate(SetupScreen.Player.route)
                    },
                    large = !state.showResume,
                )
                Spacer(modifier = Modifier.height(1.dp))
                BurracoButton(
                    text = "Statistiche",
                    enabled = true,
                    onClick = {
                        onEvent(BurracoEvent.SortPlayer(SortType.MATCH_WON))
                        onNavigate(SetupScreen.History.route)
                    },
                )
            }
        }
        Spacer(modifier = Modifier.weight(0.1f))
    }
}