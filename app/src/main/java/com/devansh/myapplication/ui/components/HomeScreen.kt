package com.devansh.myapplication.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SettingUpThroughScaffold() {
    Text("Hey There")
}

@Preview(showBackground = true)
@Composable
fun SetupPreview() {
    SettingUpThroughScaffold()
}
