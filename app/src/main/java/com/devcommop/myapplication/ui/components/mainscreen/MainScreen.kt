package com.devcommop.myapplication.ui.components.mainscreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.devcommop.myapplication.R
import com.devcommop.myapplication.data.model.UserData
import com.devcommop.myapplication.ui.navigation.BottomBarNavGraph
import com.devcommop.myapplication.ui.screens.BottomBarScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(userData: UserData?, onSignOut: () -> Unit) {
    val navController = rememberNavController()
    Scaffold(
        topBar = { TopBarSection(onSignOut) },
        bottomBar = { BottomBarSection(navController) },
    ) {
        BottomBarNavGraph(navHostController = navController)
        val top = it.calculateTopPadding()
        val bottom = it.calculateBottomPadding()
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarSection(onSignOut: () -> Unit) {
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
                imageVector = Icons.Default.Search, contentDescription = "Search",
                modifier = Modifier.padding(end = 4.dp)
            )
            Icon(
                painter = painterResource(id = R.drawable.baseline_comment_24),
                contentDescription = "Message"
            )

            Icon(
                painter = painterResource(id = R.drawable.baseline_exit_to_app_24),
                contentDescription = "Log out",
                modifier = Modifier.clickable {
                    onSignOut()
                }
            )

        },
        scrollBehavior = null
    )

}

@Composable
fun BottomBarSection(navController: NavHostController) {
    // list of all bottom bar screens
    val screens = listOf<BottomBarScreen>(
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
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    NavigationBarItem(
        enabled = true,
        label = { Text(text = screen.title) },
        onClick = { navController.navigate(screen.route) },
        icon = { Icon(imageVector = screen.icon, contentDescription = screen.title) },
        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true

    )
}


