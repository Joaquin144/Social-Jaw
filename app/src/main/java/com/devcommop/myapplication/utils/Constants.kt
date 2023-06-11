package com.devcommop.myapplication.utils

object Constants {
    //  firestore Collection/ Document constants:--
    const val USERS_COLLECTION = "USERS"
    const val POSTS_COLLECTION = "POSTS"//each document will be a post in itself
    const val COMMENTS_COLLECTION = "COMMENTS"
    const val SHORTS_COLLECTION = "SHORTS"
    const val USERS_NOTIFICATION_SUB_COLLECTION = "MY_NOTIFICATIONS"
    const val USERS_NOTIFICATION_SETTINGS_DOC = "notification_settings"

    //Google one tap sign in
    const val WEB_CLIENT_ID = "110349412397-06umj758q3e4uj8acn5r2hl05omkgrct.apps.googleusercontent.com"


    //FCM constants
    const val FCM_BASE_URL = "https://fcm.googleapis.com"
    const val FCM_SERVER_KEY = "AAAAGbFWaC0:APA91bHl6lt7OK3C8YZtsG0REJQWhourVxLu6q3q8jVYQcPfCK7ydMa9NM_ePpVIk9BV3OTHLHEzy8FsrfyQJ6YG3eDoMaUMd7me6PtxcseEXUz0NeJx3mGgRHas23cAK16tzHaO_69C"  //todo: keep it secure
    const val FCM_SENDER_ID = 110349412397
    const val FCM_CONTENT_TYPE = "application/json" //requests to be made in json
    const val DEFAULT_NOTIFICATION_CHANNEL_ID = "social_jaw_default_001"
    const val DEFAULT_NOTIFICATION_CHANNEL_NAME = "social_jaw_default_channel"
    const val DEFAULT_FCM_TOPIC = "fcm_topic_all"
    const val URGENT_FCM_TOPIC = "fcm_topic_urgent"
    const val PROMOTIONAL_FCM_TOPIC = "fcm_topic_promo"
    const val APP_UPDATE_FCM_TOPIC = "fcm_topic_app_update"
    const val SECURITY_FCM_TOPIC = "fcm_topic_security"

    //SharedPref
    const val BASIC_SHARED_PREF_NAME = "UserBasics"     //will contain basic info about the user


    //App Constants
    const val DEFAULT_ERROR_MESSAGE = "Some unknown error occurred. Please try again."
}