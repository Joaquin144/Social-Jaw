package com.devcommop.myapplication.ui.screens

sealed class OnBoardingScreen(val route: String) {
    object SimpleOnboardingScreen: OnBoardingScreen(route = "simple_onboarding_screen")
}
