package com.devcommop.myapplication.utils

import com.devcommop.myapplication.data.model.Comment
import com.devcommop.myapplication.data.model.Post
import com.devcommop.myapplication.data.model.User
import com.devcommop.myapplication.ui.components.authscreen.UserData
import java.util.Calendar

class ModelUtils {
    companion object {
        /**
         * This function will associate [User] meta-data to the [Post] given
         * @param post on which meta-data needs to be added
         * @param user which is initiating the action
         */
        fun associatePostToUser(post: Post, user: User) {
            if (post.postId == "")
                post.postId = CommonUtils.getAutoId()
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
            }
        }

//        fun assosiateShortItemToUser(short: ShortItem, user: User) {
//            if(short.id == ""){
//                short.id = CommonUtils.getAutoId()
//            }
//            short.apply {
////                id = CommonUtils.getAutoId()
//                createdBy = user.uid
////                timestamp = System.currentTimeMillis().toString()
//             }
//        }

        fun associateUserDataToUser(userData: UserData, user: User) {
            //todo: replace non-null assertions by throwing custom exceptions
            val calendar = Calendar.getInstance()
            user.apply {
                uid = userData.userId!!
                userName = userData.username?:CommonUtils.getAutoId().substring(10)//todo: give appropriate userName rather than wierd substrings
                profilePictureUrl = userData.profilePictureUrl
                createdTimestamp = System.currentTimeMillis().toString()
                dateCreated = calendar.get(Calendar.DAY_OF_MONTH).toLong()
                dayOfWeekCreated = calendar.get(Calendar.DAY_OF_WEEK).toLong()
                monthCreated = calendar.get(Calendar.MONTH).toLong()
                yearCreated = calendar.get(Calendar.YEAR).toLong()
                isDeactivated = false
                followersCount = 0
                followingCount = 0
            }
        }

        fun associateUserAndPostToComment(comment: Comment, user: User, post: Post) {
            comment.apply {
                commentId = CommonUtils.getAutoId()
                authorUserName = user.userName
                authorFullName = user.fullName?:""
                authorProfilePictureUrl = user.profilePictureUrl?:""//todo: provide default user pic
                timestamp = CommonUtils.getTimestamp()
                postId = post.postId
                authorId  = user.uid
            }
        }
    }
}