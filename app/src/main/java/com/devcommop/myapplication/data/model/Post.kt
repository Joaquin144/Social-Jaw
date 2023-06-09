package com.devcommop.myapplication.data.model

import com.devcommop.myapplication.utils.CommonUtils

data class Post(
    var postId: String = "",
    var authorId: String = "",
    var authorUserName: String = "",
    var authorFullName: String? = "",
    var authorProfilePictureUrl: String? = null,
    var timestamp: String? = null,//todo: Make it of long type to fetch kLatestPosts
    var likesCount: Long? = 0,//todo: Ensure for every post created this field is not null otherwise problem in fetching topKPosts
    var dislikesCount: Long? = 0,
    var commentsCount: Long? = 0,
    var sharesCount: Long? = 0,
    var location: String? = null,
    var deviceId: String? = null,
    var textContent: String? = null,
    var videoUrl: String? = null,//only single video allowed at a time
    var imagesUrl: List<String>? = null,
    var likedByUsers: List<String>? = null,
    var dislikedByUsers: List<String>? = null,
    var comments: List<String>? = null
)
