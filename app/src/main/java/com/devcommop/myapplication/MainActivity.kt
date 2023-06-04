package com.devcommop.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.devcommop.myapplication.ui.components.authscreen.AuthScreen
import com.devcommop.myapplication.ui.components.authscreen.AuthViewModel
import com.devcommop.myapplication.ui.components.authscreen.ForgotPasswordScreen
import com.devcommop.myapplication.ui.components.authscreen.GoogleAuthUiClient
import com.devcommop.myapplication.ui.components.authscreen.LoginScreen
import com.devcommop.myapplication.ui.components.authscreen.RegisterScreen
import com.devcommop.myapplication.ui.components.mainscreen.MainScreen
import com.devcommop.myapplication.ui.screens.AuthScreen
import com.devcommop.myapplication.ui.theme.MyApplicationTheme
import com.google.android.gms.auth.api.identity.Identity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

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
                    NavHost(
                        navController = navController,
                        startDestination = "auth"
                    )
                    {
                        navigation(
                            route = "auth",
                            startDestination = AuthScreen.LoginScreen.route
                        ) {
                            composable(route = AuthScreen.LoginScreen.route) {

                                LaunchedEffect(key1 = Unit) {
                                    if (googleAuthUiClient.getSignedInUser() != null) {
                                        // TODO: navigate to MainScreen()
                                        navController.navigate("main")
                                    }
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

                                LaunchedEffect(key1 = state.isSignInSuccessful) {
                                    if (state.isSignInSuccessful) {
                                        Toast.makeText(
                                            applicationContext,
                                            "Sign in successful",
                                            Toast.LENGTH_LONG
                                        ).show()
                                        navController.navigate("main")
                                        viewModel.resetState()
                                    }
                                }

                                LoginScreen(
                                    state = state,
                                    onSignInClick = {
                                        lifecycleScope.launch {
                                            val signInIntentSender = googleAuthUiClient.signIn()
                                            launcher.launch(
                                                IntentSenderRequest.Builder(
                                                    signInIntentSender ?: return@launch
                                                ).build()
                                            )
                                        }
                                    },
                                    onNavigateToForgotPassword = {
                                        navController.navigate(AuthScreen.ForgotPasswordScreen.route)
                                    },
                                    onNavigateToRegister = {
                                        navController.navigate(AuthScreen.RegisterScreen.route)
                                    }
                                )
                            }
                            composable(route = AuthScreen.RegisterScreen.route) {
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
                                RegisterScreen(
                                    state = state,
                                    onSignInClick = {
                                        lifecycleScope.launch {
                                            val signInIntentSender = googleAuthUiClient.signIn()
                                            launcher.launch(
                                                IntentSenderRequest.Builder(
                                                    signInIntentSender ?: return@launch
                                                ).build()
                                            )
                                        }
                                    })
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
                                    userData = googleAuthUiClient.getSignedInUser(),
                                    onSignOut = {
                                        lifecycleScope.launch {
                                            googleAuthUiClient.signOut()
                                            Toast.makeText(
                                                applicationContext,
                                                "Signed out",
                                                Toast.LENGTH_LONG
                                            ).show()

                                            navController.popBackStack()
                                        }
                                    }
                                )
                            }
                        }

                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        AuthScreen()
    }
}
