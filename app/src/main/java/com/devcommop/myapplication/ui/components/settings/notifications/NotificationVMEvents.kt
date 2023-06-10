package com.devcommop.myapplication.ui.components.settings.notifications

sealed class NotificationVMEvents {
    object FollowNotificationChange: NotificationVMEvents()
    object LikeNotificationChange: NotificationVMEvents()
    object CommentNotificationChange: NotificationVMEvents()
    object SecurityNotificationChange: NotificationVMEvents()
    object PromotionalNotificationChange: NotificationVMEvents()
    object ProductUpdateNotificationChange: NotificationVMEvents()
}