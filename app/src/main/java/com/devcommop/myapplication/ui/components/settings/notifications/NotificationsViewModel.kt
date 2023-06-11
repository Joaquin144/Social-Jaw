package com.devcommop.myapplication.ui.components.settings.notifications

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devcommop.myapplication.SMApplication
import com.devcommop.myapplication.data.local.RuntimeQueries
import com.devcommop.myapplication.data.repository.Repository
import com.devcommop.myapplication.services.MyFirebaseMessagingService
import com.devcommop.myapplication.utils.NotificationUtils
import com.devcommop.myapplication.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "##@@NotifsVM"

@HiltViewModel
class NotificationsViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _notificationState = mutableStateOf(NotificationState())
    val notificationState: State<NotificationState> = _notificationState

    private var user = RuntimeQueries.currentUser

    init {
        user = RuntimeQueries.currentUser
        inflateNotificationPreferencesData()
    }

    fun onEvent(event: NotificationVMEvents) {
        when (event) {
            is NotificationVMEvents.CommentNotificationChange -> {
                _notificationState.value =
                    notificationState.value.copy(receiveCommentNotifications = !event.oldValue)
            }

            is NotificationVMEvents.FollowNotificationChange -> {
                _notificationState.value =
                    notificationState.value.copy(receiveFollowNotifications = !event.oldValue)
            }

            is NotificationVMEvents.LikeNotificationChange -> {
                _notificationState.value =
                    notificationState.value.copy(receiveLikeNotifications = !event.oldValue)
            }

            is NotificationVMEvents.ProductUpdateNotificationChange -> {
                _notificationState.value =
                    notificationState.value.copy(receiveProductUpdatesNotifications = !event.oldValue)
            }

            is NotificationVMEvents.PromotionalNotificationChange -> {
                _notificationState.value =
                    notificationState.value.copy(receivePromotionalNotifications = !event.oldValue)
            }

            is NotificationVMEvents.SecurityNotificationChange -> {
                _notificationState.value =
                    notificationState.value.copy(receiveSecurityNotifications = !event.oldValue)
            }

            is NotificationVMEvents.SavePreferences -> {
                //todo: update user notification settings in db as well as locally on device when it starts
                user?.let { user ->
                    viewModelScope.launch {
                        when (val updateStatus = repository.updateUserNotificationsSettings(_notificationState.value, user)) {
                            is Resource.Error -> {
                                //todo: show error
                                Log.d(
                                    TAG,
                                    "User notification failed to update: ${updateStatus.message}"
                                )
                            }

                            is Resource.Loading -> {}
                            is Resource.Success -> {
                                Log.d(
                                    TAG,
                                    "User notification preferences are successfully updated in database"
                                )
                            }
                        }

                    }
                }

            }
        }
    }

    private fun inflateNotificationPreferencesData() {
        viewModelScope.launch {
            //delay(2000L)
            user?.let { user ->
                val topics = NotificationUtils.getAllTopicNames(user.uid)//list of size 7
                val options = MyFirebaseMessagingService.getTopicSubscriptions(user.uid)
                _notificationState.value = notificationState.value.copy(receiveFollowNotifications = options[topics[0]]?:true)
                _notificationState.value = notificationState.value.copy(receiveLikeNotifications = options[topics[1]]?:true)
                _notificationState.value = notificationState.value.copy(receiveCommentNotifications = options[topics[2]]?:true)
                _notificationState.value = notificationState.value.copy(receivePromotionalNotifications = options[topics[3]]?:true)
                _notificationState.value = notificationState.value.copy(receiveSecurityNotifications = options[topics[4]]?:true)
                _notificationState.value = notificationState.value.copy(receiveProductUpdatesNotifications = options[topics[5]]?:true)
            }
            Log.d(TAG, "${_notificationState.value}")
        }
//        user?.let { user ->
//            val topics = NotificationUtils.getAllTopicNames(user.uid)//list of size 7
//            var idx = 0;
//            val options = MyFirebaseMessagingService.getTopicSubscriptions(user.uid)
//            _notificationState.value.apply {
//                receiveFollowNotifications = options[topics[0]]?:true
//                receiveLikeNotifications = options[topics[1]]?:true
//                receiveCommentNotifications = options[topics[2]]?:true
//                receivePromotionalNotifications = options[topics[3]]?:true
//                receiveSecurityNotifications = options[topics[4]]?:true
//                receiveProductUpdatesNotifications = options[topics[5]]?:true
//            }
//        }
    }
}