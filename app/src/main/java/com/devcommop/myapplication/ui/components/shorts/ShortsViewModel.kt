package com.devcommop.myapplication.ui.components.shorts

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devcommop.myapplication.data.local.RuntimeQueries
import com.devcommop.myapplication.data.model.ShortItem
import com.devcommop.myapplication.data.repository.Repository
import com.devcommop.myapplication.utils.CommonUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShortsViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val TAG = "##@@ShortsViewModel"
    private var _videos = MutableStateFlow<List<ShortItem>>(listOf())
    val videos get() = _videos
    private var _currentPlayingIndex = MutableStateFlow<Int?>(null)
    val currentPlayingIndex get() = _currentPlayingIndex

    init {
        _currentPlayingIndex.value = null // reset currentPlayingIndex
        fetchShorts()
    }
    fun postShorts(videoUri : Uri?) = viewModelScope.launch {
        val currentUser = RuntimeQueries.currentUser
        if(videoUri != null && currentUser != null ){
            var short = ShortItem(
                id = CommonUtils.getAutoId(),
                createdBy = currentUser.uid,
                timestamp = System.currentTimeMillis().toString(),
                mediaUrl = videoUri.toString(),
                thumbnail = null,
                lastPlayedPosition = 0,
                title = "${currentUser.userName} ${System.currentTimeMillis()} }",
                description = null,
                likes = 0,
                comments = 0,
                views = 0,
                likesList = null,
                commentsList = null,
                tags = null,
                duration = 0
            )
            repository.postShorts(short, currentUser)
        }
        else{
            return@launch
        }
    }
    private fun fetchShorts() {
        viewModelScope.launch {
           val response =  repository.fetchShortsFromDB()
            if(!response.data.isNullOrEmpty()) {
                _videos.value = response.data
            }
            else{
                Log.d(TAG, "fetchShorts: ${response.message}")
            }

        }
    }

    //  repository.createShortsEntryInFirestoreDB()

    // @param videoIndex The index of the video to be played
    // @param playbackPosition The position of the last video
    fun onPlayVideoClick(playbackPosition: Long, videoIndex: Int) {
        when (_currentPlayingIndex.value) {
            // video is not playing at the moment
            null -> _currentPlayingIndex.value = videoIndex
            // this video is already playing
            videoIndex -> {
                _currentPlayingIndex.value = null
                videos.value = videos.value.toMutableList().also { list ->
                    list[videoIndex] =
                        list[videoIndex].copy(lastPlayedPosition = playbackPosition)
                }
            }
            // video is playing, and we're requesting new video to play
            else -> {
                videos.value = videos.value.toMutableList().also { list ->
                    list[_currentPlayingIndex.value!!] =
                        list[_currentPlayingIndex.value!!].copy(lastPlayedPosition = playbackPosition)
                }
                _currentPlayingIndex.value = videoIndex
            }
        }
    }
}
