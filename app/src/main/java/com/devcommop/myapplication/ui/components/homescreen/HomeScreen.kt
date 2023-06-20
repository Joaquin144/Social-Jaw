package com.devcommop.myapplication.ui.components.homescreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun HomeScreen(
    navController: NavController, viewModel: HomeScreenViewModel = hiltViewModel()
) {
    val userFeedState = viewModel.userFeed.value
    Surface(
        color = Color(0x80FFFFFF)
    ) {
        Column {
            LazyColumn(
                modifier = Modifier
            ) {
                items(userFeedState.postsList) { post ->
                    PostItem(
                        post = post,
                        onLikeButtonClick = { viewModel.onEvent(HomeScreenEvents.LikePost(post)) },
                        onCommentClick = { viewModel.onEvent(HomeScreenEvents.CommentPost(post)) },
                        onShareClick = { viewModel.onEvent(HomeScreenEvents.SharePost(post)) },
                    )
                }
            }
        }

    }
}

//@Preview
//@Composable
//fun HomeScreenPreview() {
//    //HomeScreen()
//}