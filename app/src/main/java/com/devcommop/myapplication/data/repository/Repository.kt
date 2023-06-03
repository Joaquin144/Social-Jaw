package com.devcommop.myapplication.data.repository

import com.devcommop.myapplication.data.model.Comment
import com.devcommop.myapplication.data.model.Post
import com.devcommop.myapplication.data.model.User
import com.devcommop.myapplication.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

private const val TAG = "##@@Repository"

/**
 * Important points to take care of:--
 *
 * 1.) For each create/update/delete operation [authToken] must be valid all the times
 *
 * 2.) Ensure atomicity of operations where more than 1 transactions are involved
 *
 * 3.) Handle [exceptions] properly for ViewModels
 */
class Repository @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val firebaseStorage: FirebaseStorage
) {
    private val storageReference = firebaseStorage.reference

    suspend fun getPostById(postId: String): Post? {
        try {
            return firestore.collection(Constants.POSTS_COLLECTION).document(postId).get().await()
                .toObject(Post::class.java)
        } catch (exception: Exception) {
            //todo: Handle exception for ViewModel
            return null
        }
    }

    suspend fun getUserById(uid: String): User? {
        try {
            return firestore.collection(Constants.USERS_COLLECTION).document(uid).get().await()
                .toObject(
                    User::class.java
                )
        } catch (exception: Exception) {
            //todo: Handle exception for ViewModel
            return null
        }
    }

    suspend fun addPost(post: Post, user: User) {
        //todo: Ensure atomicity of ops 1.) Creating [Post] in POSTS collection and 2.) Adding postId to [{user} document]
        try {
            //firestore.collection(Constants.POSTS_COLLECTION).
        } catch (exception: Exception) {
            //todo: Handle exception for ViewModel
        }
    }

    //todo: Ensure auth token is valid
    suspend fun deletePost(postId: String) {}

    suspend fun editPost(postId: String) {}

    suspend fun likePost(postId: String, user: User) {}

    suspend fun dislikePost(postId: String, user: User) {}

    suspend fun commentOnPost(postId: String, user: User, comment: Comment) {}

    suspend fun removeCommentOnPost(postId: String, commendId: String) {}

    suspend fun fetchTopKPosts(postsCount: Long): List<Post>? { return null }

    suspend fun fetchLatestKPosts(postsCount: Long): List<Post>? { return null }

    suspend fun getUserFollowers(user: User): List<User>? { return null }

    suspend fun followUser(userToFollow: User, userInitiatingAction: User) {}

    suspend fun unfollowUser(userToUnfollow: User, userInitiatingAction: User) {}

    suspend fun signUpUser(user: User) {}

    suspend fun logOutUser(user: User) { auth.signOut() }

    suspend fun logInUser(user: User) {}

    suspend fun deactivateUser(user: User) { }
}