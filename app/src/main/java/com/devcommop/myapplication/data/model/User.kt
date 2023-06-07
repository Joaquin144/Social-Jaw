package com.devcommop.myapplication.data.model

import com.devcommop.myapplication.utils.Constants

data class User(
    var uid: String = "",
    var userName: String = "",//todo: Ensure that userName is also unique to all users like uid
    var fullName: String? = null,//full name of the user may be null
    var gender: String? = "Prefer not to say",//gender of this user//todo: Limit size of Strings
    var relationshipStatus: String? = null,//   married/ single/ divorced/ neutral/ dating/ engaged/ widow/ null
    var address: String? = null,
    var city: String? = null,
    var country: String? = null,//todo: Ensure that country is valid
    var interestedIn: String? = null,//  men/ women/ both/ neutral/ null
    var dob: String? = null,//todo: divide it into month, day and year for better queries and engagement
    var phone: String? = null,//  +[CC] [PhNo]
    var bio: String? = null,//About this user
    var profilePictureUrl: String? = null,//profile picture of this user
    var coverPictureUrl: String? = null,//cover photo for this user
    var company: String? = null,//where this user works
    var employmentStatus: String? = null,// Self-Employed / Job/ Business/ Unemployed / Intern / UnderAge
    var createdTimestamp: String? = null,// when was this user created
    var dateCreated: Long? = null,
    var dayOfWeekCreated: Long? = null,
    var monthCreated: Long? = null,
    var yearCreated: Long? = null,
    var isDeactivated: Boolean? = false, //null means false
    var followers: Array<String>? = arrayOf(Constants.GLOBAL_FOLLOWER),//ids of all users who are following this user
    var following: Array<String>? = arrayOf(Constants.GLOBAL_FOLLOWER),//ids of all users which this user follows
    var followersCount: Long? = 0,
    var followingCount: Long? = 0,
    var posts: Array<String>? = null,//ids of all posts created by this user
    var likedPosts: Array<String>? = null,//ids of all posts this user has liked
    var dislikedPosts: Array<String>? = null,//ids of all posts this user has disliked
    var comments: Array<String>? = null,//ids of all comments made by this user
    var likedComments: Array<String>? = null,//ids of all comments which were liked by this user
    var dislikedComments: Array<String>? = null//ids of all comments which were disliked by this user
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (uid != other.uid) return false
        if (userName != other.userName) return false
        if (fullName != other.fullName) return false
        if (gender != other.gender) return false
        if (relationshipStatus != other.relationshipStatus) return false
        if (address != other.address) return false
        if (city != other.city) return false
        if (country != other.country) return false
        if (interestedIn != other.interestedIn) return false
        if (dob != other.dob) return false
        if (phone != other.phone) return false
        if (bio != other.bio) return false
        if (profilePictureUrl != other.profilePictureUrl) return false
        if (coverPictureUrl != other.coverPictureUrl) return false
        if (company != other.company) return false
        if (employmentStatus != other.employmentStatus) return false
        if (createdTimestamp != other.createdTimestamp) return false
        if (dateCreated != other.dateCreated) return false
        if (yearCreated != other.yearCreated) return false
        if (isDeactivated != other.isDeactivated) return false
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
        if (likedPosts != null) {
            if (other.likedPosts == null) return false
            if (!likedPosts.contentEquals(other.likedPosts)) return false
        } else if (other.likedPosts != null) return false
        if (dislikedPosts != null) {
            if (other.dislikedPosts == null) return false
            if (!dislikedPosts.contentEquals(other.dislikedPosts)) return false
        } else if (other.dislikedPosts != null) return false
        if (comments != null) {
            if (other.comments == null) return false
            if (!comments.contentEquals(other.comments)) return false
        } else if (other.comments != null) return false
        if (likedComments != null) {
            if (other.likedComments == null) return false
            if (!likedComments.contentEquals(other.likedComments)) return false
        } else if (other.likedComments != null) return false
        if (dislikedComments != null) {
            if (other.dislikedComments == null) return false
            if (!dislikedComments.contentEquals(other.dislikedComments)) return false
        } else if (other.dislikedComments != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = uid.hashCode()
        result = 31 * result + userName.hashCode()
        result = 31 * result + (fullName?.hashCode() ?: 0)
        result = 31 * result + (gender?.hashCode() ?: 0)
        result = 31 * result + (relationshipStatus?.hashCode() ?: 0)
        result = 31 * result + (address?.hashCode() ?: 0)
        result = 31 * result + (city?.hashCode() ?: 0)
        result = 31 * result + (country?.hashCode() ?: 0)
        result = 31 * result + (interestedIn?.hashCode() ?: 0)
        result = 31 * result + (dob?.hashCode() ?: 0)
        result = 31 * result + (phone?.hashCode() ?: 0)
        result = 31 * result + (bio?.hashCode() ?: 0)
        result = 31 * result + (profilePictureUrl?.hashCode() ?: 0)
        result = 31 * result + (coverPictureUrl?.hashCode() ?: 0)
        result = 31 * result + (company?.hashCode() ?: 0)
        result = 31 * result + (employmentStatus?.hashCode() ?: 0)
        result = 31 * result + (createdTimestamp?.hashCode() ?: 0)
        result = 31 * result + (dateCreated?.hashCode() ?: 0)
        result = 31 * result + (yearCreated?.hashCode() ?: 0)
        result = 31 * result + (isDeactivated?.hashCode() ?: 0)
        result = 31 * result + (followers?.contentHashCode() ?: 0)
        result = 31 * result + (following?.contentHashCode() ?: 0)
        result = 31 * result + (posts?.contentHashCode() ?: 0)
        result = 31 * result + (likedPosts?.contentHashCode() ?: 0)
        result = 31 * result + (dislikedPosts?.contentHashCode() ?: 0)
        result = 31 * result + (comments?.contentHashCode() ?: 0)
        result = 31 * result + (likedComments?.contentHashCode() ?: 0)
        result = 31 * result + (dislikedComments?.contentHashCode() ?: 0)
        return result
    }

}