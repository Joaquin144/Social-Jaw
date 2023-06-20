package com.devcommop.myapplication.ui.components.homescreen.comments

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devcommop.myapplication.data.repository.Repository
import com.devcommop.myapplication.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "##@@CommentsVM"

@HiltViewModel
class CommentsViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val _commentText = mutableStateOf("")
    val commentText: State<String> = _commentText

    private val _commentFeedState = mutableStateOf(CommentsFeedState())
    val commentFeedState: State<CommentsFeedState> = _commentFeedState

    init {
        Log.d(TAG, "I am initialised")
        val testPostId = "7d-af66-e50e587def8b"//todo: Generalize the postId
        getCommentsOnPost(postId = testPostId)
    }

    fun getCommentsOnPost(postId: String) {
        viewModelScope.launch {
            when (val commentsFetchStatus = repository.getCommentsOnPost(postId = postId)) {
                is Resource.Success -> {
                    _commentFeedState.value = commentFeedState.value.copy(
                        commentsList = commentsFetchStatus.data ?: emptyList()
                    )
                }

                is Resource.Error -> {
                    //todo: Show error message
                }

                is Resource.Loading -> {}
            }
        }
    }

    fun onEvent(event: CommentsEvent) {
        when(event){
            is CommentsEvent.AddComment -> {
                Log.d(TAG, "add comment feature is yet to be implemented")
                //when (val addCommentStatus = repository.addCommentOnPost())
            }
        }
    }
}