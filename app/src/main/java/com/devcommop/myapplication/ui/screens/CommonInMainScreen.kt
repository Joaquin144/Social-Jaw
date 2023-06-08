package com.devcommop.myapplication.ui.screens

sealed class CommonInMainScreen(val route: String ){
    object EditUserProfileScreen : CommonInMainScreen(route = "edit_user_profile_screen")
}
