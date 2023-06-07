package com.devcommop.myapplication.ui.components.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devcommop.myapplication.data.repository.Repository
import com.devcommop.myapplication.ui.components.authscreen.SignInResult
import com.devcommop.myapplication.ui.components.authscreen.SignInState
import com.devcommop.myapplication.ui.components.authscreen.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
//class AuthViewModel @Inject constructor() : ViewModel() {
    class AuthViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

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

    fun onSignOutResult() {
        _state.update { SignInState() }
        _userData.update { UserData() }
    }
}