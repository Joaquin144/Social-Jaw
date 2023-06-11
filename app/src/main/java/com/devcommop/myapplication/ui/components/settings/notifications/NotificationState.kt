package com.devcommop.myapplication.ui.components.settings.notifications

data class NotificationState(
    var receiveFollowNotifications: Boolean = true,
    var receiveLikeNotifications: Boolean = true,
    var receiveCommentNotifications: Boolean = true,
    var receiveSecurityNotifications: Boolean = true,
    var receivePromotionalNotifications: Boolean = true,
    var receiveProductUpdatesNotifications: Boolean = true
)