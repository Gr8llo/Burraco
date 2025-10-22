package com.burraco.app.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.zIndex
import com.burraco.app.R
import com.burraco.app.ui.components.BurracoActionButton
import com.burraco.app.ui.components.BurracoButton
import com.burraco.app.ui.components.GridGradient
import com.burraco.app.ui.components.NameInputField
import com.burraco.app.ui.theme.BurracoBlack
import com.burraco.app.ui.theme.BurracoBlue
import com.burraco.app.ui.theme.BurracoRed
import com.burraco.app.ui.theme.BurracoTheme
import com.burraco.app.ui.uiEvent.GameEvent
import com.burraco.app.ui.uiState.GameState

@Composable
fun MatchScreen(
    modifier: Modifier = Modifier,
    state: GameState,
    onEvent: (GameEvent) -> Unit,
    onNavigate: (String) -> Unit
) {
    val columnState = rememberLazyListState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SummaryCard(
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.weight(0.1f))
        Box(
            modifier = Modifier
                .weight(2f)
                .fillMaxSize()
                .clip(RoundedCornerShape(30.dp)),
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .border(
                        width = 2.dp,
                        color = BurracoBlack,
                        shape = RoundedCornerShape(30.dp)
                    )
                    .padding(10.dp),
                state = columnState,
            ) {
                items(state.currentRound) { round ->
                    RoundCard(
                        round = round
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
            if (columnState.canScrollForward) {
                GridGradient(
                    Modifier.align(Alignment.BottomCenter)
                )
            } else if (columnState.canScrollBackward) {
                GridGradient(
                    Modifier.align(Alignment.TopCenter),
                    reversed = true
                )
            }

        }
        Spacer(
            modifier = Modifier
                .weight(0.1f)
                .zIndex(2f)
        )

        Row(
            modifier = Modifier
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
                        onClick = { TODO() },
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.home_icon),
                            contentDescription = "Elimina giocatore selezionato",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }

                    BurracoButton(
                        onClick = { TODO() },
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.info_icon),
                            contentDescription = "Aggiungi nuovo giocatore",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }

                    BurracoButton(
                        onClick = { TODO() },
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

            BurracoActionButton(
                modifier = Modifier.weight(0.2f),
                onClick = {TODO()}
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.add_icon),
                    contentDescription = "Aggiungi nuovo giocatore",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
    if (!state.showInputField) {
        PointsInputDialog(
            onDismiss = {}
        )
    }
}

//Aggiustare per più team

@Composable
fun SummaryCard(
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
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
                .fillMaxSize()
                .padding(10.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Zia Rita & Samuele",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(5.dp))
                Text(
                    text = "1000",
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 55.sp,
                    color = BurracoRed
                )
            }

            Spacer(modifier = Modifier.weight(0.025f))
            VerticalDivider(
                color = BurracoBlack,
                thickness = 2.dp,
                modifier = Modifier.padding(vertical = 15.dp)
            )
            Spacer(modifier = Modifier.weight(0.025f))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Zia Rita & Samuele",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(5.dp))
                Text(
                    text = "1000",
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 55.sp,
                    color = BurracoBlue
                )
            }
        }
    }
}

@Composable
fun RoundCard(
    round: Int,
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .border(
                width = 2.dp,
                color = BurracoBlack,
                shape = RoundedCornerShape(30.dp)
            ),
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 8.dp,
        shape = RoundedCornerShape(30.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Mano $round",
                style = MaterialTheme.typography.bodyMedium
            )
            Row(
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = buildAnnotatedString {
                            append("Burraco: ")
                            withStyle(
                                style = MaterialTheme.typography.labelMedium.copy(
                                    color = BurracoRed
                                ).toSpanStyle(),
                            ) {
                                append("500")
                            }
                        },
                        style = MaterialTheme.typography.labelSmall
                    )
                    Text(
                        text = buildAnnotatedString {
                            append("Carte: ")
                            withStyle(
                                style = MaterialTheme.typography.labelMedium.copy(
                                    color = BurracoRed
                                ).toSpanStyle(),
                            ) {
                                append("500")
                            }
                        },
                        style = MaterialTheme.typography.labelSmall
                    )
                }

                Spacer(modifier = Modifier.weight(0.025f))
                VerticalDivider(
                    color = BurracoBlack,
                    thickness = 2.dp,
                    modifier = Modifier.padding(vertical = 5.dp)
                )
                Spacer(modifier = Modifier.weight(0.025f))

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = buildAnnotatedString {
                            append("Burraco: ")
                            withStyle(
                                style = MaterialTheme.typography.labelMedium.copy(
                                    color = BurracoBlue
                                ).toSpanStyle(),
                            ) {
                                append("500")
                            }
                        },
                        style = MaterialTheme.typography.labelSmall
                    )
                    Text(
                        text = buildAnnotatedString {
                            append("Carte: ")
                            withStyle(
                                style = MaterialTheme.typography.labelMedium.copy(
                                    color = BurracoBlue
                                ).toSpanStyle(),
                            ) {
                                append("500")
                            }
                        },
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }

        }
    }
}

@Composable
fun PointsInputDialog(
    onDismiss: () -> Unit
){
    Dialog(
        onDismissRequest = onDismiss
    ){
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
                    .aspectRatio(0.69f)
                    .padding(horizontal = 10.dp),
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 30.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        modifier = Modifier
                            .weight(0.42f)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ){
                            Text(
                                text = "Zia Rita & Samuele",
                                style = MaterialTheme.typography.bodyMedium,
                                color = BurracoRed
                            )
                            NameInputField(
                                onClick = {}
                            )
                        }
                    }

                    Spacer(modifier = Modifier.weight(0.03f))
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 5.dp),
                        thickness = 2.dp,
                        color = BurracoBlack
                    )
                    Spacer(modifier = Modifier.weight(0.03f))

                    Row(
                        modifier = Modifier
                            .weight(0.42f)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Column(
                            modifier = Modifier.fillMaxSize()
                        ){

                        }
                    }

                }
            }
            Spacer(modifier = Modifier.weight(0.65f))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GamePreview() {
    BurracoTheme {
//        MatchScreen(
//            state = GameState().copy(
//                teams = listOf(
//                    Team(
//                        name = "team1"
//                    ),
//                    Team(
//                        name = "team2"
//                    )
//                ),
//                currentRound = 5
//            ),
//            onEvent = {},
//            onNavigate = {}
//        )
        PointsInputDialog(
            onDismiss = {}
        )
    }
}