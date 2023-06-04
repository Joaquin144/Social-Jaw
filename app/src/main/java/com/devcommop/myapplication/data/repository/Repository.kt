package com.devcommop.myapplication.data.repository

import com.devcommop.myapplication.data.model.Comment
import com.devcommop.myapplication.data.model.Post
import com.devcommop.myapplication.data.model.User
import com.devcommop.myapplication.utils.CommonUtils
import com.devcommop.myapplication.utils.Constants
import com.devcommop.myapplication.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import okhttp3.internal.immutableListOf
import okhttp3.internal.toImmutableList
import java.util.Arrays
import javax.inject.Inject

private const val TAG = "##@@Repository"

/**
 * Important points to take care of:--
 *
 * 1.) For each create/update/delete operation [authToken] must be valid all the times
 *
 * 2.) Ensure atomicity of operations where more than 1 transactions are involved
 *
 * 3.) Handle [exceptions] properly for ViewModels.
 *
 * 4.) Log errors into firebase or smth
 */
class Repository @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val firebaseStorage: FirebaseStorage
) {
    private val storageReference = firebaseStorage.reference

    /**
     * This function takes a [String] object called [postId] and retrieves that [Post] from Firebase Firestore Database
     * @param postId The unique id of that [Post] which has to be searched for
     * @return A [Resource] of type [Post] that will either be a [Resource.Success] or [Resource.Error]
     */
    suspend fun getPostById(postId: String): Resource<Post> {
        return withContext(Dispatchers.IO) {
            try {
                val post =
                    firestore.collection(Constants.POSTS_COLLECTION).document(postId).get().await()
                        .toObject(Post::class.java)
                if (post == null) {
                    throw Exception(message = "Post was deleted by the author or removed by us for community guidelines violations")
                }
                Resource.Success<Post>(data = post)
            } catch (exception: Exception) {
                Resource.Error<Post>(
                    message = exception.message
                        ?: "Unknown error occurred. The post couldn't be fetched"
                )
            }
        }
    }

    /**
     * This function takes a [String] object called [uid] and retrieves that [User] from Firebase Firestore Database
     * @param uid The unique id of that [User] which has to be searched
     * @return A [Resource] of type [User] that will either be a [Resource.Success] or [Resource.Error]
     */
    suspend fun getUserById(uid: String): Resource<User> {
        return withContext(Dispatchers.IO) {
            try {
                val user =
                    firestore.collection(Constants.USERS_COLLECTION).document(uid).get().await()
                        .toObject(User::class.java)
                if (user == null) {
                    throw Exception(message = "User account was deactivated/ deleted.")
                }
                Resource.Success<User>(data = user)
            } catch (exception: Exception) {
                Resource.Error<User>(
                    message = exception.message
                        ?: "Unknown error occurred. The user couldn't be fetched."
                )
            }
        }
    }

    //todo: Ensure auth token is valid before performing such operation so that it user cannot be impersonated [for all CUD ops]
    /**
     * This function takes the [Post] object called [post] and creates it in the Firestore Database.
     *
     * <b>Note: The id will be generated automatically if it is given empty</b>
     * @param post The [Post] which has to be created
     * @param user The [User] which is initiating the create action
     * @return A Resource<Post> that will either be a Success or Error
     */
    suspend fun addPost(post: Post, user: User): Resource<Post> {
        /*Aim: Ensure atomicity of this op which contains 2 transactions:----
             i. Creating [Post] in POSTS collection and
             ii. Adding postId to [User]
         */
        return withContext(Dispatchers.IO) {
            try {
                if (post.postId == "")
                    post.postId = CommonUtils.getAutoId()
                val postRef = firestore.collection(Constants.POSTS_COLLECTION).document(post.postId)
                val userRef = firestore.collection(Constants.USERS_COLLECTION).document(user.uid)
                firestore.runBatch { batch ->
                    batch.set(postRef, post)
                    batch.update(userRef, "posts", FieldValue.arrayUnion(post.postId))
                }.await()
                Resource.Success<Post>(data = post)
            } catch (exception: Exception) {
                Resource.Error<Post>(
                    message = exception.message
                        ?: "Unknown error occurred. The post couldn't be created."
                )
            }
        }
    }

    /**
     * This function takes the [Post] object called [post] and deletes it.
     * @param post The [Post] which has to be deleted
     * @param user The [User] which is initiating the delete action
     * @return A Resource<Post> that will either be a Success or Error
     */
    suspend fun deletePost(post: Post, user: User): Resource<Post> {
        /*Aim: Ensure atomicity of this op which contains these transactions:----
             i. Deleting [Post] in POSTS collection ✅
             ii. Deleting [Post] from {user's} [post] field ✅
             iii. Deleting [Post] from {user's} {likedPosts} and {dislikedPosts} fields ✅
             iv. Deleting Media [Images/ Video] associated with this post ❌ [will do later]
             ## For now not Deleting all this post's comments because that would be an expensive op to delete too many documents and might shoot up the bill
         */
        return withContext(Dispatchers.IO) {
            try {
                val postRef = firestore.collection(Constants.POSTS_COLLECTION).document(post.postId)
                val userRef = firestore.collection(Constants.USERS_COLLECTION).document(user.uid)
                firestore.runBatch { batch ->
                    batch.delete(postRef)
                    //todo: Delete Media [Images/ Video] associated with this post
                    //todo: merge below 3 ops into one single op so as to reduce cost 50% each op
                    batch.update(userRef, "posts", FieldValue.arrayRemove(post.postId))
                    batch.update(userRef, "likedPosts", FieldValue.arrayRemove(post.postId))
                    batch.update(userRef, "dislikedPosts", FieldValue.arrayRemove(post.postId))
                }.await()
                Resource.Success<Post>(data = post)
            } catch (exception: Exception) {
                Resource.Error<Post>(
                    message = exception.message
                        ?: "Unknown error occurred. The Post couldn't be deleted"
                )
            }
        }
    }

    /**
     * This function takes the [Post] object called [newPost]. Ensure that id, authorData of previous and new posts remain the same.
     * Overwrites the [Post] referred to by this [newPost]. If the document does not
     * yet exist, it will be created. If a document already exists, it will be overwritten.
     *
     * @param newpost The newPost which will replace the old post
     * @return A Resource<Post> that will either be a Success or Error
     */
    suspend fun editPost(newPost: Post): Resource<Post> {
        return withContext(Dispatchers.IO) {
            try {
                firestore.collection(Constants.POSTS_COLLECTION).document(newPost.postId)
                    .set(newPost).await()
                Resource.Success<Post>(data = newPost)
            } catch (exception: Exception) {
                Resource.Error<Post>(
                    message = exception.message
                        ?: "Unknown error occurred. The Post couldn't be edited"
                )
            }
        }
    }

    /**
     * This function takes the [Post] object called [post].
     * Likes the [Post] referred to by this [post] for the given [user].
     *
     * @param post The post which has to be liked
     * @param user The user which has initiated the like action
     * @return A Resource<Post> that will either be a Success or Error
     */
    suspend fun likePost(post: Post, user: User): Resource<Post> {
        /*Aim: Ensure atomicity of this op which contains these transactions:----
             i. Add this post's id to user's likedPosts field ✅
             ii. Remove this post's id from user's disliked field ✅
             iii. Add this user's id to this post's likedByUsers field ✅
             iv. Remove this user' id from this post's dislikedByUsers field ✅
             v. Increasing likesCount of this post ✅
             vi. Decrease this post's dislikesCount ✅ ❓ [maybe wrong code]
         */
        return withContext(Dispatchers.IO) {
            try {
                val postRef = firestore.collection(Constants.POSTS_COLLECTION).document(post.postId)
                val userRef = firestore.collection(Constants.USERS_COLLECTION).document(user.uid)
                firestore.runBatch { batch ->
                    //todo: merge these ops to reduce billing
                    batch.update(userRef, "likedPosts", FieldValue.arrayUnion(post.postId))
                    batch.update(userRef, "dislikedPosts", FieldValue.arrayRemove(post.postId))
                    batch.update(postRef, "likedByUsers", FieldValue.arrayUnion(user.uid))
                    batch.update(postRef, "dislikedByUsers", FieldValue.arrayRemove(user.uid))
                    //todo: Check if below ops are consistent operation across multiple devices
                    batch.update(postRef, "likesCount", FieldValue.increment(1))
                    if (post.dislikesCount != null && post.dislikesCount!! > 0)
                        batch.update(postRef, "dislikesCount", FieldValue.increment(-1))
                }.await()
                Resource.Success<Post>(data = post)
            } catch (exception: Exception) {
                Resource.Error<Post>(
                    message = exception.message
                        ?: "Unknown error occurred. The post couldn't be liked"
                )
            }
        }
    }

    /**
     * This function takes the [Post] object called [post].
     * Dislikes the [Post] referred to by this [post] for the given [user].
     *
     * @param post The post which has to be disliked
     * @param user The user which has initiated the dislike action
     * @return A Resource<Post> that will either be a Success or Error
     */
    suspend fun dislikePost(post: Post, user: User): Resource<Post> {
        /*Aim: Ensure atomicity of this op which contains these transactions:----
             i. Add this post's id to user's dislikedPosts field ✅
             ii. Remove this post's id from user's likedPosts field ✅
             iii. Add this user's id to this post's dislikedByUsers field ✅
             iv. Remove this user' id from this post's likedByUsers field ✅
             v. Increasing dislikesCount of this post ✅
             vi. Decrease this post's likesCount ✅ ❓[maybe wrong]
         */
        return withContext(Dispatchers.IO) {
            try {
                val postRef = firestore.collection(Constants.POSTS_COLLECTION).document(post.postId)
                val userRef = firestore.collection(Constants.USERS_COLLECTION).document(user.uid)
                firestore.runBatch { batch ->
                    //todo: merge these ops to reduce billing
                    batch.update(userRef, "dislikedPosts", FieldValue.arrayUnion(post.postId))
                    batch.update(userRef, "likedPosts", FieldValue.arrayRemove(post.postId))
                    batch.update(postRef, "dislikedByUsers", FieldValue.arrayUnion(user.uid))
                    batch.update(postRef, "likedByUsers", FieldValue.arrayRemove(user.uid))
                    //todo: Check if below ops are consistent operation across multiple devices
                    batch.update(postRef, "dislikesCount", FieldValue.increment(1))
                    if (post.likesCount != null && post.likesCount!! > 0)
                        batch.update(postRef, "likesCount", FieldValue.increment(-1))
                }.await()
                Resource.Success<Post>(data = post)
            } catch (exception: Exception) {
                Resource.Error<Post>(
                    message = exception.message
                        ?: "Unknown error occurred. The post couldn't be liked"
                )
            }
        }
    }

    suspend fun commentOnPost(postId: String, user: User, comment: Comment): Resource<Post> {
        return Resource.Error<Post>(message = "Functionality not yet implemented")
    }

    suspend fun removeCommentOnPost(postId: String, commendId: String): Resource<Post> {
        return Resource.Error<Post>(message = "Functionality not yet implemented")
    }

    /**
     * This function takes a [Long] type parameter called [postsCount] (k) and retrieves top [postsCount] (k) Posts from the  Firebase Firestore Database
     * @param postsCount Number of most liked [Post] to be fetched
     * @return A [Resource] of type [Post] that will either be a [Resource.Success] or [Resource.Error]
     */
    suspend fun fetchTopKPosts(postsCount: Long): Resource<List<Post>> {
        return withContext(Dispatchers.IO) {
            try {
                val postsList: MutableList<Post> = mutableListOf()
                firestore.collection(Constants.POSTS_COLLECTION).limit(postsCount)
                    .orderBy("likesCount").get().await().map { documentSnapshot ->
                        postsList.add(documentSnapshot.toObject(Post::class.java))
                    }
                Resource.Success<List<Post>>(data = postsList)
            } catch (exception: Exception) {
                Resource.Error<List<Post>>(
                    message = exception.message
                        ?: "Unknown error occurred. The post couldn't be fetched"
                )
            }
        }
    }

    suspend fun fetchLatestKPosts(postsCount: Long): List<Post>? {
        return null
    }

    /**
     * <b>IMPORTANT:</b> The documentation for this is wrong as the function is still in progress
     *
     * This function takes a [User] type parameter called [user] all its followers of type [User] from the  Firebase Firestore Database
     * @param user of type [User] whose followers are to fetched to be fetched
     * @return A [Resource] of type [List] of [User] that will either be a [Resource.Success] or [Resource.Error]
     */
    suspend fun getUserFollowers(user: User): Resource<List<String>> {
        //todo: Return followers rather than List of String
        //todo: Don't fetch all the data associated with user. A major security risk. Only the basic details of those users should be fetched and not address, and sensitive details.
        //todo: Paginate the data so as not to consume too much memory & causing a crash henceforth
        return withContext(Dispatchers.IO) {
            try {
                val user = firestore.collection(Constants.USERS_COLLECTION).document(user.uid).get()
                    .await().toObject<User>()
                if (user == null)
                    throw Exception(message = "User cannot be found")
                var usersArray = user.followers
                if (usersArray == null)
                    usersArray = emptyArray()
                val usersList = usersArray.toList()
                Resource.Success<List<String>>(data = usersList)
            } catch (exception: Exception) {
                Resource.Error<List<String>>(
                    message = exception.message
                        ?: "Unknown error occurred. The post couldn't be fetched"
                )
            }
        }
    }

    suspend fun followUser(userToFollow: User, userInitiatingAction: User) {}

    suspend fun unfollowUser(userToUnfollow: User, userInitiatingAction: User) {}

    suspend fun signUpUser(user: User) {}

    suspend fun logOutUser(user: User) {
        auth.signOut()
    }

    suspend fun logInUser(user: User) {}

    suspend fun deactivateUser(user: User) {}
}