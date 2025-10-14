package com.burraco.app.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.burraco.app.ui.theme.BurracoBlack
import com.burraco.app.ui.theme.BurracoRed

@Composable
fun RoundRow(
    score: Pair<Int, Int>,
    modifier: Modifier=Modifier,
    firstRoundTo1000: Boolean = false,
){
    Row {
        Column (
            modifier = modifier.weight(1f).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Text(
                modifier = modifier,
                text = score.first.toString(),
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = if (score.first < 0) BurracoRed else BurracoBlack
                )
            )
            Text(
                modifier = modifier,
                text = score.second.toString(),
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = if (score.second < 0) BurracoRed else BurracoBlack
                )
            )
        }
        Box(
            modifier = Modifier
                .absoluteOffset(x = (-30).dp)
                .align(Alignment.CenterVertically)
        ) {
            Text(
                text = "+",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
    HorizontalDivider(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp),
        color = if (firstRoundTo1000) BurracoRed else Color.Gray,
        thickness = 2.dp
    )
}