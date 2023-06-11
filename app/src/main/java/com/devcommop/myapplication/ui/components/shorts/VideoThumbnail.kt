package com.devcommop.myapplication.ui.components.shorts


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.rememberAsyncImagePainter
import com.devcommop.myapplication.R

@Composable
fun VideoThumbnail(modifier : Modifier , url: String?) {
    Image(
        painter = if( url != null ) rememberAsyncImagePainter(url)  else  painterResource(id = R.drawable.default_thumbnail),
        contentDescription = null,
        modifier = modifier
            .safeContentPadding()
            .aspectRatio(16f / 16f )
            ,
        contentScale = ContentScale.Crop
    )
}