package com.devcommop.myapplication.ui.components.homescreen.comments

import com.devcommop.myapplication.data.model.Comment

sealed class CommentsEvent {
    object AddComment: CommentsEvent()
    object Reload: CommentsEvent()
//    sealed class DeleteComment(val comment: Comment): CommentsEvent()
//    sealed class ReportComment(val comment: Comment): CommentsEvent()
//    sealed class EditComment(val comment: Comment): CommentsEvent()
//    sealed class LikeComment(val comment: Comment): CommentsEvent()
//    sealed class DislikeComment(val comment: Comment): CommentsEvent()
//    sealed class SortComment(val commentOrder: CommentOrder): CommentsEvent()
}