package com.burraco.app.ui.screen


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.burraco.app.R
import com.burraco.app.navigation.Screen
import com.burraco.app.ui.components.BurracoButton
import com.burraco.app.ui.components.NameInputField
import com.burraco.app.ui.components.PlayerCard
import com.burraco.app.viewmodel.BurracoViewModel


@Composable
fun PlayerScreen(
    modifier: Modifier = Modifier,
    burracoViewModel: BurracoViewModel,
    onNavigate: (String) -> Unit
) {

    val players = rememberSaveable(
        saver = listSaver(
            save = { it.toList() },                // Come salvare: converti in List<String>
            restore = { it.toMutableStateList() }  // Come ripristinare: torna a SnapshotStateList
        )
    ) { mutableStateListOf<String>(*burracoViewModel.players.value.toTypedArray()) } //* è un spread operator

    var inserting by rememberSaveable { mutableStateOf(false) }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Giocatori",
                style = MaterialTheme.typography.titleLarge.copy(fontSize = 80.sp),
            )

            Spacer(modifier = Modifier.height(90.dp))

            val visibleMap = remember { mutableStateMapOf<String, Boolean>() }

            players.forEach { player ->
                val isVisible = visibleMap[player] ?: false

                AnimatedVisibility(
                    visible = isVisible,
                    enter = fadeIn(animationSpec = tween(700)) + slideInVertically(initialOffsetY = { it / 2 }, animationSpec = tween(700)),
                    exit = fadeOut() + slideOutVertically(targetOffsetY = { it / 2 })
                ) {
                    PlayerCard(player = player)
                }
                Spacer(Modifier.height(15.dp))

                LaunchedEffect(player) {  //ovvero quando cambia player
                    if (!isVisible) {
                        visibleMap[player] = true
                    }
                }
            }

            if (!inserting && players.size < 4)
                BurracoButton(onClick = { inserting = true }, text = "nuovo giocatore") { }
            else if(inserting)
                NameInputField(onDone = { newPlayer ->
                    players.add(newPlayer)
                    inserting = false
                })
        }
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            BurracoButton(onClick = {
                players.clear()
                inserting = false
            }) {
                Icon(
                    painter = painterResource(R.drawable.bin_icon),
                    modifier = Modifier.size(30.dp),
                    tint = MaterialTheme.colorScheme.onPrimary,
                    contentDescription = "Icona reset"
                )
            }
            BurracoButton(enabled = players.size >= 2, onClick = {
                burracoViewModel.resetTeam()
                when (players.size) {
                    2, 3 -> {
                        players.forEach { player ->
                            burracoViewModel.addTeam(
                                player,
                                listOf(player)

                            )
                        }
                        onNavigate(Screen.Game.route)
                    }

                    4 -> {
                        players.forEach { player ->
                            burracoViewModel.addPlayer(player)
                        }
                        onNavigate(Screen.SelectTeam.route)
                    }
                }
            }) {
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