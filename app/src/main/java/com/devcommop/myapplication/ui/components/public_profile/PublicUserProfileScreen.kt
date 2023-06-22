package com.devcommop.myapplication.ui.components.public_profile

import android.util.Log
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.devcommop.myapplication.data.model.User
import com.devcommop.myapplication.ui.components.homescreen.PostItem
import com.devcommop.myapplication.ui.components.homescreen.comments.CommentsScreen
import com.devcommop.myapplication.ui.components.homescreen.comments.CommentsViewModel
import kotlinx.coroutines.launch

private const val TAG = "##@@PublicUserProfScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PublicUserProfileScreen(
    navController: NavController,
    userId: String,
    viewModel: PublicUserProfileViewModel = hiltViewModel()
) {
    val bottomSheetState = rememberStandardBottomSheetState(
        initialValue = SheetValue.Hidden, skipHiddenState = false
    )
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = bottomSheetState
    )
    val scope = rememberCoroutineScope()
    val userFeedState = viewModel.userFeed.value
    val publicUser = viewModel.publicUser.value

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
        Surface(
            color = Color(0x80FFFFFF)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                UserDetailsSection(publicUser  = publicUser)
                Spacer(modifier = Modifier.height(12.dp))
                LazyColumn(
                    modifier = Modifier
                ) {
                    items(userFeedState.postsList) { post ->
                        PostItem(post = post,
                            onLikeButtonClick = { viewModel.onEvent(PublicUserProfileScreenEvents.LikePost(post)) },
                            onCommentClick = {
                                CommentsViewModel.currentPost = post
                                Log.d(TAG, "changing post for CommentsViewModel = ${post.postId}")
                                scope.launch { bottomSheetState.expand() }
                            },
                            onShareClick = { viewModel.onEvent(PublicUserProfileScreenEvents.SharePost(post)) },
                            //onUserProfilePicClick = { } --> No navigation from here since all posts are of user itself
                        )
                    }
                }
            }
        }
    }

    LaunchedEffect(key1 = true){
        viewModel.publicUserId = userId
        viewModel.fetchPostsForThisUser()
        viewModel.fetchUserData()
    }
}

@Composable
fun UserDetailsSection(publicUser: User?) {
    publicUser?.let{user ->
        user.fullName?.let {
            Log.d(TAG, "user.fullName: ${user.fullName}")
            Text(
                text = it,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        Text(text = "Gender: ${user.gender?:"Undefined"}")
        user.relationshipStatus?.let{
            Text(text = "Relationship Status: $it")
        }
        if(user.city != null && user.country != null){
            Text(text = "Location: ${user.city}, ${user.country}")
        }
        user.bio?.let{
            Text(
                text = "Bio: $it",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
            )
        }
        Row {
            Text(
                text = "Followers: ${user.followersCount?:0}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "Following: ${user.followingCount?:0}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1f)
            )
        }
    }
}
