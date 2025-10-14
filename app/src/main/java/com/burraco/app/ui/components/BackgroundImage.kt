package com.burraco.app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.burraco.app.R

@Composable
fun BackGroundImage(){
    Image(
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.FillBounds,
        alpha = 0.2f,
        painter = painterResource(id = R.drawable.paper_background),
        contentDescription = null,
    )
}