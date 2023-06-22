package com.devcommop.myapplication.ui.screens

sealed class TopAppBarScreen(val route: String ) {
    object SearchScreen : TopAppBarScreen("search_screen")
    object MessageScreen : TopAppBarScreen("message_screen")
}

