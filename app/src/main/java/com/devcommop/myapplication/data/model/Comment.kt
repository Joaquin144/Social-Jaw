package com.devcommop.myapplication.data.model

data class Comment(
    var commentId: String = "",
    var authorUserName: String = "",
    var text: String = "",
    var timestamp: String = "",
    var postId: String = "",
    var authorId: String = "",
)
