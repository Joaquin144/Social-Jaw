package com.devcommop.myapplication.ui.screens

sealed class TopAppBarScreen(val route: String ) {
    object SearchScreen : TopAppBarScreen("search_screen")
    object messageScreen : TopAppBarScreen("message_screen")
}

