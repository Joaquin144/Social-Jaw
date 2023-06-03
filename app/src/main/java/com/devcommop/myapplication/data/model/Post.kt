package com.devcommop.myapplication.data.model

//Each Post will also have sub-collection POST_LIKES and POST_COMMENTS
data class Post(
    var authorId: String = "",
    var authorUserName: String = "",
    var authorFullName: String? = "",
    var authorProfilePictureUrl: String? = null,
    var timestamp: String? = null,
    var likesCount: Long = 0,
    var textContent: String? = null,
    var imagesUrl: String? = null,
    var videourl: String? = null
)
