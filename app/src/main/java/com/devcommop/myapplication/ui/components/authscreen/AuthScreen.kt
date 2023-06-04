package com.devcommop.myapplication.ui.components.authscreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.devcommop.myapplication.ui.navigation.AuthNavGraph

@Composable
fun AuthScreen() {
    val navController = rememberNavController()
    AuthNavGraph(navController = navController)
}

@Preview
@Composable
fun AuthScreenPreview() {
    AuthScreen()
}