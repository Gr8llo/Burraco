package com.burraco.app.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.burraco.app.ui.uiEvent.GameEvent
import com.burraco.app.ui.uiState.GameState

@Composable
fun MatchScreen(
    modifier: Modifier = Modifier,
    state: GameState,
    onEvent: (GameEvent) -> Unit,
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
            text = "Match",
            fontSize = 80.sp,
            style = MaterialTheme.typography.titleLarge,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}
//
//    val teams by gameScreenViewModel.teams.collectAsState()  //questo rende la varibile osservabile dall'UI = quando cambia si ricompone
//    val currentRound by gameScreenViewModel.currentRound.collectAsState()
//    val lastRowPos by gameScreenViewModel.lastRowPos.collectAsState()
//    val readyToWin by gameScreenViewModel.readyToWin.collectAsState()
//
//    if (currentRound != 0)
//        BackHandler(enabled = true) {} // Non fare nulla → blocca il back
//
//    Box(
//        modifier = modifier
//            .fillMaxSize()
//            .adjustedModifier()
//    ) {
//        BackGroundImage()
//        Row(
//            modifier = Modifier.wrapContentHeight(),
//            horizontalArrangement = Arrangement.SpaceEvenly,
//        ) {
//            teams.forEachIndexed { index, team ->
//                TeamPointsColumn(
//                    team = team,
//                    modifier = Modifier.weight(1f),
//                    gameScreenViewModel = gameScreenViewModel,
//                    isLast = index == teams.size - 1,
//                    onNavigate = onNavigate
//                )
//            }
//        }
//
//        Row(
//            modifier = Modifier
//                .fillMaxWidth(),
//            horizontalArrangement = Arrangement.Center
//        ) {
//            BurracoActionButton(
//                onClick = {
//                    if (readyToWin) onNavigate(GameScreen.Winner.route)
//                    else if (teams.all { it.round == gameScreenViewModel.currentRound.value }) gameScreenViewModel.nextRound()
//                },
//                modifier = Modifier
//                    .padding(vertical = 20.dp, horizontal = 25.dp)
//                    .absoluteSafeOffsetY(lastRowPos),
//                elevation = if(readyToWin) 12.dp else null
//            ) {
//                if(readyToWin){
//                    Icon(
//                        painter = painterResource(R.drawable.trophy_icon),
//                        modifier = Modifier
//                            .size(80.dp)
//                            .padding(10.dp),
//                        contentDescription = "win"
//                    )
//                }
//                else {
//                    Icon(
//                        painter = painterResource(R.drawable.plus_icon),
//                        modifier = Modifier
//                            .size(60.dp)
//                            .padding(10.dp),
//                        contentDescription = "+"
//                    )
//                }
//            }
//        }
//    }
//}