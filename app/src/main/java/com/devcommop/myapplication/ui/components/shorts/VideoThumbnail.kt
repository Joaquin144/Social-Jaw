package com.devcommop.myapplication.ui.components.shorts


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.core.net.toUri
import coil.compose.rememberAsyncImagePainter
import com.devcommop.myapplication.R

@Composable
fun VideoThumbnail(url: String?) {
    Log.d("##@@VideoThumbnail", url.toString())
    val thumbnail = url.toString()
    Image(
        painter = if(url == null || url =="null") painterResource(id = R.drawable.default_thumbnail) else rememberAsyncImagePainter(url.toUri()),
        contentDescription = null,
        modifier = Modifier,
        contentScale = ContentScale.Crop
    )
}