package com.burraco.app.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.burraco.app.R
import com.burraco.app.data.entity.Player
import com.burraco.app.navigation.SetupScreen
import com.burraco.app.ui.components.BurracoButton
import com.burraco.app.ui.components.GridGradient
import com.burraco.app.ui.theme.BurracoBlack
import com.burraco.app.ui.theme.BurracoGold
import com.burraco.app.ui.theme.BurracoRed
import com.burraco.app.ui.theme.BurracoTheme
import com.burraco.app.ui.uiState.BurracoState
import com.burraco.app.ui.util.adjustedModifier

@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    state: BurracoState,
    onNavigate: (String) -> Unit
) {
    val listState = rememberLazyListState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(top = 10.dp, bottom = 5.dp),
            text = "Storico",
            style = MaterialTheme.typography.titleLarge,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
        Spacer(modifier = Modifier.weight(0.02f))
        Box(
            modifier = modifier
                .fillMaxWidth()
                .weight(0.93f)
        ) {
            LazyColumn(
                state = listState
            ) {
                itemsIndexed(state.allPlayers) { index, player ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                width = 5.dp,
                                color = when (index) {
                                    0 -> BurracoGold
                                    1 -> Color(0xFF898788)
                                    2 -> Color(0xFFC68356)
                                    else -> BurracoBlack
                                },
                                shape = RoundedCornerShape(30.dp)
                            )
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(20.dp)
                    ) {
                        Image(
                            painter = player.avatar.painter,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth(0.2f),
                            contentScale = ContentScale.Crop,
                        )
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = player.name.replaceFirstChar { it.uppercase() },
                                style = MaterialTheme.typography.bodyLarge,
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Text(
                                    text = buildAnnotatedString {
                                        append("Vinte: ")
                                        withStyle(
                                            style = MaterialTheme.typography.labelMedium.copy(color = BurracoGold)
                                                .toSpanStyle(),
                                        ) {
                                            append(player.matchedWon.toString())
                                        }
                                    },
                                    style = MaterialTheme.typography.bodyMedium,
                                    overflow = TextOverflow.Ellipsis
                                )

                                Text(
                                    text = buildAnnotatedString {
                                        append("Perse: ")
                                        withStyle(
                                            style = MaterialTheme.typography.labelMedium.copy(color = BurracoRed)
                                                .toSpanStyle(),
                                        ) {
                                            append((player.matchedPlayed - player.matchedWon).toString())
                                        }
                                    },
                                    style = MaterialTheme.typography.bodyMedium,
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
            if (listState.canScrollBackward) {
                GridGradient(Modifier.align(Alignment.TopCenter), reversed = true)
            }

            if (listState.canScrollForward) {
                GridGradient(Modifier.align(Alignment.BottomCenter))
            }
        }
        Spacer(modifier = Modifier.weight(0.05f))
        BurracoButton(
            onClick = { onNavigate(SetupScreen.Home.route) },
            text = "Torna alla Home",
            large = true
        )
    }
}

@Preview(
    showBackground = true, showSystemUi = true,
    device = "spec:parent=pixel_5,navigation=buttons"
)
@Composable
fun HistoryPreview() {
    BurracoTheme {
        HistoryScreen(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .adjustedModifier(),
            state = BurracoState(
                allPlayers = listOf(
                    Player(name = "giocatore1"),
                    Player(name = "giocatore1"),
                    Player(name = "giocatore1"),
                    Player(name = "giocatore1"),
                    Player(name = "giocatore1"),
                )
            ),
            onNavigate = {},
        )
    }

}