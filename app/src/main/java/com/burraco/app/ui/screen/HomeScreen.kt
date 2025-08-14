package com.burraco.app.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieConstants
import com.burraco.app.R
import com.burraco.app.navigation.Screen
import com.burraco.app.ui.components.AnimationPlayer
import com.burraco.app.ui.components.BurracoButton
import com.burraco.app.ui.components.TemporaryMessage
import com.burraco.app.ui.theme.BurracoBlack
import com.burraco.app.ui.theme.BurracoRed

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onNavigate: (String) -> Unit
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            modifier = Modifier
                .padding(15.dp)
                .size(70.dp)
                .align(Alignment.TopStart),
            painter = painterResource(id = R.drawable.club),
            contentDescription = "club",
            colorFilter = ColorFilter.tint(BurracoBlack)
        )
        Image(
            modifier = Modifier
                .padding(15.dp)
                .size(70.dp)
                .align(Alignment.BottomEnd),
            painter = painterResource(id = R.drawable.spade),
            contentDescription = "spade",
            colorFilter = ColorFilter.tint(BurracoBlack)
        )
        Image(
            modifier = Modifier
                .padding(15.dp)
                .size(70.dp)
                .align(Alignment.TopEnd),
            painter = painterResource(id = R.drawable.diamond),
            contentDescription = "diamond",
            colorFilter = ColorFilter.tint(BurracoRed)
        )
        AnimationPlayer(
            modifier = Modifier.size(150.dp).align(Alignment.BottomStart).absoluteOffset(y=(30).dp, x=(-20).dp),
            animation = R.raw.heart_suit,
            iteration = LottieConstants.IterateForever,
            speed = 2f
        )
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Burraco",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(90.dp))

        BurracoButton(onClick = { onNavigate(Screen.Player.route) }, text = "nuova partita") { }

        Spacer(modifier = Modifier.height(15.dp))

        var showMessage by remember { mutableStateOf(false) }

        BurracoButton(onClick = { showMessage = true }, text = "storico") { }

        TemporaryMessage(
            message = "prossimamente",
            visibility = showMessage,
            resetVisibility = { showMessage = false })
    }
}
