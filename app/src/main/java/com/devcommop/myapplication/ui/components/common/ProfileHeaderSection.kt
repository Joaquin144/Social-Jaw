package com.devcommop.myapplication.ui.components.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.devcommop.myapplication.data.local.RuntimeQueries


@Composable
fun ProfileHeaderSection(isEditButtonVisible: Boolean = false) {
    // TODO: when user change cover, show dialog to post it as well
    val currentUser = RuntimeQueries.currentUser
    Box(modifier = Modifier) {
        Box(
            contentAlignment = Alignment.BottomEnd,
        ) {
            Image(
                painter = rememberAsyncImagePainter(currentUser?.coverPictureUrl),
                contentDescription = "cover picture",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
                    .padding(horizontal = 2.dp)
                    .size(160.dp)
                    .safeContentPadding()
                    .clip(shape = RectangleShape),
                contentScale = ContentScale.Crop
            )
            if (isEditButtonVisible) {
                IconButton(onClick = { /*TODO: Handle profile picture edit */ }) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "Edit Profile Picture",
                        modifier = Modifier.size(28.dp)
                    )
                }
            }

        }


        Row(
            modifier = Modifier.padding(top = 81.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Box(
                contentAlignment = Alignment.BottomEnd,
                modifier = Modifier.padding(0.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(currentUser?.profilePictureUrl),
                    contentDescription = "profile_picture",
                    modifier = Modifier
                        .padding(horizontal = 2.dp)
                        .size(180.dp)
                        .safeContentPadding()
                        .clip(shape = CircleShape),
                    contentScale = ContentScale.Crop
                )
                if (isEditButtonVisible) {
                    IconButton(onClick = { /*TODO: Handle profile picture edit */ }) {
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = "Edit Profile Picture",
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
//
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
    if (!isEditButtonVisible) {
        Text(
            text = "${currentUser?.userName}", // TODO: Replace with userData.username
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(horizontal = 2.dp, vertical = 4.dp)
        )
    }

}