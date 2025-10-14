package com.burraco.app.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.burraco.app.model.Team
import com.burraco.app.ui.theme.BurracoBlack
import com.burraco.app.ui.theme.BurracoRed

@Composable
fun PointsInputField(
    team: Team?,
    currentRound: Int,
    onPointsConfirmed: (String, Int, Int) -> Unit
) {

    var burracoPoints by rememberSaveable {
        mutableStateOf(
            team?.getScoreRound(currentRound)?.first?.toString() ?: ""
        )
    }
    var cardPoints by rememberSaveable {
        mutableStateOf(
            team?.getScoreRound(currentRound)?.second?.toString() ?: ""
        )
    }

    var burracoError by rememberSaveable { mutableStateOf(false) }
    var cardError by rememberSaveable { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        OutlinedInputField(
            label = "Punti - Burraco",
            onValueChange = { point -> burracoPoints = point },
            updateError = { isError -> burracoError = isError },
            pointInitialValue = if (burracoPoints=="0") "" else burracoPoints,
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedInputField(
            label = "Punti - Carte",
            onValueChange = { point -> cardPoints = point },
            updateError = { isError -> cardError = isError },
            pointInitialValue = if(cardPoints=="0") "" else cardPoints,
        )
        Spacer(modifier = Modifier.height(20.dp))

        BurracoButton(
            text = "conferma",
            onClick = {
                onPointsConfirmed(
                    team?.name ?: "",
                    (burracoPoints.toIntOrNull() ?: 0),
                    (cardPoints.toIntOrNull() ?: 0)
                )
            },
            enabled = !burracoError && !cardError
        )
    }
}

@Composable
fun OutlinedInputField(
    modifier: Modifier = Modifier,
    label: String,
    onValueChange: (String) -> Unit,
    updateError: (Boolean) -> Unit,
    pointInitialValue: String,
) {
    var point by rememberSaveable { mutableStateOf(pointInitialValue) }
    var isError by rememberSaveable { mutableStateOf(false) }
    val localFocusManager = LocalFocusManager.current

    OutlinedTextField(
        value = point,
        onValueChange = {
            point = it
            onValueChange(point)
            isError = !point.isValidPoint()

            updateError(isError)
        },
        label = {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Light,
                    background = Color.Transparent
                )
            )
        },
        enabled = true,
        isError = isError,
        supportingText = {
            if (isError)
                Text(
                    text = "punteggio non valido",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Light
                    )
                )
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Number
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                onValueChange(point)
                localFocusManager.clearFocus()
            }
        ),
        singleLine = true,
        shape = RoundedCornerShape(15.dp),
        colors = OutlinedTextFieldDefaults.colors(
//                // Colori del contenitore
            errorContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,


            // Colori del cursore
            cursorColor = BurracoBlack,

            //Colori Bordo
            errorBorderColor = BurracoRed,
            focusedBorderColor = BurracoBlack,

            // Colori della label
            focusedLabelColor = BurracoRed,
            errorLabelColor = Color.Red,

            // Colore del testo di supporto (es. messaggi di errore)
            errorSupportingTextColor = BurracoRed
        ),
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "0") }
    )
}

fun String.isValidPoint(): Boolean {
    return when {
        (this.toIntOrNull() ?: 0) % 5 != 0 -> false
        (this.toIntOrNull() ?: 0) >= 1500 -> false
        (this.toIntOrNull() ?: 0) <= -1500 -> false
        this.any { !it.isDigit() && it != '-' } -> false
         else -> true
    }
}