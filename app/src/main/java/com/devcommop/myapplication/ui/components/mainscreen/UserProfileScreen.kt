package com.devcommop.myapplication.ui.components.mainscreen


import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material3.Card
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.devcommop.myapplication.data.local.RuntimeQueries
import com.devcommop.myapplication.ui.components.common.DividerWithSpace
import com.devcommop.myapplication.ui.components.common.ProfileHeaderSection
import com.devcommop.myapplication.ui.components.common.buttons.RowScopedHorizontalButtonWithText
import com.devcommop.myapplication.ui.components.viewmodel.AuthViewModel


@Composable
fun UserProfileScreen(
    onNavigateToEditUserProfileScreen: () -> Unit = {},
    onSignOut: () -> Unit = {}
) {
//    val viewModel: AuthViewModel = hiltViewModel()
    val viewModel: AuthViewModel = viewModel(LocalContext.current as ComponentActivity)

    val userData = viewModel.userData.collectAsState().value
    val currentUser = RuntimeQueries.currentUser
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

        ProfileHeaderSection(
            isEditButtonVisible = false,
            profilePictureUrl = currentUser?.profilePictureUrl,
            coverPictureUrl = currentUser?.coverPictureUrl,
            userName = currentUser?.userName
            )

        // Second Row - Follower,Following and Posts Count
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

        // TODO: Show User Bio
        DividerWithSpace()

        // Third Row - Edit Profile and Share Profile Buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.weight(1f))

            RowScopedHorizontalButtonWithText(
                icon = Icons.Default.Edit,
                onClick = { onNavigateToEditUserProfileScreen() },
                text = "Edit Profile"
            )

            RowScopedHorizontalButtonWithText(
                icon = Icons.Default.Share,
                onClick = { /* Handle share profile button click */ },
                text = "Share Profile"
            )
            Spacer(modifier = Modifier.weight(1f))

        }
        // Fourth Row - Photos, Videos, and Tagged Posts
        // TODO: on Pressing any of the buttons , User will navigate to those specific posts
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.weight(1f))

            RowScopedHorizontalButtonWithText(
                icon = Icons.Default.Edit,
                onClick = { /* TODO: show Photos : screen */ },
                text = "Photos"
            )
            RowScopedHorizontalButtonWithText(
                icon = Icons.Default.VideoLibrary,
                onClick = { /* TODO: show Reels : screen */ },
                text = "Reels"
            )
            RowScopedHorizontalButtonWithText(
                icon = Icons.Default.Tag,
                onClick = { /* TODO: show Tagged Posts : screen */ },
                text = "Tagged"
            )
            Spacer(modifier = Modifier.weight(1f))

        }



        if (currentUser != null) {
            // TODO: show Gender , relation ship status
            OtherUserDetailsCard(
                currentUser.gender,
                currentUser.dob,
                currentUser.interestedIn,
                currentUser.phone,
                currentUser.relationshipStatus,
                currentUser.hobbies
            )
            // TODO: show address card
            AddressCard(
                currentUser.address,
                currentUser.city,
                currentUser.country

            )
            // TODO: show employment card
            EmploymentCard(
                employmentStatus = currentUser.employmentStatus,
                companyName = currentUser.company,
                duration = ""
            )
        }
    }


    viewModel.showAllPost()
    val posts = viewModel.mUserPosts.collectAsState().value
    Log.d(TAG, "posts fetched from db: ${posts.toString()}")
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


@Preview(showBackground = true)
@Composable
fun EmploymentCard(
    employmentStatus: String? = "",
    companyName: String? = "",
    duration: String? = ""
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Employment Details",
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Employment Status: $employmentStatus",
                style = MaterialTheme.typography.bodyMedium
            )
            if ((employmentStatus == "Full-time employment") || (employmentStatus == "Part-time employment") || (employmentStatus == "Self-employment or entrepreneurship") || (employmentStatus == "Internship")) {
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Company Name: $companyName",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Duration: $duration",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}


@Composable
fun AddressCard(
    address: String? = "",
    city: String? = "",
    country: String? = ""
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Address Details",
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Address: $address",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "City: $city",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "Country: $country",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun OtherUserDetailsCard(
    gender: String? = "Prefer not to say",
    dob: String? = null,
    interestedIn: String? = null,
    phone: String? = null,
    relationshipStatus: String? = null,
    hobbies: List<String>? = null
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Other User Details",
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Gender: $gender",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "Date of Birth: $dob",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "Hobbies: ${hobbies?.joinToString(", ")}",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "Phone: $phone",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "Relationship Status: $relationshipStatus",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "Interested In: $interestedIn",
                style = MaterialTheme.typography.bodyMedium
            )

        }
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

