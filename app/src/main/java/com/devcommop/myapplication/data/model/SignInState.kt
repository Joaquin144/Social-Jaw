package com.devcommop.myapplication.data.model

data class SignInState(
    val isSignInSuccessful : Boolean = false ,
    val signInError : String? = null
)