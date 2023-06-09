package com.devcommop.myapplication.ui.components.shorts


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.TapAndPlay
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.media3.exoplayer.ExoPlayer
import com.devcommop.myapplication.data.model.ShortItem

@Composable
fun VideoCard(
    modifier: Modifier = Modifier,
    videoItem: ShortItem,
    isPlaying: Boolean,
    exoPlayer: ExoPlayer,
    onClick: () -> Unit
) {
    val TAG = "##@@VideoCard"
    var isPlayerUiVisible by remember { mutableStateOf(false) }
    val isPlayButtonVisible = if (isPlayerUiVisible) true else !isPlaying

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Black, RoundedCornerShape(4.dp))
            .clip(RoundedCornerShape(4.dp)),
        contentAlignment = Alignment.Center
    ) {
        if (isPlaying) {
            VideoPlayer(exoPlayer) { uiVisible ->
                isPlayerUiVisible = when {
                    isPlayerUiVisible -> uiVisible
                    else -> true
                }
            }
        } else {
            if(videoItem.thumbnail.isNullOrEmpty()){
                Log.d(TAG , "videoItem.thumbnail is null or empty")
            }
            else {
                VideoThumbnail(videoItem.thumbnail!!)
            }
        }
        if (isPlayButtonVisible) {
            Icon(
                imageVector = (if (isPlaying) (Icons.Default.Pause) else Icons.Default.TapAndPlay),
                contentDescription = "",
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(72.dp)
                    .clip(remember { RoundedCornerShape(percent = 50) })
                    .clickable(onClick = onClick)
            )
        }
    }
}