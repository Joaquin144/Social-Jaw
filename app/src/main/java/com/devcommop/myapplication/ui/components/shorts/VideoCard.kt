package com.devcommop.myapplication.ui.components.shorts


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import com.devcommop.myapplication.data.local.RuntimeQueries
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
    val currentUser = RuntimeQueries.currentUser
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        ShortsHeaderSection(user)
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            if (isPlaying) {
                VideoPlayer(modifier, exoPlayer) { uiVisible ->
                    isPlayerUiVisible = when {
                        isPlayerUiVisible -> uiVisible
                        else -> true
                    }
                }
            } else {

                val thumbnail = videoItem.thumbnail.toString()
                if (thumbnail == null || thumbnail == "null")
                    VideoThumbnail(url = null)
                else VideoThumbnail(url = thumbnail)
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


}
/*

@Composable
fun ShortsHeaderSection(user: User) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = user.profilePictureUrl),
            contentDescription = null,
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = user.userName,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = videoItem.timestamp.toString(),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.DarkGray
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        IconButton(
            onClick = {
                //TODO: show options : delete and report
            }, modifier = Modifier.size(24.dp)
        ) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "Options",
                tint = Color.Black
            )
        }
    }
}


 */
