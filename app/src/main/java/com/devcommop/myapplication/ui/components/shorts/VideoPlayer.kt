package com.devcommop.myapplication.ui.components.shorts

import android.view.LayoutInflater
import android.view.View
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.devcommop.myapplication.R

@Composable
fun VideoPlayer(
    modifier : Modifier = Modifier,
    exoPlayer: ExoPlayer,
    onControllerVisibilityChanged: (uiVisible: Boolean) -> Unit
) {
    val context = LocalContext.current
    val playerView = remember {
        val layout = LayoutInflater.from(context).inflate(R.layout.video_player, null, false)
        val playerView = layout.findViewById(R.id.playerView) as PlayerView
        playerView.apply {
            setControllerVisibilityListener(PlayerView.ControllerVisibilityListener {
                onControllerVisibilityChanged(it == View.VISIBLE)
            })
            player = exoPlayer
        }
    }

    AndroidView(
        { playerView },
        modifier = modifier
            .aspectRatio(9f / 16f)
//            .fillMaxSize()
//            .background(Color.Black)
    )
}