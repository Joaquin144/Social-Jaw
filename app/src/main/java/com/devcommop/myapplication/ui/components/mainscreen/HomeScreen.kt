package com.devcommop.myapplication.ui.components.mainscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun HomeScreen(
//    signOut:()->Unit
) {
    Surface(
        color = Color(0x80FFFFFF)
    ) {
        Column() {
            LazyColumn(
                modifier = Modifier
            ) {
                items(count = 5) {
                    PostItem()
                }
            }
        }

    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}