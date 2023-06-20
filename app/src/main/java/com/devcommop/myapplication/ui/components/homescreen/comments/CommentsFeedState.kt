package com.devcommop.myapplication.ui.components.homescreen.comments

import com.devcommop.myapplication.data.model.Comment

data class CommentsFeedState(
    val commentsList: List<Comment> = emptyList()
)
