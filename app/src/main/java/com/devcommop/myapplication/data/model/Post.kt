package com.devcommop.myapplication.data.model

//Each Post will also have sub-collection POST_LIKES and POST_COMMENTS
data class Post(
    var postId: String = "",
    var authorId: String = "",
    var authorUserName: String = "",
    var authorFullName: String? = "",
    var authorProfilePictureUrl: String? = null,
    var timestamp: String? = null,//todo: Make it of long type to fetch kLatestPosts
    var likesCount: Long? = 0,//todo: Ensure for every post created this field is not null otherwise problem in fetching topKPosts
    var dislikesCount: Long? = 0,
    var commentsCount: Long? = 0,
    var location: String? = null,
    var deviceId: String? = null,
    var textContent: String? = null,
    var videoUrl: String? = null,//only single video allowed at a time
    var imagesUrl: Array<String>? = null,
    var likedByUsers: Array<String>? = null,
    var dislikedByUsers: Array<String>? = null,
    var comments: Array<String>? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Post

        if (postId != other.postId) return false
        if (authorId != other.authorId) return false
        if (authorUserName != other.authorUserName) return false
        if (authorFullName != other.authorFullName) return false
        if (authorProfilePictureUrl != other.authorProfilePictureUrl) return false
        if (timestamp != other.timestamp) return false
        if (likesCount != other.likesCount) return false
        if (dislikesCount != other.dislikesCount) return false
        if (commentsCount != other.commentsCount) return false
        if (location != other.location) return false
        if (deviceId != other.deviceId) return false
        if (textContent != other.textContent) return false
        if (videoUrl != other.videoUrl) return false
        if (imagesUrl != null) {
            if (other.imagesUrl == null) return false
            if (!imagesUrl.contentEquals(other.imagesUrl)) return false
        } else if (other.imagesUrl != null) return false
        if (likedByUsers != null) {
            if (other.likedByUsers == null) return false
            if (!likedByUsers.contentEquals(other.likedByUsers)) return false
        } else if (other.likedByUsers != null) return false
        if (dislikedByUsers != null) {
            if (other.dislikedByUsers == null) return false
            if (!dislikedByUsers.contentEquals(other.dislikedByUsers)) return false
        } else if (other.dislikedByUsers != null) return false
        if (comments != null) {
            if (other.comments == null) return false
            if (!comments.contentEquals(other.comments)) return false
        } else if (other.comments != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = postId.hashCode()
        result = 31 * result + authorId.hashCode()
        result = 31 * result + authorUserName.hashCode()
        result = 31 * result + (authorFullName?.hashCode() ?: 0)
        result = 31 * result + (authorProfilePictureUrl?.hashCode() ?: 0)
        result = 31 * result + (timestamp?.hashCode() ?: 0)
        result = 31 * result + (likesCount?.hashCode() ?: 0)
        result = 31 * result + (dislikesCount?.hashCode() ?: 0)
        result = 31 * result + (commentsCount?.hashCode() ?: 0)
        result = 31 * result + (location?.hashCode() ?: 0)
        result = 31 * result + (deviceId?.hashCode() ?: 0)
        result = 31 * result + (textContent?.hashCode() ?: 0)
        result = 31 * result + (videoUrl?.hashCode() ?: 0)
        result = 31 * result + (imagesUrl?.contentHashCode() ?: 0)
        result = 31 * result + (likedByUsers?.contentHashCode() ?: 0)
        result = 31 * result + (dislikedByUsers?.contentHashCode() ?: 0)
        result = 31 * result + (comments?.contentHashCode() ?: 0)
        return result
    }
}
