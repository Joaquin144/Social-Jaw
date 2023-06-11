package com.devcommop.myapplication.ui.components.settings.notifications

sealed class NotificationVMEvents {
    data class FollowNotificationChange(val oldValue: Boolean): NotificationVMEvents()
    data class LikeNotificationChange(val oldValue: Boolean): NotificationVMEvents()
    data class CommentNotificationChange(val oldValue: Boolean): NotificationVMEvents()
    data class SecurityNotificationChange(val oldValue: Boolean): NotificationVMEvents()
    data class PromotionalNotificationChange(val oldValue: Boolean): NotificationVMEvents()
    data class ProductUpdateNotificationChange(val oldValue: Boolean): NotificationVMEvents()
    object SavePreferences: NotificationVMEvents()
}