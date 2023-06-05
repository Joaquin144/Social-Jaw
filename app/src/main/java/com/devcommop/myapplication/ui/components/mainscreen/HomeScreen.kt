package com.devcommop.myapplication.ui.components.mainscreen

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
//    signOut:()->Unit
) {
    Surface(
        color = Color(0x80FFFFFF)
    ) {
        LazyColumn(
            modifier = Modifier.padding(vertical = 8.dp)
        ){
            items(5){
                FacebookPostItem()
            }
        }
    }
}

