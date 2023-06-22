package com.devcommop.myapplication.ui.components.searchscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.devcommop.myapplication.ui.screens.OtherScreens

@Composable
fun SearchScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    hint: String = "Search",
    onSearch: (String) -> Unit = {},
    leadingIcon: ImageVector = Icons.Default.Search,
    trailingIcon: ImageVector = Icons.Default.Clear,
    viewModel: SearchScreenViewModel = hiltViewModel()
) {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        modifier
            .padding(horizontal = 16.dp, vertical = 32.dp)
            .safeContentPadding()
        Column() {
            SearchBar(viewModel = viewModel)
            Spacer(modifier = Modifier.height(16.dp))
            SearchResults(viewModel = viewModel, navController = navController)
        }
    }
}

@Composable
fun SearchBar(
    viewModel: SearchScreenViewModel
) {
    var searchQuery by remember { mutableStateOf("") }

    TextField(
        value = searchQuery,
        onValueChange = { query ->
            searchQuery = query
        },
        label = { Text("Search") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = {
            if (searchQuery.isNotEmpty() && searchQuery.isNotBlank()) {
                viewModel.onEvent(SearchScreenEvents.OnSearchQuerySubmit(searchQuery))
            }
        }),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun SearchResults(viewModel: SearchScreenViewModel, navController: NavController) {
    val usersListResult = viewModel.usersListResult.value
    if (usersListResult.isNotEmpty()) {
        LazyColumn {
            items(usersListResult) { userResult ->
//                Text(text = result, modifier = Modifier.padding(8.dp))
                UserSearchItem(user = userResult, onUserProfilePicClick = {
                    navController.navigate(OtherScreens.PublicUserProfileScreen.route + "/${userResult.uid}") {
                        launchSingleTop = false//todo: decide whether allow multiple tops or not
                    }
                })
            }
        }
    } else {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "No results found", modifier = Modifier.padding(8.dp))
        }
    }
}