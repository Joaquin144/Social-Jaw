package com.devcommop.myapplication.ui.components.common.buttons

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun SignOutButton(
    onSignOut: () -> Unit,
    isLoading: Boolean = false,
    modifier: Modifier = Modifier
) {
    var buttonState by remember { mutableStateOf(isLoading) }

    Button(
        onClick = {
            buttonState = true
            onSignOut()
        },
        modifier = modifier,
        enabled = !buttonState
    ) {
        when {
            buttonState -> {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(24.dp)
                            .padding(end = 8.dp),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(text = "Signing Out...")
                }
            }
            else -> Text(text = "Sign Out")
        }
    }

    LaunchedEffect(buttonState) {
        if (buttonState) {
            delay(1000) // Simulating an asynchronous sign-out process
            buttonState = false
        }
    }
}