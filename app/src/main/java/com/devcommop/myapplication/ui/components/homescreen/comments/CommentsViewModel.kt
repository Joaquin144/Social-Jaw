package com.devcommop.myapplication.ui.components.homescreen.comments

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devcommop.myapplication.data.local.RuntimeQueries
import com.devcommop.myapplication.data.model.Comment
import com.devcommop.myapplication.data.model.Post
import com.devcommop.myapplication.data.repository.Repository
import com.devcommop.myapplication.utils.ModelUtils
import com.devcommop.myapplication.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "##@@CommentsVM"

@HiltViewModel
class CommentsViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    companion object {
        var currentPost = Post()
            set(value) {
                field = value
                currentPostState.value = value
            }
        var currentPostState = mutableStateOf(currentPost)
    }

    private val _commentFeedState = mutableStateOf(CommentsFeedState())
    val commentFeedState: State<CommentsFeedState> = _commentFeedState

    private val _commentText = mutableStateOf("")
    val commentText: State<String> = _commentText

    private var user = RuntimeQueries.currentUser

    init {
        Log.d(TAG, "I am initialised")
        val testPostId = "7d-af66-e50e587def8b"//todo: Generalize the postId
    }

    fun getCommentsOnPost(postId: String) {
        viewModelScope.launch {
            when (val commentsFetchStatus =
                repository.getCommentsOnPost(postId = postId)) {
                is Resource.Success -> {
                    val commentsList = commentsFetchStatus.data ?: emptyList()
                    _commentFeedState.value = commentFeedState.value.copy(
                        commentsList = commentsList
                    )
                    Log.d(TAG, "commentsList size is: ${commentsList.size}")
                }

                is Resource.Error -> {
                    //todo: Show error message
                }

                is Resource.Loading -> {}
            }
        }
    }

    fun onEvent(event: CommentsEvent) {
        user = RuntimeQueries.currentUser
        when (event) {
            is CommentsEvent.AddComment -> {
                user?.let { user ->
                    Log.d(TAG, "add comment feature is yet to be implemented")
                    viewModelScope.launch {
                        val comment = Comment(text = event.text)
                        Log.d(TAG, "SubmitPost Event: trying to submit post")
                        if (comment.text.isBlank() || comment.text.isEmpty()) {
                            Log.d(TAG, "SubmitPost Event: Oops post validation failed")
                            return@launch
                        } else {
                            ModelUtils.associateUserAndPostToComment(comment, user, currentPost)
                            val addStatus = repository.addCommentOnPost(currentPost, user, comment)
                            Log.d(TAG, "addCommentStatus of repo call is: ${addStatus.toString()}")
                            when(addStatus){
                                is Resource.Error -> {}
                                is Resource.Loading -> {}
                                is Resource.Success -> { }
                            }
                        }
                    }
                }
            }

            is CommentsEvent.Reload -> {
                Log.d(TAG, "Reload called")
                getCommentsOnPost(postId = currentPost.postId)
            }
        }
    }
}