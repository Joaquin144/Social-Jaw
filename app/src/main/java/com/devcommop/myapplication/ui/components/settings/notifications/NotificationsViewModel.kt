package com.devcommop.myapplication.ui.components.settings.notifications

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.devcommop.myapplication.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    private val _notificationState = mutableStateOf(NotificationState())
    val notificationState: State<NotificationState> = _notificationState

    fun onEvent(event: NotificationVMEvents){
        when(event){
            NotificationVMEvents.CommentNotificationChange -> {
                _notificationState.value = notificationState.value.copy(receiveCommentNotifications)
            }
            NotificationVMEvents.FollowNotificationChange -> {

            }
            NotificationVMEvents.LikeNotificationChange -> {

            }
            NotificationVMEvents.ProductUpdateNotificationChange -> {

            }
            NotificationVMEvents.PromotionalNotificationChange -> {

            }
            NotificationVMEvents.SecurityNotificationChange -> {

            }
        }
    }
}