package com.devcommop.myapplication.ui.components.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.devcommop.myapplication.data.model.Post
import com.devcommop.myapplication.data.repository.Repository
import com.devcommop.myapplication.ui.components.authscreen.SignInResult
import com.devcommop.myapplication.ui.components.authscreen.SignInState
import com.devcommop.myapplication.ui.components.authscreen.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
//class AuthViewModel @Inject constructor() : ViewModel() {
    class AuthViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val TAG = "##**AuthViewModel"
    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()
    val _mUserPosts = MutableStateFlow(listOf<Post>())
    val mUserPosts = _mUserPosts.asStateFlow()
    private val _userData = MutableStateFlow(UserData())
    val userData = _userData.asStateFlow()

    fun onSignInResult(result: SignInResult) {
        _state.update {
            it.copy(
                isSignInSuccessful = result.data != null,
                signInError = result.errorMessage
            )
        }
        updateUserData(result?.data)
    }

    fun updateUserData(userData: UserData?) {
        _userData.update {
            UserData(
                userId = userData?.userId,
                username = userData?.username,
                profilePictureUrl = userData?.profilePictureUrl,
            )
        }
    }
    fun showAllPost() {
        var post : List<Post>? = null
        GlobalScope.launch {
            try {
                post =  repository.fetchTopKPosts(5).data
            } catch (e: Exception) {
                Log.d(TAG, "showAllPost: ${e.message}")
            }
        }
        post?.let {
            _mUserPosts.value = it
        }
    }
    fun onSignOutResult() {
        _state.update { SignInState() }
        _userData.update { UserData() }
    }
}