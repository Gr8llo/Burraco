package com.burraco.app.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.burraco.app.R
import com.burraco.app.data.entity.Player
import com.burraco.app.navigation.Screen
import com.burraco.app.navigation.SetupScreen
import com.burraco.app.ui.components.BurracoButton
import com.burraco.app.ui.components.GridGradient
import com.burraco.app.ui.components.NameInputField
import com.burraco.app.ui.components.PlayerCard
import com.burraco.app.ui.theme.BurracoBlack
import com.burraco.app.ui.theme.BurracoCardColor
import com.burraco.app.ui.theme.BurracoRed
import com.burraco.app.ui.uiEvent.BurracoEvent
import com.burraco.app.ui.uiState.BurracoState

@Composable
fun PlayerSelectionScreen(
    modifier: Modifier = Modifier,
    state: BurracoState,
    onEvent: (BurracoEvent) -> Unit,
    onNavigate: (String) -> Unit
) {
    val gridState = rememberLazyGridState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(top = 10.dp, bottom = 5.dp),
            text = "Giocatori",
            fontSize = 80.sp,
            style = MaterialTheme.typography.titleLarge,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )

        Spacer(modifier = Modifier.height(24.dp))

        Box(
            //Box per sovrapporre la griglia e la sfumatura
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.75f)
        ) {
            LazyVerticalGrid(
                state = gridState,
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(state.allPlayers) { player ->
                    PlayerCard(
                        player = player,
                        onClick = { player ->
                            onEvent(BurracoEvent.OnPlayerSelected(player))
                        },
                        color = if (player in state.selectedPlayers) BurracoCardColor.BLUE else BurracoCardColor.BLACK,
                        showRemovePlayer = state.showRemovePlayer,
                        onDelete = { player ->
                            onEvent(BurracoEvent.RemovePlayer(player))
                        }
                    )
                }
            }
            if (gridState.canScrollBackward)
                GridGradient(Modifier.align(Alignment.TopCenter), reversed = true)

            if (gridState.canScrollForward) {
                GridGradient(Modifier.align(Alignment.BottomCenter))
            }
        }

        Column(
            modifier = Modifier.weight(0.25f),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (!state.showRemovePlayer) {
                Text(
                    modifier = Modifier.padding(top = 15.dp, bottom = 5.dp),
                    text = "Clicca per selezionare i giocatori",
                    style = MaterialTheme.typography.labelSmall,
                    color = BurracoBlack,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.weight(0.4f))

            Surface(
                modifier = Modifier
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
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (!state.showRemovePlayer) {
                        BurracoButton(
                            modifier = Modifier,
                            onClick = { onEvent(BurracoEvent.ShowRemovePlayer) },
                        ) {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.remove_player_icon),
                                contentDescription = "Elimina giocatore selezionato",
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }

                        Spacer(Modifier.weight(0.1f))

                        BurracoButton(
                            onClick = { onEvent(BurracoEvent.ShowAddPlayer) },
                        ) {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.add_player_icon),
                                contentDescription = "Aggiungi nuovo giocatore",
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }

                        // Questo Spacer spinge il bottone "Conferma" all'estrema destra
                        Spacer(modifier = Modifier.weight(0.9f))

                        // Bottone "Conferma"
                        BurracoButton(
                            onClick = {
                                onEvent(BurracoEvent.OnMatchPlayersConfirmed)
                                if(state.selectedPlayers.size < 4){
                                    onNavigate(Screen.GameRoot.route)
                                }
                                else{
                                    onNavigate(SetupScreen.Team.route)
                                }
                            },
                            enabled = state.selectedPlayers.size in 2..4,
                            text = "Conferma"
                        )
                    } else {
                        Spacer(modifier = Modifier.weight(0.5f))
                        BurracoButton(
                            onClick = { onEvent(BurracoEvent.HideRemovePlayer) },
                            enabled = true,
                            text = "Torna alla Selezione"
                        )
                        Spacer(modifier = Modifier.weight(0.5f))
                    }
                }
            }
            Spacer(modifier = Modifier.weight(0.6f))
        }
    }
    if (state.showAddPlayer) {
        PlayerDialog(
            onDismiss = { onEvent(BurracoEvent.HideAddPlayer) },
            onDone = {
                onEvent(BurracoEvent.AddPLayer(Player(name = it)))
                onEvent(BurracoEvent.HideAddPlayer)
            }
        )
    }
    if(state.showErrorDialog){
        ErrorDialog(
            onDismiss = { onEvent(BurracoEvent.HideErrorMessage) },
            errorMessage = state.errorMessage ?: ""
        )
    }
}

@Composable
fun PlayerDialog(
    onDismiss: () -> Unit,
    onDone: (String) -> Unit
) {
    Dialog(
        onDismissRequest = { onDismiss() },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.weight(0.35f))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 10.dp, start = 10.dp)
                    .wrapContentHeight(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.card),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    contentScale = ContentScale.Crop,
                )
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painterResource(R.drawable.spade),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth(0.6f)
                            .wrapContentHeight()
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    NameInputField(
                        onClick = onDone
                    )
                }
            }
            Spacer(modifier = Modifier.weight(0.65f))
        }
    }
}

@Composable
fun ErrorDialog(
    onDismiss: () -> Unit,
    errorMessage: String
) {
    Dialog(
        onDismissRequest = onDismiss,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.weight(0.35f))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 10.dp, start = 10.dp)
                    .wrapContentHeight(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.red_card),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    contentScale = ContentScale.Crop,
                )
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painterResource(R.drawable.exclamation_point),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxSize(0.3f)
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        modifier = Modifier.padding(horizontal = 20.dp),
                        text = errorMessage,
                        style = MaterialTheme.typography.bodyLarge,
                        color = BurracoRed,
                        textAlign = TextAlign.Center
                    )
                }
            }
            Spacer(modifier = Modifier.weight(0.65f))
        }
    }
}
