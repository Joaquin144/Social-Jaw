package com.devcommop.myapplication.ui.components.settings

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun ProfileCardUI(
    image_url: String
) {
    AsyncImage(
        model =  image_url ,
        contentDescription = "Profile Image",
        modifier = Modifier
//                        .aspectRatio(1f)
            .size(160.dp)
            .clip(CircleShape),
        contentScale = ContentScale.Crop
    )
}