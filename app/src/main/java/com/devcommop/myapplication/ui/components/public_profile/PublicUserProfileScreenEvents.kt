package com.devcommop.myapplication.ui.components.public_profile

import com.devcommop.myapplication.data.model.Post

//todo: follow DRY principle -> PublicUserProfileScreenEvents and HomeScreenEvents are both same. Make them common
sealed class PublicUserProfileScreenEvents {
    object Refresh: PublicUserProfileScreenEvents()//refresh the posts list
    data class LikePost(val post: Post): PublicUserProfileScreenEvents()
    data class DislikePost(val post: Post): PublicUserProfileScreenEvents()
    data class CommentPost(val post: Post): PublicUserProfileScreenEvents()
    data class SharePost(val post: Post): PublicUserProfileScreenEvents()
    data class ReportPost(val post: Post): PublicUserProfileScreenEvents()
    data class DeletePost(val post: Post): PublicUserProfileScreenEvents()
}