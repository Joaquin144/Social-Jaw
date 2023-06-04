package com.devcommop.myapplication.ui.screens

sealed class AuthScreen(val route :String )
{
    object LoginScreen : AuthScreen(route = "login_screen")
    object RegisterScreen : AuthScreen(route = "register_screen")
    object ForgotPasswordScreen : AuthScreen(route = "forgot_password_screen")
}
