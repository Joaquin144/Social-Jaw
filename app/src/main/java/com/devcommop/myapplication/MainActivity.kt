package com.devcommop.myapplication

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.devcommop.myapplication.ui.components.authscreen.ForgotPasswordScreen
import com.devcommop.myapplication.ui.components.authscreen.GoogleAuthUiClient
import com.devcommop.myapplication.ui.components.authscreen.LoginScreen
import com.devcommop.myapplication.ui.components.authscreen.RegisterScreen
import com.devcommop.myapplication.ui.components.mainscreen.MainScreen
import com.devcommop.myapplication.ui.components.viewmodel.AuthViewModel
import com.devcommop.myapplication.ui.screens.AuthScreen
import com.devcommop.myapplication.ui.theme.MyApplicationTheme
import com.google.android.gms.auth.api.identity.Identity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val TAG = "MAIN_ACTIVITY"
    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val viewModel = viewModel<AuthViewModel>()
                    val state by viewModel.state.collectAsState()
                    var startDestination by remember { mutableStateOf(value = "auth") }

                    Log.d(
                        TAG,
                        "onCreate: ${googleAuthUiClient.getSignedInUser().toString()}"
                    )
                    Log.d(
                        TAG,
                        "just at the start : onCreate: ${viewModel.userData.collectAsState().value.toString()}"
                    )

                    val userData = googleAuthUiClient.getSignedInUser()

                    if (userData != null) {
                        viewModel.updateUserData(userData)
                        startDestination = "main"
                    }

                    val launcher = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.StartIntentSenderForResult(),
                        onResult = { result ->
                            if (result.resultCode == RESULT_OK) {
                                lifecycleScope.launch {
                                    val signInResult =
                                        googleAuthUiClient.signInWithIntent(
                                            intent = result.data ?: return@launch
                                        )
                                    viewModel.onSignInResult(signInResult)
                                }
                            }
                        }
                    )

                    // setting up the navigation
                    NavHost(
                        navController = navController,
                        startDestination = startDestination
                    )
                    {
                        // nav-graph composable for navigation inside authentication screens
                        navigation(
                            route = "auth",
                            startDestination = AuthScreen.LoginScreen.route
                        ) {
                            composable(route = AuthScreen.LoginScreen.route) {
                                LaunchedEffect(key1 = state.isSignInSuccessful) {
                                    if (state.isSignInSuccessful) {
                                        Toast.makeText(
                                            applicationContext,
                                            "Sign in successful",
                                            Toast.LENGTH_LONG
                                        ).show()
                                        navController.navigate("main") {
                                            popUpTo(route = "auth") {
                                                inclusive = true
                                            }
                                        }
                                        // viewModel.resetState()
                                    }
                                }

                                LoginScreen(
//                                    state = state,
                                    onSignInClick = performSignIn(launcher),
                                    onNavigateToForgotPassword = {
                                        navController.navigate(AuthScreen.ForgotPasswordScreen.route)
                                    },
                                    onNavigateToRegister = {
                                        navController.navigate(AuthScreen.RegisterScreen.route)
                                    }
                                )
                            }
                            composable(route = AuthScreen.RegisterScreen.route) {
                                LaunchedEffect(key1 = state.isSignInSuccessful) {
                                    if (state.isSignInSuccessful) {
                                        Toast.makeText(
                                            applicationContext,
                                            "Sign in successful",
                                            Toast.LENGTH_LONG
                                        ).show()
                                        navController.navigate("main") {
                                            popUpTo(route = "auth") {
                                                inclusive = true
                                            }
                                        }
                                        // viewModel.resetState()
                                    }
                                }
                                RegisterScreen(
                                    state = state,
                                    onSignInClick = performSignIn(launcher)
                                )
                            }
                            composable(route = AuthScreen.ForgotPasswordScreen.route) {
                                ForgotPasswordScreen()
                            }

                        }
                        navigation(
                            route = "main",
                            startDestination = "main_screen"
                        ) {
                            composable("main_screen") {
                                MainScreen(
                                    userData = viewModel.userData.collectAsState().value,
                                    onSignOut = {
                                        performSignOut(navController = navController)
                                        viewModel.onSignOutResult()
                                    }
                                )
                            }
                        }

                    }
                }
            }
        }
    }


    private fun performSignOut(navController: NavHostController) = lifecycleScope.launch {
        googleAuthUiClient.signOut()
        Toast.makeText(
            applicationContext,
            "Signed out",
            Toast.LENGTH_LONG
        ).show()

        navController.navigate("auth") {
            popUpTo("main") {
                inclusive = true
            }
        }
    }


    private fun performSignIn(launcher: ManagedActivityResultLauncher<IntentSenderRequest, ActivityResult>): () -> Unit =
        {
            lifecycleScope.launch {
                val signInIntentSender = googleAuthUiClient.signIn()
                launcher.launch(
                    IntentSenderRequest.Builder(
                        signInIntentSender ?: return@launch
                    ).build()
                )
            }
        }

}

