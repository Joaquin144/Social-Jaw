package com.devcommop.myapplication.ui.components.mainscreen.createpost

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.devcommop.myapplication.ui.components.mainscreen.common.TransparentHintTextField
import kotlinx.coroutines.flow.collectLatest

@Composable
fun CreatePostScreen(
    navController: NavController,
    viewModel: CreatePostViewModel = hiltViewModel()
) {

    val contentState = viewModel.postContent.value
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    //todo: Add Images and Video support also
    Surface() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextField(
                text = contentState.text,
                hint = contentState.hint,
                onValueChange = {
                    viewModel.onEvent(CreatePostEvents.EnteredContent(it))
                },
                onFocusChange = {
                    viewModel.onEvent(CreatePostEvents.ChangeContentFocus(it))
                },
                isHintVisible = contentState.isHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.headlineMedium
            )

            Button(
                shape = RoundedCornerShape(8.dp),
                onClick = {
                    viewModel.onEvent(CreatePostEvents.SubmitPost)
                }
            ) {
                Text(text = "Add Post")
            }
        }
    }


    LaunchedEffect(key1 = true) {
        viewModel.event.collectLatest { event ->
            when (event) {
                is CreatePostViewModel.UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }

                is CreatePostViewModel.UiEvent.PostUploadedSuccessfully -> {
                    //todo: [Decide] whether to move to the user's profile or navigateUp
                    navController.navigateUp()    //move to the previous screen
                }
            }
        }

    }
}

@Preview
@Composable
fun PreviewCreatePostScreen() {
    //CreatePostScreen()
}