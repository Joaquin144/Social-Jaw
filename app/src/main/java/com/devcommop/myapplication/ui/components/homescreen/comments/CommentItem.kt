package com.devcommop.myapplication.ui.components.homescreen.comments

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.devcommop.myapplication.data.model.Comment

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun CommentItem(
    comment: Comment = Comment(authorUserName = "undefined", text = "this is a sample comment")
) {
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Image: Author profile picture
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = comment.authorProfilePictureUrl),
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = comment.authorUserName,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = comment.timestamp.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.DarkGray
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = { /* Handle option button click */ }, modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Options",
                    tint = Color.Black
                )
            }
        }
        Spacer(modifier = Modifier.height(2.dp))
        Divider(modifier = Modifier.fillMaxWidth())

        // Text: Author username
        Text(
            text = "@{$comment.authorUserName}",
            style = TextStyle(fontWeight = FontWeight.Bold),
            modifier = Modifier
                .padding(start = 8.dp)
                .weight(1f)
        )

        // Text: Comment text
        Text(
            text = comment.text,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}