package com.devcommop.myapplication.ui.components.mainscreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devcommop.myapplication.R


@Composable
fun PostItem(
//    username: String,
//    userProfileIcon: Painter = painterResource(R.drawable.dummy_profile_picture),
//    timePosted: String,
//    contentDescription: String,
//    postImage: Painter,
    username: String = "John Doe",
    userProfileIcon: Painter = painterResource(R.drawable.dummy_profile_picture),
    timePosted: String = "2 hours ago",
    contentDescription: String = "Check out this amazing view!",
    postImage: Painter = painterResource(id = R.drawable.img)
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(.9f)),
        border = BorderStroke(1.dp, Color.Black)
    ) {
        Column(
            modifier = Modifier.padding(vertical = 2.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                Image(
                    painter = userProfileIcon,
                    contentDescription = null,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = username,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = timePosted,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.DarkGray
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                IconButton(
                    onClick = { /* Handle option button click */ },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "Options",
                        tint = Color.Black
                    )
                }
            }
            Divider(modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(4.dp))
            Image(
                painter = postImage,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(4.dp))
                    .clickable(onClick = { /* Handle image click */ })
            )

            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = contentDescription,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Divider(modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ActionButton(
                    icon = Icons.Default.FavoriteBorder,
                    onClick = { /* Handle like button click */ }
                )
                ActionButton(
                    icon = Icons.Default.Comment,
                    onClick = { /* Handle comment button click */ }
                )
                ActionButton(
                    icon = Icons.Default.Share,
                    onClick = { /* Handle share button click */ }
                )
            }
        }
    }
}

@Composable
fun ActionButton(
    icon: ImageVector,
    onClick: () -> Unit,
    count: Int = 420
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.height(36.dp)

    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.Black
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = count.toString(),
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black
        )

    }
}

@Preview(showBackground = true)
@Composable
fun PostItemPreview() {
    PostItem(
        username = "Anime Girl",
        userProfileIcon = painterResource(R.drawable.dummy_profile_picture),
        timePosted = "just now",
        contentDescription = "Check out this amazing view!",
        postImage = painterResource(id = R.drawable.img)
    )
}
