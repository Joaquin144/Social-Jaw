package com.devcommop.myapplication.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devcommop.myapplication.R

// ui ref -> https://www.google.com/url?sa=i&url=https%3A%2F%2Fdribbble.com%2Fshots%2F9353362-Facebook-post-UI&psig=AOvVaw0q5MZB1dbJvr5OlDSXIN7z&ust=1685876559529000&source=images&cd=vfe&ved=0CBEQjRxqFwoTCOCKxd35pv8CFQAAAAAdAAAAABAE

@Composable
fun PostItem() {
    Card(
        modifier = Modifier
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)

        ) {
            PostHeader()
            PostBody()
            PostBottom()

        }
    }
}

@Composable
fun PostHeader() {
    Row(
        verticalAlignment = Alignment.CenterVertically,

        ) {
        Image(
            modifier = Modifier.padding(end = 8.dp),
            painter = painterResource(id = R.drawable.baseline_person_24),
            contentDescription = "person image"
        )
        Text(
            text = "John Doe",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = { /*
        TODO -> options related to a post like like, share, save, etc. */
        }) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_more_vert_24),
                contentDescription = "more options"
            )
        }

    }
}

@Composable
fun PostBody() {
    Column(
        modifier = Modifier
    ) {
        Text(
            text = "This is the description of a social media post",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(2.dp))
        Image(
            painter = painterResource(id = R.drawable.yellow_bg),
            contentDescription = "post_image"
        )
    }
}

@Composable
fun PostBottom() {
    Row(
        modifier = Modifier.padding(start = 4.dp, end = 4.dp , top = 2.dp ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // this button is used to like a post
        // in future clicking on text can show the list who have liked

        Icon(
            painter = painterResource(id = R.drawable.baseline_thumb_up_24),
            contentDescription = "like",
            modifier = Modifier.padding(end = 8.dp)
        )
        Text(
            text = "45k",
            style = MaterialTheme.typography.titleSmall
            )
        Spacer(modifier = Modifier.weight(1f))

        Icon(
            painter = painterResource(id = R.drawable.baseline_comment_24),
            contentDescription = "like", modifier = Modifier.padding(end = 8.dp)

        )
        Text(
            text = "254",
            style = MaterialTheme.typography.titleSmall
        )
        Spacer(modifier = Modifier.weight(1f))


        Icon(
            painter = painterResource(id = R.drawable.baseline_share_24),
            contentDescription = "like", modifier = Modifier.padding(end = 8.dp)

        )
        Text(
            text = "4k",
            style = MaterialTheme.typography.titleSmall
        )

    }
}

@Preview
@Composable
fun PostItemPreview() {
    PostItem()
}