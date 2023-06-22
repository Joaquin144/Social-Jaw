package com.devcommop.myapplication.ui.screens

sealed class OtherScreens(val route: String) {
    object PublicUserProfileScreen : OtherScreens(route = "user_profile")
}