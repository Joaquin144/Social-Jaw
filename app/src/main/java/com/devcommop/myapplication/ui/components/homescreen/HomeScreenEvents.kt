package com.devcommop.myapplication.ui.components.homescreen

sealed class HomeScreenEvents {
    object Refresh: HomeScreenEvents()//refresh the posts list
}