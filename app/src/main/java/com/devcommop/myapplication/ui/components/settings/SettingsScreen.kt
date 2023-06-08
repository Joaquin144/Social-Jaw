package com.devcommop.myapplication.ui.components.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.devcommop.myapplication.ui.navigation.SetupSettingNavigation

@Composable
fun SettingsScreen(
    onSignOut: () -> Unit = {}
) {
    val navController = rememberNavController()
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        SetupSettingNavigation(navController, onSignOut = onSignOut)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewSettingsScreen() {
    SettingsScreen()
}




