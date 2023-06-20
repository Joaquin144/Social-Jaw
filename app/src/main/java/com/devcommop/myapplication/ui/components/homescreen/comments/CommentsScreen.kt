package com.devcommop.myapplication.ui.components.homescreen.comments

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.devcommop.myapplication.data.model.Post

private const val TAG = "##@@CommentsScreen"

@Composable
fun CommentsScreen(
    onClose: () -> Unit = {},
    onCommentSubmit: () -> Unit = {}
) {
    val viewModel: CommentsViewModel = hiltViewModel()
    Column(modifier = Modifier
        .fillMaxWidth()
        .height(500.dp)) {
        // Cross(X) button on top right corner
        IconButton(
            onClick = { onClose() },
            modifier = Modifier
                .align(Alignment.End)
                .padding(8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close"
            )
        }

        // A TextField for user to write comment
        var commentText = viewModel.commentText.value
        TextField(
            value = commentText,
            onValueChange = { commentText = it },
            label = { Text("Write a comment") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )

        // A list of comments from other users
        val commentsList = viewModel.commentFeedState.value.commentsList
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(commentsList) { comment ->
                CommentItem(comment)
            }
        }

        // Submit button for adding the user's comment
        Button(
            onClick = { onCommentSubmit() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(text = "Submit")
        }
    }

    LaunchedEffect(key1 = CommentsViewModel.currentPostState.value){
        Log.d(TAG, "PostId for which comments are being fetched is: ${CommentsViewModel.currentPost}")
        //viewModel.getCommentsOnPost()
        viewModel.onEvent(CommentsEvent.Reload)
    }
}