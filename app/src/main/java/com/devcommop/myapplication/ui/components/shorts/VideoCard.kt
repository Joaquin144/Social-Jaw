package com.devcommop.myapplication.ui.components.shorts


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (isPlaying) {
            VideoPlayer(modifier , exoPlayer) { uiVisible ->
                isPlayerUiVisible = when {
                    isPlayerUiVisible -> uiVisible
                    else -> true
                }
            }
        } else {
            videoItem.thumbnail?.let{
                VideoThumbnail(modifier , url = null )
            } ?: VideoThumbnail(modifier , videoItem.thumbnail)
//
        }
        if (isPlayButtonVisible) {
            Icon(
                imageVector = (if (isPlaying) (Icons.Default.Pause) else Icons.Default.PlayArrow),
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