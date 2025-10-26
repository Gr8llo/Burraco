package com.burraco.app.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.burraco.app.model.Team
import com.burraco.app.ui.theme.BurracoBlack
import com.burraco.app.ui.theme.BurracoBlue
import com.burraco.app.ui.theme.BurracoRed
import com.burraco.app.ui.theme.BurracoTheme
import com.burraco.app.ui.util.isValidPoint

@Composable
fun PointsInputField(
    modifier: Modifier = Modifier,
    team: Team,
    currentRound: Int,
    onValueChage: (Pair<Int, Int>, Boolean) -> Unit
) {

    var burracoPoints by rememberSaveable {
        mutableStateOf(
            team.getScoreRound(currentRound)?.first?.toString() ?: ""
        )
    }
    var cardPoints by rememberSaveable {
        mutableStateOf(
            team.getScoreRound(currentRound)?.second?.toString() ?: ""
        )
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        MinimalPointsInput(
            placeHolder = "Burraco",
            pointInitialValue = burracoPoints,
            onValueChange = { point, pointError ->
                burracoPoints = point
                onValueChage(Pair(burracoPoints.toIntOrNull() ?: 0, cardPoints.toIntOrNull() ?: 0), pointError)
            },
        )
        MinimalPointsInput(
            placeHolder = "Carte",
            pointInitialValue = cardPoints,
            onValueChange = { point, pointError ->
                cardPoints = point
                onValueChage(Pair(burracoPoints.toIntOrNull() ?: 0, cardPoints.toIntOrNull() ?: 0), pointError)
            },
        )
    }
}

@Composable
fun MinimalPointsInput(
    placeHolder: String,
    onValueChange: (String, Boolean) -> Unit,
    pointInitialValue: String,
) {
    var point by rememberSaveable { mutableStateOf(pointInitialValue) }
    var isError by rememberSaveable { mutableStateOf(false) }
    val localFocusManager = LocalFocusManager.current

    TextField(
        value = point,
        onValueChange = {
            point = it
            isError = !point.isValidPoint()
            onValueChange(point, isError)
        },
        singleLine = true,
        textStyle = MaterialTheme.typography.labelSmall.copy(
            textAlign = TextAlign.Center,
            color = BurracoBlack
        ),
        placeholder = {
            Text(
                text = placeHolder,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.SemiBold,
                ),
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        isError = isError,
        supportingText =
            if (isError) {
                {
                    Text(
                        text = "punteggio non valido",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 10.sp,
                            color = BurracoRed
                        ),
                        textAlign = TextAlign.Center, // Centra anche il testo di errore
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            } else {
                null
            },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = { localFocusManager.clearFocus() }
        ),
        colors = TextFieldDefaults.colors(
            // Sfondi trasparenti per renderlo minimalista
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent,

            // Colori per la sottolineatura (indicatore)
            focusedIndicatorColor = BurracoBlue,
            unfocusedIndicatorColor = BurracoBlack, // Nessuna sottolineatura
            errorIndicatorColor = BurracoRed,

            // Colori del cursore
            cursorColor = BurracoBlack,
            errorCursorColor = BurracoRed,
        ),
    )
}

@Preview(showBackground = true)
@Composable
fun Test() {
    BurracoTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            PointsInputField(
                team = Team(
                    name = "Pippo",
//                    scores = listOf(
//                        Pair(100, 1000),
//                        Pair(100, 1000),
//                        Pair(100, 1000),
//                    )
                ),
                currentRound = 1,
                onValueChage = {_,_ -> },
            )
        }

    }
}
