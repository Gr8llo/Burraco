package com.burraco.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.burraco.app.model.Team
import com.burraco.app.R
import com.burraco.app.navigation.Screen
import com.burraco.app.ui.theme.BurracoBlack
import com.burraco.app.ui.theme.BurracoRed

@Composable
fun TeamPointsColumn(
    team: Team,
    modifier: Modifier = Modifier,
    isLast: Boolean = false,
    insertingNewRound: Boolean = false,
    onClick: () -> Unit,
    onNavigate: (String) -> Unit,
    getGlobalPos: (Offset) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = team.name,
            style = MaterialTheme.typography.titleLarge.copy(fontSize = 35.sp),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth(),
            thickness = 2.dp,
            color = Color.Gray
        )

        team.scores.forEachIndexed { index, score ->
            RoundRow(
                score = score,
            )
        }

        if (insertingNewRound) ShowInputBox(onClick = onClick)
        else ShowTotalScore(team = team, onNavigate = onNavigate)

        Spacer(Modifier
            .height(0.dp)
            .onGloballyPositioned { coordinates ->
                getGlobalPos(coordinates.positionInWindow())
            }
        )
    }

    if (!isLast) VerticalDivider(
        modifier = Modifier
            .fillMaxHeight(0.98f),
        color = Color.Gray,
        thickness = 2.dp
    )
}


@Composable
fun ShowTotalScore(
    modifier: Modifier = Modifier,
    team: Team,
    onNavigate: (String) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(end = 15.dp)
    ) {
        Text(
            text = team.totalScore.toString(),
            fontWeight = FontWeight.Bold,
            color = when {
                team.totalScore < 0 -> BurracoRed
                team.totalScore >= 2005 -> Color(0xFFCEB12C)
                else -> BurracoBlack
            },
            modifier = Modifier.align(Alignment.Center)
        )

        if (team.round != 0) {
            Icon(
                painter = painterResource(R.drawable.edit_note_icon),
                tint = BurracoRed,
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.CenterEnd)
                    .clickable {
                        onNavigate(Screen.ScoreEntry.route + "/${team.name}")
                    },
                contentDescription = "edit"
            )
        }
    }
}

@Composable
fun ShowInputBox(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Spacer(Modifier.height(10.dp))

    Box(
        modifier = Modifier
            .clickable(
                true,
                onClickLabel = "inserisci qui i punti",  //descrive l'azione testualemente (per i non vedenti)
                onClick = onClick
            )
            .clip(RoundedCornerShape(10.dp))
            .background(Color.LightGray.copy(alpha = 1f))
            .fillMaxWidth(0.9f)
            .height(80.dp)
    ) {
        Text(
            text = "",
            modifier = Modifier.align(Alignment.Center),
            color = Color.White
        )
    }
}