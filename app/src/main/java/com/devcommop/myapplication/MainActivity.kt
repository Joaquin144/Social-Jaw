package com.devcommop.myapplication

import android.content.Context
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.devcommop.myapplication.data.local.RuntimeQueries
import com.devcommop.myapplication.data.repository.Repository
import com.devcommop.myapplication.services.MyFirebaseMessagingService
import com.devcommop.myapplication.ui.components.authscreen.ForgotPasswordScreen
import com.devcommop.myapplication.ui.components.authscreen.GoogleAuthUiClient
import com.devcommop.myapplication.ui.components.authscreen.LoginScreen
import com.devcommop.myapplication.ui.components.authscreen.RegisterScreen
import com.devcommop.myapplication.ui.components.authscreen.UserData
import com.devcommop.myapplication.ui.components.mainscreen.MainScreen
import com.devcommop.myapplication.ui.components.onboarding.OnBoardingScreen
import com.devcommop.myapplication.ui.components.viewmodel.AuthViewModel
import com.devcommop.myapplication.ui.screens.AuthScreen
import com.devcommop.myapplication.ui.screens.OnBoardingScreen
import com.devcommop.myapplication.ui.theme.MyApplicationTheme
import com.devcommop.myapplication.utils.Constants
import com.devcommop.myapplication.utils.Resource
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val TAG = "##@@MainAct"

    @Inject
    lateinit var repository: Repository

    @Inject
    lateinit var auth: FirebaseAuth

    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupNotificationsFunctionality()
        //loadRuntimeQueries()//todo: do only if the user is logged in

        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    //val viewModel = viewModel<AuthViewModel>()
//                    val viewModel : AuthViewModel = hiltViewModel()
                    val viewModel: AuthViewModel = hiltViewModel()
                    val state by viewModel.state.collectAsState()
                    var startDestination by remember { mutableStateOf(value = "onboarding") }

                    Log.d(
                        TAG,
                        "onCreate: ${googleAuthUiClient.getSignedInUser().toString()}"
                    )
                    Log.d(
                        TAG,
                        "just at the start : onCreate: ${viewModel.userData.collectAsState().value.toString()}"
                    )

                    var userData = googleAuthUiClient.getSignedInUser()

                    if (userData != null) {
                        viewModel.updateUserData(userData)
                        addUserToDatabase(userData!!)
                        loadRuntimeQueries()
                        startDestination = "main"
                    } else {
                        Log.d(TAG, "OOPS the user gotten from googleAuthUiClient is null")
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
                            route = "onboarding",
                            startDestination = OnBoardingScreen.SimpleOnboardingScreen.route
                        ) {
                            composable(route = OnBoardingScreen.SimpleOnboardingScreen.route) {
                                //todo: Fix the below block (there's some delay)
//                                LaunchedEffect(key1 = true) {
//                                    //Aim: Check if user has already completed the onboarding process
//                                    Log.d(TAG, "Checking if user has already completed onboarding in past or not")
//                                    val sharedPreferences = getSharedPreferences(Constants.BASIC_SHARED_PREF_NAME, Context.MODE_PRIVATE)
//                                    val onBoardingDoneInPast = sharedPreferences.getBoolean("OnBoardingCompletedInPast", false)
//                                    if(onBoardingDoneInPast){
//                                        navController.navigate("auth")
//                                    }
//                                }
                                OnBoardingScreen(
                                    onOnBoardingComplete = {
                                        Log.d(TAG, "OnBoarding complete lambda () -> Unit")
                                        navController.navigate("auth") {
                                            popUpTo(route = "onboarding") {
                                                inclusive = true
                                            }
                                        }
                                        val sharedPreferences = getSharedPreferences(Constants.BASIC_SHARED_PREF_NAME, Context.MODE_PRIVATE)
                                        with(sharedPreferences.edit()){
                                            putBoolean("OnBoardingCompletedInPast", true)
                                            apply()
                                        }
                                    }
                                )
                            }
                        }
                        navigation(
                            route = "auth",
                            startDestination = AuthScreen.LoginScreen.route
                        ) {
                            composable(route = AuthScreen.LoginScreen.route) {
                                LaunchedEffect(key1 = state.isSignInSuccessful) {
                                    //Aim: Add user to database
                                    userData = googleAuthUiClient.getSignedInUser()
                                    if (userData != null) {
                                        addUserToDatabase(userData!!)
                                        loadRuntimeQueries()
                                    } else {
                                        //todo: Show error and leave application
                                        Log.d(TAG, "omg userData is null bye bye")
                                    }
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
//                                    userData = viewModel.userData.collectAsState().value,
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

    private fun addUserToDatabase(userData: UserData) {
        lifecycleScope.launch {
            //todo: take repo from di rather than creating here
            when (val addStatus = repository.editUser(userData = userData)) {
                is Resource.Success -> {
                    Log.d(TAG, "User created successfully in Database")
                }

                is Resource.Error -> {
                    Log.d(TAG, "User creation in Database has failed: " + addStatus.message)
                    Toast.makeText(this@MainActivity, addStatus.message, Toast.LENGTH_LONG)
                        .show()
                    //todo: finish this activity & logout user as this is a serious error
                }

                is Resource.Loading -> {}
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

    private fun setupNotificationsFunctionality() {
        MyFirebaseMessagingService.sharedPreferences =
            getSharedPreferences(
                Constants.BASIC_SHARED_PREF_NAME,
                Context.MODE_PRIVATE
            )    //create the SharedPref object
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.d(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            // Aim: To get new FCM registration token
            val token = task.result
            MyFirebaseMessagingService.token = token
        })

        ////Discarded Aim: Subscribe to the required topics
        ///FirebaseMessaging.getInstance().subscribeToTopic(Constants.DEFAULT_FCM_TOPIC)
    }

    private fun loadRuntimeQueries() {
        val uid = auth.uid
        if (uid != null) {
            lifecycleScope.launch {
                val status = repository.getUserById(uid)
                when (status) {
                    is Resource.Success -> {
                        Log.d(
                            TAG,
                            "Use fetched from db successfully. Now setting it as RuntimeQueries.currentUser"
                        )
                        RuntimeQueries.currentUser = status.data
                    }

                    is Resource.Error -> {
                        Log.d(TAG, "User cannot be fetched from firestore database")
                        //todo: finish this activity as this is a serious error
                    }

                    is Resource.Loading -> {}
                }
            }
        }
    }
}

