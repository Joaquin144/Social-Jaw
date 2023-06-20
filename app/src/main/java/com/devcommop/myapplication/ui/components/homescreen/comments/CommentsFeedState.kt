package com.devcommop.myapplication.ui.components.homescreen.comments

import com.devcommop.myapplication.data.model.Comment
import com.devcommop.myapplication.data.model.Post

data class CommentsFeedState(
    val commentsList: List<Comment> = emptyList()
)
