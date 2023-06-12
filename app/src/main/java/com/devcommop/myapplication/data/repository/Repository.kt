package com.devcommop.myapplication.data.repository

import android.util.Log
import androidx.core.net.toUri
import com.devcommop.myapplication.data.model.Comment
import com.devcommop.myapplication.data.model.NotificationData
import com.devcommop.myapplication.data.model.Post
import com.devcommop.myapplication.data.model.PushNotification
import com.devcommop.myapplication.data.model.ShortItem
import com.devcommop.myapplication.data.model.User
import com.devcommop.myapplication.services.MyFirebaseMessagingService
import com.devcommop.myapplication.ui.components.authscreen.UserData
import com.devcommop.myapplication.ui.components.settings.notifications.NotificationState
import com.devcommop.myapplication.utils.CommonUtils
import com.devcommop.myapplication.utils.Constants
import com.devcommop.myapplication.utils.CustomException
import com.devcommop.myapplication.utils.ModelUtils
import com.devcommop.myapplication.utils.NotificationApiInstance
import com.devcommop.myapplication.utils.NotificationUtils
import com.devcommop.myapplication.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val TAG = "##@@Repository"

/**
 * Important points to take care of:--
 *
 * 1.) For each create/update/delete operation "authToken" must be valid all the times
 *
 * 2.) Ensure atomicity of operations where more than 1 transactions are involved
 *
 * 3.) Handle "exceptions" properly for ViewModels.
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
     * This function takes a [UserData] object called [userData] and creates that [User] in Firebase Firestore Database
     * @param userData The very minimal data of user just created at Auth
     * @return A [Resource] of type [User] that will either be a [Resource.Success] or [Resource.Error]
     */
    suspend fun editUser(userData: UserData): Resource<User> {/*Aim:--- Ensure these steps are followed
            i) If if user already exists in database then just do "NOTHING". ✅
            ii) Create User object and associate userData to it. ✅
            iii) Create this user object in firestore database. ✅
         */
        return withContext(Dispatchers.IO) {
            try {
                val user = User()
                ModelUtils.associateUserDataToUser(userData = userData, user = user)
                val dbUser =
                    firestore.collection(Constants.USERS_COLLECTION).document(user.uid).get()
                        .await().toObject(User::class.java)
                if (dbUser != null) {
                    Log.d(
                        TAG, "User was already created in DB. Not creating it again during SignIn"
                    )
                    return@withContext Resource.Success<User>(data = dbUser)
                }
                firestore.collection(Constants.USERS_COLLECTION).document(user.uid).set(user)
                    .await()
                Resource.Success<User>(data = user)
            } catch (exception: Exception) {
                Resource.Error<User>(
                    message = exception.message
                        ?: "Unknown error occurred. The post couldn't be fetched"
                )
            }
        }
    }

    suspend fun editUser(user: User): Resource<User> {
        return withContext(Dispatchers.IO) {
            try {
                // upload the images on firebase && update the downloaded url
                user.profilePictureUrl = downloadUrlFromFirebaseStorage(
                    user.profilePictureUrl, "profilePictures/${user.uid}.jpg"
                )
                user.coverPictureUrl = downloadUrlFromFirebaseStorage(
                    user.coverPictureUrl, "coverPictures/${user.uid}.jpg"
                )

                firestore.collection(Constants.USERS_COLLECTION).document(user.uid).set(user)
                    .await()
                Log.d(TAG, "User DB updated successfully")
                Resource.Success<User>(data = user)
            } catch (exception: Exception) {
                Log.d(TAG, exception.message.toString())
                Resource.Error<User>(
                    message = exception.message
                        ?: "Unknown error occurred. The post couldn't be fetched"
                )
            }
        }
    }

    private suspend fun downloadUrlFromFirebaseStorage(imageUrl: String?, path: String): String? {
        var downloadUrl = null as String?
        withContext(Dispatchers.IO) {
            try {
                imageUrl?.let {
                    downloadUrl = storageReference.child(path).putFile(it.toUri())
                        .await().storage.downloadUrl.await().toString()
                }
            } catch (exception: Exception) {
                Log.d(TAG, exception.message.toString())
            }
        }
        return downloadUrl
    }

    suspend fun postShorts(short: ShortItem, user: User) {
        withContext(Dispatchers.IO) {
            try {
//                ModelUtils.assosiateShortItemToUser(short = short, user = user)
                //TODO: upload video to firebase storage
                val videoUri = short.mediaUrl?.toUri()
                var downloadUrl = null as String?
                Log.d(TAG, "Uploading video to firebase storage with url $videoUri")
                downloadUrl = videoUri?.let { uri ->
                    storageReference.child("shorts/${short.id}.mp4").putFile(uri)
                        .await().storage.downloadUrl.await().toString()
                }
                Log.d(TAG, "Successfully uploaded video to firebase storage $downloadUrl")
                //TODO: make a new shortItem
                short.mediaUrl = downloadUrl

                //TODO: upload the video to firestore db
                withContext(Dispatchers.IO) {
                    try {
                        Log.d(TAG, "Uploading video to firestore db ${short.mediaUrl}")
                        firestore.collection(Constants.SHORTS_COLLECTION).document(short.id)
                            .set(short).await()
                        Log.d(TAG, "Successfully uploaded video to firestore ${short.mediaUrl}")
                    } catch (exception: Exception) {
                        Log.d(
                            TAG, "Error uploading video to firestore $exception.message.toString()"
                        )
                    }
                }
            } catch (exception: Exception) {
                Log.d(TAG, exception.message.toString())
            }
        }
    }

    suspend fun getUserDetailsById(id: String): Resource<User> {
        return withContext(Dispatchers.IO) {
            try {
                val user =
                    firestore.collection(Constants.USERS_COLLECTION).document(id).get().await()
                        .toObject(User::class.java)
                Log.d(TAG, "Successfully fetched user details $user")
                if (user == null) {
                    throw Exception("User not found")
                }
                Resource.Success<User>(data = user)
            } catch (
                exception: Exception
            ) {
                Log.d(TAG, exception.message.toString())
                Resource.Error<User>(
                    message = exception.message ?: "Unable to get user details"
                )
            }
        }

    }

    suspend fun fetchShortsFromDB(): Resource<List<ShortItem>> {
        return withContext(Dispatchers.IO) {
            Log.d(TAG, "Fetching shorts from DB")
            try {
                val shortItems = firestore.collection(Constants.SHORTS_COLLECTION).get().await()
                    .toObjects(ShortItem::class.java)
                Log.d(TAG, "Successfully fetched shorts from DB ${shortItems.toString()}")
                Resource.Success<List<ShortItem>>(data = shortItems)
            } catch (exception: Exception) {
                Log.d(TAG, exception.message.toString())
                Resource.Error<List<ShortItem>>(
                    message = exception.message ?: "Something went wrong. Please try again later"
                )
            }
        }
    }

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
                        .toObject(Post::class.java) ?: throw CustomException(
                        message = "Post was deleted by the author or removed by us for community guidelines violations"
                    )
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
                        ?: throw CustomException(message = "User account was deactivated/ deleted.")
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
    suspend fun addPost(post: Post, user: User): Resource<Post> {/*Aim: Ensure atomicity of this op which contains 2 transactions:----
             i. Creating [Post] in POSTS collection and ✅
             ii. Adding postId to [User] ✅
         */
        return withContext(Dispatchers.IO) {
            try {
                // if url is null, then remove the entry from list
                post.imagesUrl?.let { list ->
                    if (list.isNotEmpty() && list[0] == null) list.dropLast(1)
                }
                ModelUtils.associatePostToUser(post, user)
                var downloadUrl: String? = null
                if (post.imagesUrl?.isNullOrEmpty() == false) {
                    try {
                        downloadUrl = storageReference.child("images/${post.postId}.jpg")
                            .putFile(post.imagesUrl!![0].toUri())
                            .await().storage.downloadUrl.await().toString()
                    } catch (e: Exception) {
                        Log.d(TAG, "Error in uploading img to firebase cloud: $e")
                    }
                }
                if (downloadUrl != null) post.imagesUrl = listOf(downloadUrl)
                Log.d(TAG, "imageUrl @Cloud Firebase : ${downloadUrl.toString()}")
                Log.d(TAG, "imageUrl in Post : ${post.imagesUrl.toString()}")
                val postRef = firestore.collection(Constants.POSTS_COLLECTION).document(post.postId)
                val userRef = firestore.collection(Constants.USERS_COLLECTION).document(user.uid)
                firestore.runBatch { batch ->
                    batch.set(postRef, post)
                    batch.update(userRef, "posts", FieldValue.arrayUnion(post.postId))
                }.await()
                Resource.Success<Post>(data = post)
            } catch (exception: Exception) {
                Log.d(TAG, "addPost: $exception")
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
    suspend fun deletePost(post: Post, user: User): Resource<Post> {/*Aim: Ensure atomicity of this op which contains these transactions:----
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
     * @param newPost The newPost which will replace the old post
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
    suspend fun likePost(post: Post, user: User): Resource<Post> {/*Aim: Ensure atomicity of this op which contains these transactions:----
             i. Add this post's id to user's likedPosts field ✅
             ii. Remove this post's id from user's disliked field ✅
             iii. Add this user's id to this post's likedByUsers field ✅
             iv. Remove this user' id from this post's dislikedByUsers field ✅
             v. Increasing likesCount of this post ✅
             vi. Decrease this post's dislikesCount ✅ ❓ [maybe wrong code]
             vii. Send Notification to that user //todo: Replace it with some cloud function or admin sdk.
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
                    if (post.dislikesCount != null && post.dislikesCount!! > 0) batch.update(
                        postRef,
                        "dislikesCount",
                        FieldValue.increment(-1)
                    )
                }.await()
                sendNotification(
                    PushNotification(
                        data = NotificationData(
                            title = "You received a like from ${post.authorFullName ?: "some user"}",
                            message = ""
                        ),
                        to = "${post.authorId}_like"//todo: fill it
                    )
                )
                Resource.Success<Post>(data = post)
            } catch (exception: Exception) {
                Log.d(TAG, "Like failed: ${exception.message}")
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
    suspend fun dislikePost(post: Post, user: User): Resource<Post> {/*Aim: Ensure atomicity of this op which contains these transactions:----
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
                    if (post.likesCount != null && post.likesCount!! > 0) batch.update(
                        postRef,
                        "likesCount",
                        FieldValue.increment(-1)
                    )
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
     * This function takes 3 objects of type [Post], [User], [Comment] and creates a [comment] on that [post] by the [user]
     * @param post The object [Post] on which comment has to be created
     * @param user The object [User] the user which is initiating the action
     * @param comment The object [Comment] which has to be created
     * @return A [Resource] of type [Comment] that will either be a [Resource.Success] or [Resource.Error]
     */
    suspend fun commentOnPost(post: Post, user: User, comment: Comment): Resource<Comment> {
        //todo: Block offensive comments [ML or smth]
        /*Aim: Ensure atomicity of this op which contains these transactions:----
             i. Add this comment's id to user's comments' field ✅
             ii. Add this comment's id to post's comments' field ✅
             iii. Add this comment to Comments collection ✅
             iv. Increasing commentsCount for this post ✅
         */
        return withContext(Dispatchers.IO) {
            try {
                val postRef = firestore.collection(Constants.POSTS_COLLECTION).document(post.postId)
                val userRef = firestore.collection(Constants.USERS_COLLECTION).document(user.uid)
                if (comment.commentId == "") comment.commentId = CommonUtils.getAutoId()
                val commentsRef =
                    firestore.collection(Constants.COMMENTS_COLLECTION).document(comment.commentId)
                firestore.runBatch { batch ->
                    //todo: merge these ops to reduce billing
                    batch.update(userRef, "comments", FieldValue.arrayUnion(comment.commentId))
                    batch.update(postRef, "comments", FieldValue.arrayUnion(comment.commentId))
                    batch.update(postRef, "commentsCount", FieldValue.increment(1))
                    batch.set(commentsRef, comment)
                }.await()
                Resource.Success<Comment>(data = comment)
            } catch (exception: Exception) {
                Resource.Error<Comment>(
                    message = exception.message
                        ?: "Unknown error occurred. The post couldn't be liked"
                )
            }
        }
    }

    /**
     * This function takes 3 objects of type [Post], [User], [Comment] and deletes the [comment] on that [post] by the [user]
     * @param post The object [Post] on which comment has to be deleted
     * @param user The object [User] the user which is initiating the action
     * @param comment The object [Comment] which has to be deleted
     * @return A [Resource] of type [Comment] that will either be a [Resource.Success] or [Resource.Error]
     */
    suspend fun removeCommentOnPost(post: Post, user: User, comment: Comment): Resource<Comment> {/*Aim: Ensure atomicity of this op which contains these transactions:----
             i. Delete this comment's id to user's comments' field ✅
             ii. Delete this comment's id to post's comments' field ✅
             iii. Delete this comment to Comments collection ✅
             iv. Decreasing commentsCount for this post ✅
         */
        return withContext(Dispatchers.IO) {
            try {
                val postRef = firestore.collection(Constants.POSTS_COLLECTION).document(post.postId)
                val userRef = firestore.collection(Constants.USERS_COLLECTION).document(user.uid)
                if (comment.commentId == "") comment.commentId = CommonUtils.getAutoId()
                val commentsRef =
                    firestore.collection(Constants.COMMENTS_COLLECTION).document(comment.commentId)
                firestore.runBatch { batch ->
                    //todo: merge these ops to reduce billing
                    batch.update(userRef, "comments", FieldValue.arrayRemove(comment.commentId))
                    batch.update(postRef, "comments", FieldValue.arrayRemove(comment.commentId))
                    batch.update(
                        postRef, "commentsCount", FieldValue.increment(-1)
                    )//todo: ensure this is atleast 0 all times
                    batch.delete(commentsRef)
                }.await()
                Resource.Success<Comment>(data = comment)
            } catch (exception: Exception) {
                Resource.Error<Comment>(
                    message = exception.message
                        ?: "Unknown error occurred. The post couldn't be liked"
                )
            }
        }
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
                    .orderBy("likesCount", Query.Direction.DESCENDING).get().await()
                    .map { documentSnapshot ->
                        postsList.add(documentSnapshot.toObject(Post::class.java))
                    }
                Resource.Success<List<Post>>(data = postsList)
            } catch (exception: Exception) {
                Log.d(TAG, exception.message.toString())
                Resource.Error<List<Post>>(
                    message = exception.message
                        ?: "Unknown error occurred. The post couldn't be fetched"
                )
            }
        }
    }

    suspend fun fetchLatestKPosts(postsCount: Long): Resource<List<Post>> {
        return withContext(Dispatchers.IO) {
            try {
                val postsList: MutableList<Post> = mutableListOf()
                firestore.collection(Constants.POSTS_COLLECTION).limit(postsCount)
                    .orderBy("timestamp", Query.Direction.DESCENDING).get().await()
                    .map { documentSnapshot ->
                        postsList.add(documentSnapshot.toObject(Post::class.java))
                    }
                Resource.Success<List<Post>>(data = postsList)
            } catch (exception: Exception) {
                Log.d(TAG, exception.message.toString())
                Resource.Error<List<Post>>(
                    message = exception.message
                        ?: "Unknown error occurred. The post couldn't be fetched"
                )
            }
        }
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
                    .await().toObject(User::class.java)
                if (user == null) throw CustomException(message = "User cannot be found")
                val usersList = user.followers
                Resource.Success<List<String>>(data = usersList ?: emptyList())//(data = usersList)
            } catch (exception: Exception) {
                Resource.Error<List<String>>(
                    message = exception.message
                        ?: "Unknown error occurred. The post couldn't be fetched"
                )
            }
        }
    }

    /**
     * This function causes [userInitiatingAction] to follow [userToFollow] on Database
     * @param userToFollow of type [User] whose has to be followed
     * @param userInitiatingAction of type [User] whose has initiated the action
     * @return A [Resource] of type [User] that will either be a [Resource.Success] or [Resource.Error]
     */
    suspend fun followUser(userToFollow: User, userInitiatingAction: User): Resource<User> {/*Aim: Ensure atomicity of this op which contains these transactions:----
             i. Add this user2's id to user1's followers field ✅
             ii. Add this user1's id to user2's following field ✅
             iii. Increasing followingCount field of user2 ✅
             iv. Increasing followersCount field for user1 ✅
         */
        return withContext(Dispatchers.IO) {
            try {
                val user1Ref =
                    firestore.collection(Constants.USERS_COLLECTION).document(userToFollow.uid)
                val user2Ref = firestore.collection(Constants.USERS_COLLECTION)
                    .document(userInitiatingAction.uid)
                firestore.runBatch { batch ->
                    //todo: merge these ops to reduce billing
                    batch.update(user1Ref, "followers", FieldValue.arrayUnion(userToFollow.uid))
                    batch.update(
                        user2Ref, "following", FieldValue.arrayUnion(userInitiatingAction.uid)
                    )
                    batch.update(user2Ref, "followingCount", FieldValue.increment(1))
                    batch.update(user1Ref, "followersCount", FieldValue.increment(1))
                }.await()
                Resource.Success<User>(data = userToFollow)
            } catch (exception: Exception) {
                Resource.Error<User>(
                    message = exception.message
                        ?: "Unknown error occurred. The post couldn't be liked"
                )
            }
        }
    }

    /**
     * This function causes [userInitiatingAction] to unfollow [userToUnfollow] on Database
     * @param userToUnfollow of type [User] whose has to be unfollowed
     * @param userInitiatingAction of type [User] whose has initiated the action
     * @return A [Resource] of type [User] that will either be a [Resource.Success] or [Resource.Error]
     */
    suspend fun unfollowUser(userToUnfollow: User, userInitiatingAction: User) {/*Aim: Ensure atomicity of this op which contains these transactions:----
             i. Remove this user2's id to user1's followers field ✅
             ii. Remove this user1's id to user2's following field ✅
             iii. Decreasing followingCount field of user2
             iv. Decreasing followersCount field for user1
         */
        return withContext(Dispatchers.IO) {
            try {
                val user1Ref =
                    firestore.collection(Constants.USERS_COLLECTION).document(userToUnfollow.uid)
                val user2Ref = firestore.collection(Constants.USERS_COLLECTION)
                    .document(userInitiatingAction.uid)
                firestore.runBatch { batch ->
                    //todo: merge these ops to reduce billing
                    batch.update(
                        user1Ref, "followers", FieldValue.arrayRemove(userInitiatingAction.uid)
                    )
                    batch.update(user2Ref, "following", FieldValue.arrayRemove(userToUnfollow.uid))
                    //todo: Make sure below fields don't get less than zero
                    batch.update(user2Ref, "followingCount", FieldValue.increment(-1))
                    batch.update(user1Ref, "followersCount", FieldValue.increment(-1))
                }.await()
                Resource.Success<User>(data = userToUnfollow)
            } catch (exception: Exception) {
                Resource.Error<User>(
                    message = exception.message
                        ?: "Unknown error occurred. The post couldn't be liked"
                )
            }
        }
    }

    suspend fun signUpUser(user: User) {

    }

    suspend fun logOutUser(user: User) {
        auth.signOut()
    }

    suspend fun logInUser(user: User) {}

    /**
     * This function takes a [User] object called [user] and deactivates it on Database.
     * @param user The object [User] which has to be deactivated
     * @return A [Resource] of type [User] that will either be a [Resource.Success] or [Resource.Error]
     */
    suspend fun deactivateUser(user: User): Resource<User> {
        return withContext(Dispatchers.IO) {
            try {
                firestore.collection(Constants.USERS_COLLECTION).document(user.uid)
                    .update("isDeactivated", true).await()
                Resource.Success<User>(data = user)
            } catch (exception: Exception) {
                Resource.Error<User>(
                    message = exception.message
                        ?: "Unknown error occurred. The post couldn't be fetched"
                )
            }
        }
    }

    /**
     * This function updates user preferences on database as well as locally
     * @param notificationState of type [NotificationState] The preferred notification settings of the [User]
     * @param user of type [User] who is initiating the action
     * @return A [Resource] of type [User] that will either be a [Resource.Success] or [Resource.Error]
     */
    suspend fun updateUserNotificationsSettings(
        notificationState: NotificationState, user: User
    ): Resource<User> {
        /*  Aim: To do these things either all together or none
            1.) Save notification preferences locally ✅
            2.) Subscribe/Unsubscribe the topics/tokens ✅
            3.) Save those settings on Database ✅

         */
        //todo: Ensure that both either ops are successful or both get failed and no other state should be allowed
        return withContext(Dispatchers.IO) {
            try {
                val options = NotificationUtils.buildOptions(notificationState, user.uid)
                //todo: For comments, likes, follow it is better to have token rather than topics as token is faster, secure and for few devices. topics are only good for public announcements
                MyFirebaseMessagingService.changeFcmTopicsSubscriptionsAndWriteLocally(options)

                firestore.collection(Constants.USERS_COLLECTION).document(user.uid)
                    .collection(Constants.USERS_NOTIFICATION_SUB_COLLECTION)
                    .document(Constants.USERS_NOTIFICATION_SETTINGS_DOC).set(notificationState)
                    .await()
                Resource.Success<User>(data = user)
            } catch (exception: Exception) {
                Log.d(TAG, "updateUserNotificationsSettings failed to update: $exception")
                Resource.Error<User>(
                    message = exception.message ?: "Some unknown error occurred. Please try again"
                )
            }
        }
    }

    private fun sendNotification(notification: PushNotification) =
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = NotificationApiInstance.api.postNotification(
                    notification = notification
                )
                if (response.isSuccessful) {
                    Log.d(TAG, "sendNotification--> Success with response: $response")
                    //Log.d(TAG, "sendNotification--> Success with response: ${Gson().toJson(response)}")
                } else {
                    Log.d(TAG, "sendNotification--> Failed with response: $response")
                }
            } catch (exception: Exception) {
                Log.d(TAG, "sendNotification--> failed with exception= $exception")
            }
        }

    /*
    private fun sendNotification(
        topic: String,
        notificationTitle: String,
        notificationBody: String = "",
        notificationImageUrl: String
    ) {
        val condition = "'$topic' in topics"
        val to = "${Constants.FCM_SENDER_ID}@fcm.googleapis.com"
        val message = RemoteMessage.Builder(to)
            .addData("topic", topic)
            .
    }*/
}