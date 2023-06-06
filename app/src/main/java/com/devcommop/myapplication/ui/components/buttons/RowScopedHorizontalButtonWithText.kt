package com.devcommop.myapplication.ui.components.buttons

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun RowScope.RowScopedHorizontalButtonWithText(
    modifier: Modifier = Modifier,
    icon: ImageVector = Icons.Default.Android,
    onClick: () -> Unit = {},
    text: String = ""
) {
        Card(
            modifier = modifier.padding(horizontal = 4.dp  ),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
        ) {
            Row(
                modifier = Modifier
                    .height(32.dp)
                    .padding(4.dp)
                    .clickable(
                        onClick = { onClick() }
                    ),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    imageVector = icon,
                    contentDescription = "Upload Image",
                    tint = Color.Black
                )
                Text(
                    modifier = Modifier.padding(end = 4.dp ),
                    text = text,
                    color = Color.Black
                )
            }
        }
}

@Preview(showBackground = true)
@Composable
private fun ButtonPreview() {
    Row() {
        RowScopedHorizontalButtonWithText(text = "Button")
        RowScopedHorizontalButtonWithText(text = "Button")
        RowScopedHorizontalButtonWithText(text = "Button")

    }

}
