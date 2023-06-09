package com.devcommop.myapplication.data.model

import com.google.errorprone.annotations.Immutable

@Immutable
data class ShortItem(
    var id: String = "",
    var createdBy: String? = null,
    var timestamp: String? = null ,
    var mediaUrl: String? = null ,
    var thumbnail: String? = null,
    var lastPlayedPosition: Long = 0,
    var title: String? = null,
    var description: String? = null,
    var likes: Int = 0,
    var comments : Int = 0 ,
    var views: Int = 0,
    var likesList: List<String>? = null, // this will store list of user id's who like the short
    var commentsList : List<String>? = null,// this will store list of comment ids
    var tags: List<String>? = null,
    var duration: Long = 0
)