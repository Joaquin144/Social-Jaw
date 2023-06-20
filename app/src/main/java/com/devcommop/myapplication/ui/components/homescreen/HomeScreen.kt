package com.devcommop.myapplication.ui.components.homescreen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.devcommop.myapplication.data.model.Post
import com.devcommop.myapplication.ui.components.homescreen.comments.CommentsScreen
import com.devcommop.myapplication.ui.components.homescreen.comments.CommentsViewModel
import kotlinx.coroutines.launch

private const val TAG = "##@@HomeScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController, viewModel: HomeScreenViewModel = hiltViewModel()
) {
    val bottomSheetState = rememberStandardBottomSheetState(
        initialValue = SheetValue.Hidden, skipHiddenState = false
    )
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = bottomSheetState
    )
    val scope = rememberCoroutineScope()

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            CommentsScreen(
                onClose = {
                    scope.launch {
                        bottomSheetState.hide()
                    }
                }//todo: toggle off the bottom sheet
            )
        },
        sheetPeekHeight = 0.dp,
    ) {
        val userFeedState = viewModel.userFeed.value
        Surface(
            color = Color(0x80FFFFFF)
        ) {
            LazyColumn(
                modifier = Modifier
            ) {
                items(userFeedState.postsList) { post ->
                    PostItem(post = post,
                        onLikeButtonClick = { viewModel.onEvent(HomeScreenEvents.LikePost(post)) },
                        onCommentClick = {
                            CommentsViewModel.currentPost = post
                            Log.d(TAG, "changing post for CommentsViewModel = ${post.postId}")
                            scope.launch { bottomSheetState.expand() }
                        },
                        onShareClick = { viewModel.onEvent(HomeScreenEvents.SharePost(post)) }
                    )
                }
            }
        }
    }

    /**
    //---------------Old UI---------------
    val userFeedState = viewModel.userFeed.value
    Surface(
    color = Color(0x80FFFFFF)
    ) {
    Column {
    LazyColumn(
    modifier = Modifier
    ) {
    items(userFeedState.postsList) { post ->
    PostItemWrapper(post = post,
    onLikeButtonClick = { viewModel.onEvent(HomeScreenEvents.LikePost(post)) },
    onShareClick = { viewModel.onEvent(HomeScreenEvents.SharePost(post)) })
    //                    PostItem(
    //                        post = post,
    //                        onLikeButtonClick = { viewModel.onEvent(HomeScreenEvents.LikePost(post)) },
    //                        onCommentClick = { viewModel.onEvent(HomeScreenEvents.CommentPost(post)) },
    //                        onShareClick = { viewModel.onEvent(HomeScreenEvents.SharePost(post)) },
    //                    )
    }
    }
    }

    }
     **/
}