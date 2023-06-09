package com.devcommop.myapplication.ui.components.homescreen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devcommop.myapplication.data.repository.Repository
import com.devcommop.myapplication.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
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

    init {
        fetchPosts()
    }


    fun onEvent(event: HomeScreenEvents) {
        when (event) {
            HomeScreenEvents.Refresh -> {
                //todo: launch a coroutine so that refresh is not called too many times too quickly
            }
        }
    }

    fun fetchPosts() {
        viewModelScope.launch {
            when (val queryStatus = repository.fetchTopKPosts(DEFAULT_POSTS_COUNT)) {
                is Resource.Success -> {
                    userFeed.value.postsList = queryStatus.data ?: emptyList()
                    Log.d(TAG, "the list size in fetchPosts(): ${userFeed.value.postsList.size}")
                }

                is Resource.Error -> {
                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(
                            message = queryStatus.message ?: "Some unknown error occurred"
                        )
                    )
                }

                is Resource.Loading -> TODO()
            }
        }
    }

    sealed class UiEvent() {
        data class ShowSnackbar(val message: String) : UiEvent()
    }
}