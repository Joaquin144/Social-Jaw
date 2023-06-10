package com.devcommop.myapplication.ui.components.settings.notifications

data class NotificationState(
    val receiveFollowNotifications: Boolean = true,
    val receiveLikeNotifications: Boolean = true,
    val receiveCommentNotifications: Boolean = true,
    val receiveSecurityNotifications: Boolean = true,
    val receivePromotionalNotifications: Boolean = true,
    val receiveProductUpdatesNotifications: Boolean = true
)