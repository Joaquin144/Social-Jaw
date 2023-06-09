package com.devcommop.myapplication.ui.components.shorts


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.devcommop.myapplication.R

@Composable
fun VideoThumbnail(url: String) {
    Image(
        painter = painterResource(id = R.drawable.default_thumbnail),
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .size(512.dp),
        contentScale = ContentScale.Crop
    )
}