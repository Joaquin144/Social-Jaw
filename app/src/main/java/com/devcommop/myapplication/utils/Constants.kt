package com.devcommop.myapplication.utils

object Constants {
    //  firestore Collection/ Document constants:--
    const val USERS_COLLECTION = "USERS"
    const val POSTS_COLLECTION = "POSTS"//each document will be a post in itself
    const val COMMENTS_COLLECTION = "COMMENTS"
    const val SHORTS_COLLECTION = "SHORTS"

    //other firebase constants
    const val GLOBAL_FOLLOWER = "admin_hoon_mai"


    const val WEB_CLIENT_ID = "110349412397-06umj758q3e4uj8acn5r2hl05omkgrct.apps.googleusercontent.com"


    //FCM constants
    const val BASE_URL = "https://fcm.googleapis.com"
    const val SERVER_KEY = "AAAAGbFWaC0:APA91bHl6lt7OK3C8YZtsG0REJQWhourVxLu6q3q8jVYQcPfCK7ydMa9NM_ePpVIk9BV3OTHLHEzy8FsrfyQJ6YG3eDoMaUMd7me6PtxcseEXUz0NeJx3mGgRHas23cAK16tzHaO_69C"  //todo: keep it secure
    const val CONTENT_TYPE = "application/json" //requests to be made in json
    const val DEFAULT_NOTIFICATION_CHANNEL_ID = "social_jaw_default_001"
    const val DEFAULT_NOTIFICATION_CHANNEL_NAME = "social_jaw_default_channel"
    const val DEFAULT_FCM_TOPIC = "fcm_topic_all"

    //SharedPref
    const val BASIC_SHARED_PREF_NAME = "UserBasics"


    //App Constants
    const val DEFAULT_ERROR_MESSAGE = "Some unknown error occurred. Please try again."
}