package com.devcommop.myapplication.ui.components.shorts

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.devcommop.myapplication.data.model.ShortItem
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues

@Composable
fun ShortsScreen(viewModel: ShortsViewModel = hiltViewModel()) {
    val TAG = "##@@ShortsScreen"
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val exoPlayer = remember(context) { ExoPlayer.Builder(context).build() }
    val listState = rememberLazyListState()

    val videos by viewModel.videos.collectAsState()
    val playingItemIndex by viewModel.currentPlayingIndex.collectAsState()
    var isCurrentItemVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        snapshotFlow {
            listState.visibleAreaContainsItem(playingItemIndex, videos)
        }.collect { isItemVisible ->
            isCurrentItemVisible = isItemVisible
        }
    }

    LaunchedEffect(playingItemIndex) {
        if (playingItemIndex == null) {
            exoPlayer.pause()
        } else {
            val video = videos[playingItemIndex!!]
            if (video.mediaUrl != null) {
                exoPlayer.setMediaItem(
                    MediaItem.fromUri(video.mediaUrl!!),
                    video.lastPlayedPosition
                )
                exoPlayer.prepare()
                exoPlayer.playWhenReady = true
            } else {
                Log.d(TAG, "Media url null for $video")
            }

        }
    }

    LaunchedEffect(isCurrentItemVisible) {
        if (!isCurrentItemVisible && playingItemIndex != null) {
            viewModel.onPlayVideoClick(exoPlayer.currentPosition, playingItemIndex!!)
        }
    }

    DisposableEffect(exoPlayer) {
        val lifecycleObserver = LifecycleEventObserver { _, event ->
            if (playingItemIndex == null) return@LifecycleEventObserver
            when (event) {
                Lifecycle.Event.ON_START -> exoPlayer.play()
                Lifecycle.Event.ON_STOP -> exoPlayer.pause()
                else -> {}
            }
        }

        lifecycleOwner.lifecycle.addObserver(lifecycleObserver)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(lifecycleObserver)
            exoPlayer.release()
        }
    }
    var videoUri by remember {
        mutableStateOf(null as Uri?)
    }
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            videoUri = uri
            videoUri?.let {
                Log.d(TAG, "non null Uri: $it")
                viewModel.postShorts(videoUri)
            }
        }
    )
    Box(
        modifier = Modifier.fillMaxSize()
        ,
        contentAlignment = Alignment.BottomEnd
    ) {
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize()
            ,
            contentPadding = rememberInsetsPaddingValues(
                insets = LocalWindowInsets.current.systemBars,
                applyTop = true,
                applyBottom = true
            )
        ) {
            itemsIndexed(videos, { _, video -> video.id }) { index, video ->
                VideoCard(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background, RoundedCornerShape(2.dp))
                        .safeContentPadding()
                        .clip(RectangleShape)
                        .fillMaxSize(),
                    videoItem = video,
                    exoPlayer = exoPlayer,
                    isPlaying = index == playingItemIndex
                ) {
                    viewModel.onPlayVideoClick(exoPlayer.currentPosition, index)
                }
            }
        }
        Button(
            modifier = Modifier.padding(8.dp),
            onClick = {
                Log.d(TAG, "Button clicked")
                galleryLauncher.launch("video/*")
            }) {
            Text(text = "Create a Short")
        }
    }


}


private fun LazyListState.visibleAreaContainsItem(
    currentlyPlayedIndex: Int?,
    videos: List<ShortItem>
): Boolean {
    return when {
        currentlyPlayedIndex == null -> false
        videos.isEmpty() -> false
        else -> {
            layoutInfo.visibleItemsInfo.map { videos[it.index] }
                .contains(videos[currentlyPlayedIndex])
        }
    }
}