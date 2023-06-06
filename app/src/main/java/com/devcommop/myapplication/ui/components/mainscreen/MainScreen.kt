package com.devcommop.myapplication.ui.components.mainscreen

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.animation.core.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.devcommop.myapplication.R
import com.devcommop.myapplication.ui.components.viewmodel.AuthViewModel
import com.devcommop.myapplication.ui.navigation.BottomBarNavGraph
import com.devcommop.myapplication.ui.screens.BottomBarScreen

//fun MainScreen (userData: UserData?, onSignOut: () -> Unit, viewModel : AuthViewModel = hiltViewModel()) {

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun MainScreen( onSignOut: () -> Unit) {
//    val viewModel = viewModel<AuthViewModel>()
    val viewModel: AuthViewModel = viewModel(LocalContext.current as ComponentActivity)

    val state by viewModel.state.collectAsState()
    val currentUser = viewModel.userData.collectAsState().value
    Log.d("HiltChecking", currentUser.toString())
    val navController = rememberNavController()
    Scaffold(
        topBar = {
            TopBarSection(navController , onSignOut)
        },
        bottomBar = { BottomBarSection(navController) },
    ) { innerPadding ->
        BottomBarNavGraph(
            modifier = Modifier.padding(innerPadding),
            navHostController = navController,
//            userData,
            onSignOut
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarSection(
    navController: NavHostController,
    onSignOut: () -> Unit
) {
    // scrollable TopAppBar code
    val topAppBarScrollState = rememberTopAppBarState()
    val scrollBehavior =
        TopAppBarDefaults.enterAlwaysScrollBehavior(
            state = topAppBarScrollState,
            canScroll = { true })

    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.app_name),
                fontWeight = FontWeight.SemiBold,
                color = Color.Red,
                fontFamily = FontFamily.Monospace
            )
        },
        actions = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                modifier = Modifier
                    .size(32.dp)
                    .padding(end = 4.dp)
                    .clickable {
                        navController.navigate("search_screen")
                    }
            )
            Icon(
                painter = painterResource(id = R.drawable.baseline_comment_24),
                contentDescription = "Message",
                modifier = Modifier
                    .size(32.dp)
                    .padding(end = 4.dp)
                    .clickable{
                        navController.navigate("message_screen")
                    }
            )
        },
        scrollBehavior = scrollBehavior // scrollBehavior
    )

}

@Composable
fun BottomBarSection(navController: NavHostController) {
    // list of all bottom bar screens
    val screens = listOf(
        BottomBarScreen.HomeScreen,
        BottomBarScreen.ShortsScreen,
        BottomBarScreen.CreatePostScreen,
        BottomBarScreen.UserProfileScreen,
        BottomBarScreen.SettingsScreen
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomAppBar {
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }

}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen, currentDestination: NavDestination?, navController: NavHostController
) {
    NavigationBarItem(enabled = true,
        label = { Text(text = screen.title) },
        onClick = {
            navController.navigate(route = screen.route) {
                //will pop up all the recently opened bottom bar screens from backstack
                currentDestination?.route?.let {
                    popUpTo(it) {
                        inclusive = true
                    }
                }
                launchSingleTop = true
            }
        },
        icon = { Icon(imageVector = screen.icon, contentDescription = screen.title) },
        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true

    )
}


