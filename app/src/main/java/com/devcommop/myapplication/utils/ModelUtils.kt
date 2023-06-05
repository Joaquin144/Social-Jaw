package com.devcommop.myapplication.utils

import com.devcommop.myapplication.data.model.Post
import com.devcommop.myapplication.data.model.User

class ModelUtils {
    companion object {
        /**
         * This function will associate [User] meta-data to the [Post] given
         * @param post on which meta-data needs to be added
         * @param user which is initiating the action
         */
        fun associatePostToUser(post: Post, user: User) {
            post.apply {
                postId = CommonUtils.getAutoId()
                authorId = user.uid
                authorUserName = user.userName
                authorFullName = user.fullName
                authorProfilePictureUrl = user.profilePictureUrl
                timestamp = System.currentTimeMillis().toString()
                likesCount = 0
                dislikesCount = 0
                commentsCount = 0
                textContent = post.textContent
            }
        }
    }
}