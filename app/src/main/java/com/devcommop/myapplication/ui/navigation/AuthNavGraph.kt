package com.devcommop.myapplication.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.devcommop.myapplication.data.model.SignInState
import com.devcommop.myapplication.ui.components.authscreen.ForgotPasswordScreen
import com.devcommop.myapplication.ui.components.authscreen.LoginScreen
import com.devcommop.myapplication.ui.components.authscreen.RegisterScreen
import com.devcommop.myapplication.ui.screens.AuthScreen

@Composable
fun AuthNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = AuthScreen.LoginScreen.route){
        composable(AuthScreen.LoginScreen.route) {
            LoginScreen(SignInState()) { println("sign in function") }
        }
        composable(AuthScreen.RegisterScreen.route) {
            RegisterScreen(SignInState()) { println("sign in function") }

        }
        composable(AuthScreen.ForgotPasswordScreen.route) {
            ForgotPasswordScreen()
        }
    }

}