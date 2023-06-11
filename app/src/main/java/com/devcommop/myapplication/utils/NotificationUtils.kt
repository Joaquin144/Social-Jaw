package com.devcommop.myapplication.utils

import com.devcommop.myapplication.ui.components.settings.notifications.NotificationState

class NotificationUtils {
    companion object {
        fun buildOptions(
            notificationState: NotificationState,
            uid: String
        ): HashMap<String, Boolean> {
            val options: HashMap<String, Boolean> = hashMapOf()
            options[Constants.URGENT_FCM_TOPIC] = true//urgent notifications shouldn't be turned off
            options[Constants.PROMOTIONAL_FCM_TOPIC] =
                notificationState.receivePromotionalNotifications
            options[Constants.APP_UPDATE_FCM_TOPIC] =
                notificationState.receiveProductUpdatesNotifications
            options[Constants.SECURITY_FCM_TOPIC] = notificationState.receiveSecurityNotifications
            val followFcmTopic = uid + "_follow"
            val likeFcmTopic = uid + "_like"
            val commentFcmTopic = uid + "_comment"
            options[followFcmTopic] = notificationState.receiveFollowNotifications
            options[likeFcmTopic] = notificationState.receiveLikeNotifications
            options[commentFcmTopic] = notificationState.receiveCommentNotifications

            return options
        }

        /**
         * Guarantees a list of fixed size = 7
         */
        fun getAllTopicNames(uid: String): List<String> {
            val topicList = mutableListOf<String>()
            topicList.add(uid + "_follow")
            topicList.add(uid + "_like")
            topicList.add(uid + "_comment")
            topicList.add(Constants.PROMOTIONAL_FCM_TOPIC)
            topicList.add(Constants.SECURITY_FCM_TOPIC)
            topicList.add(Constants.APP_UPDATE_FCM_TOPIC)
            topicList.add(Constants.URGENT_FCM_TOPIC)

            return topicList
        }
    }
}