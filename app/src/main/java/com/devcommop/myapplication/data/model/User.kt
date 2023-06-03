package com.devcommop.myapplication.data.model

data class User(
    var uid: String = "",
    var userName: String = "",//todo: make sure that userName is also unique to all users like uid
    var fullName: String? = null,
    var bio: String? = null,
    var profilePictureUrl: String? = null,
    var followers: Array<String>? = arrayOf("admin_user_120"),
    var following: Array<String>? = arrayOf("admin_user_120"),
    var posts: Array<String>? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (uid != other.uid) return false
        if (userName != other.userName) return false
        if (fullName != other.fullName) return false
        if (bio != other.bio) return false
        if (profilePictureUrl != other.profilePictureUrl) return false
        if (followers != null) {
            if (other.followers == null) return false
            if (!followers.contentEquals(other.followers)) return false
        } else if (other.followers != null) return false
        if (following != null) {
            if (other.following == null) return false
            if (!following.contentEquals(other.following)) return false
        } else if (other.following != null) return false
        if (posts != null) {
            if (other.posts == null) return false
            if (!posts.contentEquals(other.posts)) return false
        } else if (other.posts != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = uid.hashCode()
        result = 31 * result + userName.hashCode()
        result = 31 * result + (fullName?.hashCode() ?: 0)
        result = 31 * result + (bio?.hashCode() ?: 0)
        result = 31 * result + (profilePictureUrl?.hashCode() ?: 0)
        result = 31 * result + (followers?.contentHashCode() ?: 0)
        result = 31 * result + (following?.contentHashCode() ?: 0)
        result = 31 * result + (posts?.contentHashCode() ?: 0)
        return result
    }

}