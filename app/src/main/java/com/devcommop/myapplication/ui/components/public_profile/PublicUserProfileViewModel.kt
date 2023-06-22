package com.devcommop.myapplication.ui.components.public_profile

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devcommop.myapplication.data.local.RuntimeQueries
import com.devcommop.myapplication.data.model.User
import com.devcommop.myapplication.data.repository.Repository
import com.devcommop.myapplication.ui.components.homescreen.UserFeedState
import com.devcommop.myapplication.utils.Constants
import com.devcommop.myapplication.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "##@@PublicUserProfVM"

@HiltViewModel
class PublicUserProfileViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val _eventFlow =
        MutableSharedFlow<UiEvent>()   //For some UI events we don't want to show again and again while config changes. eg: Snackbar, Toast, AlertDialog, vibration etc.
    val event = _eventFlow.asSharedFlow()

    private val _userFeed = mutableStateOf(UserFeedState(postsList = emptyList()))
    val userFeed: State<UserFeedState> = _userFeed

    private val _publicUser: MutableState<User?> = mutableStateOf(null)
    val publicUser: State<User?> = _publicUser

    private var user = RuntimeQueries.currentUser
    var publicUserId: String = "null"

    init {
        Log.d(TAG, "I am initialized")
    }

    fun fetchPostsForThisUser() {
        viewModelScope.launch {
            when (val queryStatus = repository.getPostsByUserId(userId = publicUserId)) {
                is Resource.Success -> {
                    val postsList = queryStatus.data ?: emptyList()
                    _userFeed.value = userFeed.value.copy(postsList = postsList)
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

    fun fetchUserData() {
        viewModelScope.launch {
            when (val queryStatus = repository.getUserById(uid = publicUserId)) {
                is Resource.Success -> {
                    val user = queryStatus.data
                    _publicUser.value = user//todo: is it good practice?
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

    fun onEvent(event: PublicUserProfileScreenEvents){
        when(event){
            is PublicUserProfileScreenEvents.CommentPost -> {}
            is PublicUserProfileScreenEvents.DeletePost -> {}
            is PublicUserProfileScreenEvents.DislikePost -> {}
            is PublicUserProfileScreenEvents.LikePost -> {
                viewModelScope.launch {
                    when (val likeStatus = user?.let { user -> repository.likePost(event.post, user) }) {
                        is Resource.Error -> _eventFlow.emit(UiEvent.ShowSnackbar(message = Constants.DEFAULT_ERROR_MESSAGE))
                        is Resource.Loading -> {}
                        is Resource.Success -> {Log.d(TAG, "Like success")}
                        else -> {}
                    }
                }
            }
            is PublicUserProfileScreenEvents.Refresh -> { fetchPostsForThisUser() }
            is PublicUserProfileScreenEvents.ReportPost -> {}
            is PublicUserProfileScreenEvents.SharePost -> {}
        }
    }
}