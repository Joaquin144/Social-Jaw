package com.devcommop.myapplication.ui.components.homescreen

import com.devcommop.myapplication.data.model.Post

sealed class HomeScreenEvents {
    object Refresh: HomeScreenEvents()//refresh the posts list
    data class LikePost(val post: Post): HomeScreenEvents()
    data class DislikePost(val post: Post): HomeScreenEvents()
    data class CommentPost(val post: Post): HomeScreenEvents()
    data class SharePost(val post: Post): HomeScreenEvents()
    data class ReportPost(val post: Post): HomeScreenEvents()
    data class DeletePost(val post: Post): HomeScreenEvents()
}