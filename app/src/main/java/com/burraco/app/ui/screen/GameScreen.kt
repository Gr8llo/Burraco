package com.burraco.app.ui.screen

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.burraco.app.R
import com.burraco.app.navigation.Screen
import com.burraco.app.ui.components.BurracoActionButton
import com.burraco.app.ui.components.TeamPointsColumn
import com.burraco.app.viewmodel.BurracoViewModel
import kotlinx.coroutines.delay
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GameScreen(
    modifier: Modifier = Modifier,
    burracoViewModel: BurracoViewModel,
    onNavigate: (String) -> Unit
) {

    val teams by burracoViewModel.teams.collectAsState()  //questo rende la varibile osservabile dall'UI = quando cambia si ricompone
    val currentRound by burracoViewModel.currentRound.collectAsState()

    var currentTeamAdding by rememberSaveable { mutableStateOf("") }

    var lastRowPos by remember { mutableStateOf(Offset.Zero) }

    if (currentRound != 0)
        BackHandler(enabled = true) {
        } // Non fare nulla → blocca il back

    Box(
        modifier = modifier
            .fillMaxSize()
            .adjustedModifier()
    ) {
        Row(
            modifier = Modifier.wrapContentHeight(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            teams.forEachIndexed { index, team ->
                TeamPointsColumn(
                    team = team,
                    modifier = Modifier.weight(1f),
                    isLast = index == teams.size - 1,
                    insertingNewRound = team.round < currentRound,
                    onClick = { currentTeamAdding = team.name },
                    onNavigate = { screen -> onNavigate(screen) },
                    getGlobalPos = { globalPos -> lastRowPos = globalPos }
                )
            }
        }

        Column(
            modifier = Modifier
                .absoluteOffset()
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BurracoActionButton(
                onClick = {
                    if (teams.all { it.round == currentRound })
                        burracoViewModel.nextRound()
                },
                modifier = Modifier
                    .padding(vertical = 20.dp, horizontal = 25.dp)
                    .absoluteSafeOffsetY(lastRowPos)
            ) {
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

    LaunchedEffect(currentTeamAdding) {
        if (currentTeamAdding.isNotBlank()) {
            onNavigate(Screen.ScoreEntry.route + "/$currentTeamAdding")
            currentTeamAdding = "" // Reset per evitare il bug
        }
    }

    LaunchedEffect(currentRound, teams) {
        if ((teams.all { it.round == currentRound }) && teams.any { it.totalScore >= 2005 }) { //partita finita
            delay(1000)
            onNavigate(Screen.Winner.route)
        }
    }
}

@Composable
fun Modifier.adjustedModifier(): Modifier {
    val configuration = LocalConfiguration.current
    if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
        return this
    else
        return this.padding(WindowInsets.displayCutout.asPaddingValues())
}

@Composable
fun Modifier.absoluteSafeOffsetY(offset: Offset): Modifier {
    val y: Dp = with(LocalDensity.current) { offset.y.toDp() } + 30.dp
    val max = (LocalConfiguration.current.screenHeightDp.dp * 3)/4
    return if(y>max) this.absoluteOffset(y=max) else this.absoluteOffset(y=y)
}