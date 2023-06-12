package com.devcommop.myapplication.ui.components.homescreen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devcommop.myapplication.data.local.RuntimeQueries
import com.devcommop.myapplication.data.repository.Repository
import com.devcommop.myapplication.utils.Constants
import com.devcommop.myapplication.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "##@@HomeScreenVM"
private const val DEFAULT_POSTS_COUNT = 100L

@HiltViewModel
class HomeScreenViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _eventFlow =
        MutableSharedFlow<UiEvent>()   //For some UI events we don't want to show again and again while config changes. eg: Snackbar, Toast, AlertDialog, vibration etc.
    val event = _eventFlow.asSharedFlow()

    private val userFeed = mutableStateOf(UserFeedState(postsList = emptyList()))
    val userFeedState: State<UserFeedState> = userFeed

    private var user = RuntimeQueries.currentUser

    init {
        Log.d(TAG, "I am initialized")
        fetchPosts()
    }


    fun onEvent(event: HomeScreenEvents) {
        //todo: stop using this anti-patter and check for internet permission
        if (user == null) {
            user = RuntimeQueries.currentUser
            viewModelScope.launch {
                delay(1000L) //wait for 1 second so that use gets initialized
            }
            onEvent(event)//recursion
            return //because we don't want to execute when block multiple times
        }
        when (event) {
            is HomeScreenEvents.Refresh -> {
                //todo: launch a coroutine so that refresh is not called too many times too quickly
                fetchPosts()
            }

            is HomeScreenEvents.CommentPost -> {}
            is HomeScreenEvents.DeletePost -> {}
            is HomeScreenEvents.DislikePost -> {}
            is HomeScreenEvents.LikePost -> {
                viewModelScope.launch {
                    when (val likeStatus = user?.let { user -> repository.likePost(event.post, user) }) {
                        is Resource.Error -> _eventFlow.emit(UiEvent.ShowSnackbar(message = Constants.DEFAULT_ERROR_MESSAGE))
                        is Resource.Loading -> {}
                        is Resource.Success -> {Log.d(TAG, "Like success")}
                        else -> {}
                    }
                }
            }

            is HomeScreenEvents.ReportPost -> {}
            is HomeScreenEvents.SharePost -> {}
        }
    }

    fun fetchPosts() {
        viewModelScope.launch {
            when (val queryStatus = repository.fetchLatestKPosts(DEFAULT_POSTS_COUNT)) {
                is Resource.Success -> {
                    userFeed.value.postsList = queryStatus.data ?: emptyList()
                    Log.d(TAG, "the list size in fetchPosts(): ${userFeed.value.postsList.size}")
                }

                is Resource.Error -> {
                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(
                            message = queryStatus.message ?: Constants.DEFAULT_ERROR_MESSAGE
                        )
                    )
                }

                is Resource.Loading -> {}
            }
        }
    }

    sealed class UiEvent() {
        data class ShowSnackbar(val message: String) : UiEvent()
    }
}