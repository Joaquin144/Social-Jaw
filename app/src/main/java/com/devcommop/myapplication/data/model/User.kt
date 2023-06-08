package com.devcommop.myapplication.data.model

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
    var followers: List<String>? = null,//ids of all users who are following this user
    var following: List<String>? = null,//ids of all users which this user follows
    var followersCount: Long? = 0,
    var followingCount: Long? = 0,
    var posts: List<String>? = null,//ids of all posts created by this user
    var likedPosts: List<String>? = null,//ids of all posts this user has liked
    var dislikedPosts: List<String>? = null,//ids of all posts this user has disliked
    var comments: List<String>? = null,//ids of all comments made by this user
    var likedComments: List<String>? = null,//ids of all comments which were liked by this user
    var dislikedComments: List<String>? = null//ids of all comments which were disliked by this user
)