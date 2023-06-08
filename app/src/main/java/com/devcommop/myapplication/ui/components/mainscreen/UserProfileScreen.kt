package com.devcommop.myapplication.ui.components.mainscreen


import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material3.Divider
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.devcommop.myapplication.R
import com.devcommop.myapplication.ui.components.common.buttons.RowScopedHorizontalButtonWithText
import com.devcommop.myapplication.ui.components.viewmodel.AuthViewModel


@Composable
fun UserProfileScreen(
//    userData : UserData?,
    onSignOut: () -> Unit
) {
//    val viewModel: AuthViewModel = hiltViewModel()
    val viewModel: AuthViewModel = viewModel(LocalContext.current as ComponentActivity)

    val userData = viewModel.userData.collectAsState().value
    var showProgressBar by remember {
        mutableStateOf(false)
    }
    val TAG = "##@@USER_DATA_SCREEN"
    val context = LocalContext.current
    Log.d(TAG, userData.toString())

//        if (userData?.username != null) {
//            Text(
//                text = userData.username ?: "null",
//                textAlign = TextAlign.Center,
//                fontSize = 36.sp,
//                fontWeight = FontWeight.SemiBold
//            )
//            Spacer(modifier = Modifier.height(16.dp))
//        }
//        SignOutButton(onSignOut = onSignOut)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        // First Row - Username
        Text(
            text = "Username", // TODO: Replace with userData.username
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier
                .padding(horizontal = 2.dp)
                .align(Alignment.Start)
        )

        // Second Row - Profile Image and Follower/Following Count
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (userData.profilePictureUrl != null) {
                AsyncImage(
                    model = userData.profilePictureUrl,
                    contentDescription = "Profile picture",
                    modifier = Modifier
//                        .aspectRatio(1f)
                        .size(140.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                Image(
                    bitmap = ImageBitmap.imageResource(R.drawable.dummy_profile_picture), // Replace with your own image resource
                    contentDescription = "Profile Image",
                    modifier = Modifier
//                        .aspectRatio(1f)
                        .size(140.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }
            Column(
                modifier = Modifier ,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(
                    text = "${userData.username}", // TODO: Replace with userData.username
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                )
                {
                    VerticalLayoutButtonWithText("5", {}, "Posts")
                    VerticalLayoutButtonWithText("29", {}, "Following")
                    VerticalLayoutButtonWithText("1M", {}, "Followers")

                }

            }

        }
        Divider(
            color = Color.Black,
            thickness = 1.dp,
            modifier = Modifier.padding(horizontal = 1.dp)
        )


        // Third Row - Edit Profile and Share Profile Buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.weight(1f))

            RowScopedHorizontalButtonWithText(
//                Modifier
//                    .padding(horizontal = 4.dp),
                icon = Icons.Default.Edit,
                onClick = { /* Handle edit profile button click(navigate to edit profile) */ },
                text = "Edit Profile"
            )

            RowScopedHorizontalButtonWithText(
//                Modifier
//                    .padding(horizontal = 4.dp),
                icon = Icons.Default.Share,
                onClick = { /* Handle share profile button click */ },
                text = "Share Profile"
            )
            Spacer(modifier = Modifier.weight(1f))

        }

        // Fourth Row - Photos, Videos, and Tagged Posts
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.weight(1f))

            RowScopedHorizontalButtonWithText(
//                Modifier
//                    .padding(horizontal = 4.dp),
                icon = Icons.Default.Edit,
                onClick = { /* TODO: show Photos : screen */ },
                text = "Photos"
            )
            RowScopedHorizontalButtonWithText(
//                Modifier
//                    .padding(horizontal = 4.dp),
                icon = Icons.Default.VideoLibrary,
                onClick = { /* TODO: show Reels : screen */ },
                text = "Reels"
            )
            RowScopedHorizontalButtonWithText(
//                Modifier
//                    .padding(horizontal = 4.dp),
                icon = Icons.Default.Tag,
                onClick = { /* TODO: show Tagged Posts : screen */ },
                text = "Tagged"
            )
            Spacer(modifier = Modifier.weight(1f))

        }
        viewModel.showAllPost()
        val posts = viewModel.mUserPosts.collectAsState().value
        Log.d(TAG , "posts fetched from db: ${posts.toString()}")
//        LazyColumn(){
//            items(posts.size){ index ->
//                userData.username?.let { it1 ->
//                    posts[index].textContent?.let {
//                        posts[index].timestamp?.let { it2 ->
//                            PostItem(
//                                username = it1,
//                                userProfileIcon = userData.profilePictureUrl,
//                                timePosted = it2,
//                                contentDescription = it,
//                                postImage = posts[index].imagesUrl
//                            )
//                        }
//                    }
//                }
//            }
//        }
    }

}

@Composable
fun VerticalLayoutButtonWithText(count: String, onClick: () -> Unit, buttonText: String) {
    Column(
        modifier = Modifier
            .padding(horizontal = 2.dp)
            .clickable(onClick = { onClick() }),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = count, // Replace this with following count of Current User
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = LocalContentColor.current.copy(alpha = 0.9f)
        )
        Text(
            text = buttonText,
            style = MaterialTheme.typography.bodyMedium,
            color = LocalContentColor.current
        )
    }
}

//@Composable
//fun RowScope.HorizontalLayoutButtonWithText(
//    onClick: () -> Unit,
//    buttonText: String,
//    icon: ImageVector
//) {
//    Row(
//        modifier = Modifier
//            .weight(1f)
//            .clickable(onClick = { onClick() }),
//        verticalAlignment = Alignment.CenterVertically,
//
//        ) {
//        Icon(
//            imageVector = icon, contentDescription = buttonText, tint = Color.Black,
//            modifier = Modifier
//                .size(24.dp)
//                .padding(end = 4.dp)
//        )
//        Text(
//            text = buttonText,
//            style = MaterialTheme.typography.bodyMedium,
//            color = LocalContentColor.current.copy(alpha = 0.6f)
//        )
//    }
//}


@Preview(showBackground = true)
@Composable
fun UserScreenPreview() {
    UserProfileScreen {}
}

