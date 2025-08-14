package com.burraco.app.ui.screen

import android.annotation.SuppressLint
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.burraco.app.navigation.Screen
import com.burraco.app.ui.components.PointsInputField
import com.burraco.app.ui.theme.BurracoBlack
import com.burraco.app.ui.theme.BurracoRed
import com.burraco.app.viewmodel.BurracoViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ScoreEntryScreen(
    modifier: Modifier = Modifier,
    burracoViewModel: BurracoViewModel,
    onNavigate: (String) -> Unit,
    currentTeam: String?,
) {
    val focusManager = LocalFocusManager.current

    Box(modifier = modifier.fillMaxSize()) {

        // Sfondo: schermata di gioco
        GameScreen(
            burracoViewModel = burracoViewModel,
            onNavigate = {}, // disattivato per overlay
            modifier = modifier.blurIfSupported(10.dp)
        )

        // Overlay simulato (senza blur nativo)
        Box(
            modifier = modifier
                .fillMaxSize()
                .fakeBlur()
                .pointerInput(Unit) {
                    detectTapGestures {
                        //focusManager.clearFocus()
                        onNavigate(Screen.Game.route)
                    }
                },// blocca interazioni sotto
            contentAlignment = Alignment.Center
        ) {
            Card(
                Modifier
                    .fillMaxWidth(0.75f)
                    .fillMaxHeight(0.45f)
                    .border(
                        width = 2.dp,
                        brush = Brush.linearGradient(
                            listOf(
                                BurracoBlack,
                                BurracoRed,
                                BurracoBlack,
                                BurracoRed
                            )
                        ),
                        shape = RoundedCornerShape(15.dp),
                    )
                    .pointerInput(Unit){detectTapGestures {}},
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp
                ),
                shape = RoundedCornerShape(15.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = BurracoBlack
                ),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = currentTeam ?: "",
                        style = MaterialTheme.typography.titleLarge.copy(fontSize = 30.sp, lineHeight = 32.sp),
                        color = BurracoBlack,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(Modifier.height(10.dp))

                    PointsInputField(
                        team = burracoViewModel.getTeamByName(currentTeam ?: ""),
                        onPointsConfirmed = { name, burraco, point ->
                            burracoViewModel.addScore(name, Pair(burraco, point))
                            onNavigate(Screen.Game.route)
                        },
                        currentRound = burracoViewModel.currentRound.value
                    )
                }
            }
        }

    }
}

//extension function -> estende una classe senza modificare direttamente il codice
fun Modifier.blurIfSupported(radius: Dp): Modifier {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        this.blur(radius)
    } else {
        this // fallback: nessun blur
    }
}

fun Modifier.fakeBlur(): Modifier {
    return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
        this.background(Color(0x9E5B5858)) // grigio semi-trasparente
    } else {
        this // fallback: nessun blur
    }
}
