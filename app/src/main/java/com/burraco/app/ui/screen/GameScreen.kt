package com.burraco.app.ui.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.burraco.app.R
import com.burraco.app.navigation.GameGroupScreen
import com.burraco.app.ui.components.BackGroundImage
import com.burraco.app.ui.components.BurracoActionButton
import com.burraco.app.ui.components.TeamPointsColumn
import com.burraco.app.ui.util.absoluteSafeOffsetY
import com.burraco.app.ui.util.adjustedModifier
import com.burraco.app.viewmodel.GameScreenViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GameScreen(
    modifier: Modifier = Modifier,
    gameScreenViewModel: GameScreenViewModel,
    onNavigate: (String) -> Unit = {}
) {

    val teams by gameScreenViewModel.teams.collectAsState()  //questo rende la varibile osservabile dall'UI = quando cambia si ricompone
    val currentRound by gameScreenViewModel.currentRound.collectAsState()
    val lastRowPos by gameScreenViewModel.lastRowPos.collectAsState()
    val readyToWin by gameScreenViewModel.readyToWin.collectAsState()

    if (currentRound != 0)
        BackHandler(enabled = true) {} // Non fare nulla → blocca il back

    Box(
        modifier = modifier
            .fillMaxSize()
            .adjustedModifier()
    ) {
        BackGroundImage()
        Row(
            modifier = Modifier.wrapContentHeight(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            teams.forEachIndexed { index, team ->
                TeamPointsColumn(
                    team = team,
                    modifier = Modifier.weight(1f),
                    gameScreenViewModel = gameScreenViewModel,
                    isLast = index == teams.size - 1,
                    onNavigate = onNavigate
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            BurracoActionButton(
                onClick = {
                    if (readyToWin) onNavigate(GameGroupScreen.Winner.route)
                    else if (teams.all { it.round == gameScreenViewModel.currentRound.value }) gameScreenViewModel.nextRound()
                },
                modifier = Modifier
                    .padding(vertical = 20.dp, horizontal = 25.dp)
                    .absoluteSafeOffsetY(lastRowPos),
                elevation = if(readyToWin) 12.dp else null
            ) {
                if(readyToWin){
                    Icon(
                        painter = painterResource(R.drawable.trophy_icon),
                        modifier = Modifier
                            .size(80.dp)
                            .padding(10.dp),
                        contentDescription = "win"
                    )
                }
                else {
                    Icon(
                        painter = painterResource(R.drawable.plus_icon),
                        modifier = Modifier
                            .size(60.dp)
                            .padding(10.dp),
                        contentDescription = "+"
                    )
                }
            }
        }
    }
}