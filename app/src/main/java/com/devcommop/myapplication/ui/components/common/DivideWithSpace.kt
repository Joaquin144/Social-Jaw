package com.devcommop.myapplication.ui.components.common

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DividerWithSpace(dividerThickness: Int = 1, spaceGap: Int = 2) {
    Spacer(modifier = Modifier.height(spaceGap.dp))
    Divider(modifier = Modifier.height(dividerThickness.dp))
    Spacer(modifier = Modifier.height(spaceGap.dp))
}
