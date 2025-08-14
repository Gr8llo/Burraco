package com.burraco.app.ui.components

import android.icu.text.Edits
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun AnimationPlayer(
    modifier: Modifier = Modifier,
    animation: Int,
    iteration: Int = 1,
    speed: Float = 1f,
    onEnd: ()->Unit = {},
){
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(animation))

    val animationState = animateLottieCompositionAsState(
        composition = composition, //modello in memoria dell'animazione
        iterations = iteration, //come viene eseguito
        speed = speed
    )

    LottieAnimation(
        composition = composition,
        progress = { animationState.progress },
        modifier = modifier,
    )

    LaunchedEffect(animationState.progress) {
        if (animationState.progress == 1f) onEnd()
    }
}