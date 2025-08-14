package com.burraco.app.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.burraco.app.ui.theme.BurracoRed
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import com.burraco.app.ui.theme.BurracoBlack
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun NameInputField(
    modifier: Modifier = Modifier,
    onDone: (String) -> Unit
) {
    var newPlayer by rememberSaveable { mutableStateOf("") }
    var isError by rememberSaveable { mutableStateOf(true) }

    val focusManager = LocalFocusManager.current

    val onButtonPressed: () -> Unit = {
        when {
            newPlayer.isNotBlank() && !isError -> {
                onDone(newPlayer)
                newPlayer = ""
                focusManager.clearFocus()
            }
        }
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = newPlayer,
            onValueChange = {
                if (it.length <= 13) {
                    newPlayer = it
                    isError = if (it.length > 12 || it.length == 0) true else false
                }
            },
            label = {
                Text(
                    text = "Giocatore",
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
                when {
                    newPlayer.length > 12 -> Text(
                        text = "numero massimo di caratteri raggiunto",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Light
                        )
                    )

                    newPlayer.isBlank() -> Text(
                        text = "inserisci un nome",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Light
                        )
                    )
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = { onButtonPressed() }
            ),
            singleLine = true,
            shape = RoundedCornerShape(15.dp),
            colors = OutlinedTextFieldDefaults.colors(
                // Colori del contenitore
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
            modifier = Modifier.fillMaxWidth(0.5f)
        )

        var isButtonVisible by rememberSaveable { mutableStateOf(false) }
        LaunchedEffect(Unit) { isButtonVisible = false }
        LaunchedEffect(isError) { isButtonVisible = !isError}

        AnimatedVisibility(
            visible = isButtonVisible,
            enter = fadeIn(animationSpec = tween(500)),
            exit = fadeOut(animationSpec = tween(500))
        ) {
            BurracoButton (text="OK", onClick = {onButtonPressed()}, modifier = Modifier.height(50.dp))
        }
    }
}