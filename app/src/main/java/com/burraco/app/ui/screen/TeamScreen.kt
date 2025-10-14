package com.burraco.app.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.burraco.app.R
import com.burraco.app.navigation.Screen
import com.burraco.app.ui.components.BurracoButton
import com.burraco.app.ui.components.PlayerCard
import com.burraco.app.ui.theme.BurracoBlack
import com.burraco.app.ui.theme.BurracoRed
import com.burraco.app.viewmodel.BurracoViewModel
import com.burraco.app.ui.components.BackGroundImage

@Composable
fun TeamScreen(
    modifier: Modifier = Modifier,
    burracoViewModel: BurracoViewModel,
    onNavigate: (String) -> Unit
) {

    val players by burracoViewModel.players.collectAsState() //Stato = ultimo valore del  flow

    val firstTeam = rememberSaveable(
        saver = listSaver(
            save = { it.toList() },
            restore = { it.toMutableStateList() }
        )
    ) { mutableStateListOf<String>() }

    val secondTeam = rememberSaveable(
        saver = listSaver(
            save = { it.toList() },
            restore = { it.toMutableStateList() }
        )
    ) { mutableStateListOf<String>() }


    Box(
        modifier = modifier.fillMaxSize()
    ) {
        BackGroundImage()
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Squadre",
                style = MaterialTheme.typography.titleLarge.copy(fontSize = 80.sp),
            )

            Spacer(Modifier.height(15.dp))

            Text(
                text = """seleziona le squadre 
                        |cliccando sui giocatori""".trimMargin("|"),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(60.dp))

            players.forEach { player ->
                PlayerCard(
                    player = player,
                    color = when (player) {
                        in firstTeam -> BurracoRed
                        in secondTeam -> BurracoBlack
                        else -> Color.LightGray
                    },
                    borderWidth = when (player) {
                        in firstTeam, in secondTeam-> 5.dp
                        else -> 2.dp
                    },
                    clickable = true,
                    onClick = {
                        if (player !in firstTeam && player !in secondTeam) {
                            if (firstTeam.size < 2)
                                firstTeam.add(player)
                            else
                                secondTeam.add(player)
                        } else {
                            if (player in firstTeam)
                                firstTeam.remove(player)
                            else
                                secondTeam.remove(player)
                        }
                    }
                )
                Spacer(Modifier.height(15.dp))
            }
        }
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            BurracoButton(onClick = {
                firstTeam.clear()
                secondTeam.clear()
            }) {
                Icon(
                    painter = painterResource(R.drawable.bin_icon),
                    modifier = Modifier.size(30.dp),
                    tint = MaterialTheme.colorScheme.onPrimary,
                    contentDescription = "Icona reset"
                )
            }
            BurracoButton(enabled = true, onClick = {
                burracoViewModel.resetTeam()
                burracoViewModel.addTeam(
                    teamName = firstTeam.joinToString(separator = " & "),
                    players = firstTeam
                )
                burracoViewModel.addTeam(
                    teamName = secondTeam.joinToString(separator = " & "),
                    players = secondTeam
                )
                onNavigate(Screen.Game.route)
            })
            {
                Icon(
                    painter = painterResource(R.drawable.check_icon),
                    modifier = Modifier.size(30.dp),
                    tint = MaterialTheme.colorScheme.onPrimary,
                    contentDescription = "Icona reset"
                )
            }
        }
    }
}