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
import com.burraco.app.ui.uiEvent.BurracoEvent
import com.burraco.app.ui.uiState.BurracoState

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
            text = "Giocatori",
            fontSize = 80.sp,
            style = MaterialTheme.typography.titleLarge,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}
//
//    val players by burracoViewModel.players.collectAsState() //Stato = ultimo valore del  flow
//
//    val firstTeam = rememberSaveable(
//        saver = listSaver(
//            save = { it.toList() },
//            restore = { it.toMutableStateList() }
//        )
//    ) { mutableStateListOf<String>() }
//
//    val secondTeam = rememberSaveable(
//        saver = listSaver(
//            save = { it.toList() },
//            restore = { it.toMutableStateList() }
//        )
//    ) { mutableStateListOf<String>() }
//
//
//    Box(
//        modifier = modifier.fillMaxSize()
//    ) {
//        BackGroundImage()
//        Column(
//            modifier = Modifier.fillMaxSize(),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center
//        ) {
//            Text(
//                text = "Squadre",
//                style = MaterialTheme.typography.titleLarge.copy(fontSize = 80.sp),
//            )
//
//            Spacer(Modifier.height(15.dp))
//
//            Text(
//                text = """seleziona le squadre
//                        |cliccando sui giocatori""".trimMargin("|"),
//                style = MaterialTheme.typography.bodyLarge,
//                textAlign = TextAlign.Center
//            )
//
//            Spacer(modifier = Modifier.height(60.dp))
//
//            players.forEach { player ->
//                PlayerCard(
//                    player = player,
//                    color = when (player) {
//                        in firstTeam -> BurracoRed
//                        in secondTeam -> BurracoBlack
//                        else -> Color.LightGray
//                    },
//                    borderWidth = when (player) {
//                        in firstTeam, in secondTeam-> 5.dp
//                        else -> 2.dp
//                    },
//                    clickable = true,
//                    onClick = {
//                        if (player !in firstTeam && player !in secondTeam) {
//                            if (firstTeam.size < 2)
//                                firstTeam.add(player)
//                            else
//                                secondTeam.add(player)
//                        } else {
//                            if (player in firstTeam)
//                                firstTeam.remove(player)
//                            else
//                                secondTeam.remove(player)
//                        }
//                    }
//                )
//                Spacer(Modifier.height(15.dp))
//            }
//        }
//        Row(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(16.dp),
//            horizontalArrangement = Arrangement.SpaceBetween,
//            verticalAlignment = Alignment.Bottom
//        ) {
//            BurracoButton(onClick = {
//                firstTeam.clear()
//                secondTeam.clear()
//            }) {
//                Icon(
//                    painter = painterResource(R.drawable.bin_icon),
//                    modifier = Modifier.size(30.dp),
//                    tint = MaterialTheme.colorScheme.onPrimary,
//                    contentDescription = "Icona reset"
//                )
//            }
//            BurracoButton(enabled = true, onClick = {
//                burracoViewModel.resetTeam()
//                burracoViewModel.addTeam(
//                    teamName = firstTeam.joinToString(separator = " & "),
//                    players = firstTeam
//                )
//                burracoViewModel.addTeam(
//                    teamName = secondTeam.joinToString(separator = " & "),
//                    players = secondTeam
//                )
//                onNavigate(Screen.Game.route)
//            })
//            {
//                Icon(
//                    painter = painterResource(R.drawable.check_icon),
//                    modifier = Modifier.size(30.dp),
//                    tint = MaterialTheme.colorScheme.onPrimary,
//                    contentDescription = "Icona reset"
//                )
//            }
//        }
//    }