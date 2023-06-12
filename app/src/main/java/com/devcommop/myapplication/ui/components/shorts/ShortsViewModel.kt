package com.devcommop.myapplication.ui.components.shorts

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devcommop.myapplication.data.local.RuntimeQueries
import com.devcommop.myapplication.data.model.ShortItem
import com.devcommop.myapplication.data.repository.Repository
import com.devcommop.myapplication.utils.CommonUtils
import com.devcommop.myapplication.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShortsViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val TAG = "##@@ShortsViewModel"
    private var _eventFlow = MutableSharedFlow<UIEvents>()
    val event = _eventFlow.asSharedFlow()
    private var _videos = MutableStateFlow(ShortsFeedState(shortsList = mutableListOf()))
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

    fun onEvent(event : ShortsScreenEvents ){
        event.apply{
            when( this ){
                is ShortsScreenEvents.Refresh -> {
                    fetchShorts()
                }
                is ShortsScreenEvents.LikeShort -> {
                    //TODO: Like short with event.value as shortItem
                }
                is ShortsScreenEvents.CommentShort -> {
                    //TODO: comment short with event.value as shortItem
                }
                is ShortsScreenEvents.ReportShort -> {
                    //TODO: report short with event.value as shortItem
                }
                is ShortsScreenEvents.ShareShort -> {
                    //TODO: share short with event.value as shortItem
                }
                is ShortsScreenEvents.DeleteShort -> {
                    //TODO: delete short with event.value as shortItem
                }


            }
        }
    }

    fun getUserByUserId(id :String ){
        viewModelScope.launch {
            val response = repository.getUserById(id)
            when(response){
                is Resource.Success -> {

                }
                is Resource.Error -> {
                    _eventFlow.emit(UIEvents.ShowSnackbar(response.message?: "Unknown error occurred"))
                }
                is Resource.Loading -> {
                    _eventFlow.emit(UIEvents.ShowProgressBar)
                }
            }
        }
    }

    fun fetchShorts() {
        viewModelScope.launch {
           when(val response =  repository.fetchShortsFromDB()){
               is Resource.Success -> {
                   // Log.d(TAG, "fetchShorts: ${response.data}")
                   //TODO: UI event show shorts
                   response.data?.let{
                       _videos.value = ShortsFeedState(shortsList = it.toMutableList())
                   } ?: kotlin.run {
                       _videos.value = ShortsFeedState(shortsList = mutableListOf())
                   }
               }
               is Resource.Error -> {
//                     show snackbarUi event
                   _eventFlow.emit(UIEvents.ShowSnackbar(response.message?: "Unknown error occurred"))
               }
               is Resource.Loading -> {
                   // show circular progress bar
                   _eventFlow.emit(UIEvents.ShowProgressBar)
               }
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
                videos.value = ShortsFeedState(videos.value.shortsList.toMutableList()).also { state ->
                    val list = state.shortsList
                    list[videoIndex] =
                        list[videoIndex].copy(lastPlayedPosition = playbackPosition)
                }
            }
            // video is playing, and we're requesting new video to play
            else -> {
                videos.value = ShortsFeedState(videos.value.shortsList.toMutableList()).also { state ->
                    val list = state.shortsList
                    list[_currentPlayingIndex.value!!] =
                        list[_currentPlayingIndex.value!!].copy(lastPlayedPosition = playbackPosition)
                }
                _currentPlayingIndex.value = videoIndex
            }
        }
    }
    sealed class UIEvents{
        object ShowProgressBar : UIEvents()
        data class ShowSnackbar(val message: String) : UIEvents()
    }
}
