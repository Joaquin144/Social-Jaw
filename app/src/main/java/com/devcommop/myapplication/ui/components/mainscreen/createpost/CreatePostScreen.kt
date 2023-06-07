package com.devcommop.myapplication.ui.components.mainscreen.createpost


import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.devcommop.myapplication.data.local.RuntimeQueries
import com.devcommop.myapplication.ui.components.buttons.RowScopedHorizontalButtonWithText
import com.devcommop.myapplication.ui.components.mainscreen.common.TransparentHintTextField
import kotlinx.coroutines.flow.collectLatest
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Objects

@Composable
fun CreatePostScreen(viewModel: CreatePostViewModel = hiltViewModel()) {
    val tag = "Image URI"
    val context = LocalContext.current
    val contentState = viewModel.postContent.value
    var file = context.createImageFile()
    var cameraImageUri = FileProvider.getUriForFile(
        Objects.requireNonNull(context),
//        BuildConfig.APPLICATION_ID + ".provider", file
        "com.devcommop.myapplication.provider", file
    )

    val galleryUploadLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        viewModel.onEvent(
            CreatePostEvents.EnteredContent(
                value = contentState.text,
                imageUri = uri
            )
        )
    }

    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
//        capturedImageUri = uri // this will store URI of image captured from camera
        viewModel.onEvent(
            CreatePostEvents.EnteredContent(
                value = contentState.text,
                imageUri = cameraImageUri
            )
        )
        file = context.createImageFile()
        cameraImageUri = FileProvider.getUriForFile(
            Objects.requireNonNull(context),
//        BuildConfig.APPLICATION_ID + ".provider", file
            "com.devcommop.myapplication.provider", file
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isPermissionGranted ->
        if (isPermissionGranted) {
            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
            cameraLauncher.launch(cameraImageUri)
        } else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // User profile icon
                AsyncImage(
                    model = RuntimeQueries.currentUser?.profilePictureUrl,
                    contentDescription = "profile pic",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(40.dp)
                        .padding(horizontal = 4.dp)
                        .clip(shape = MaterialTheme.shapes.small)
                )

                Column {
                    TextField(value = contentState.text,
                        onValueChange = {
                            viewModel.onEvent(
                                CreatePostEvents.EnteredContent(
                                    value = it,
                                    imageUri = contentState.imageUri
                                )
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.Black),
                        singleLine = false,
                        placeholder = { Text(text = "Write a post...") })
//
                    contentState.imageUri?.let { uri ->

                        Box(
                            contentAlignment = Alignment.TopEnd
                        ){
                            IconButton(onClick = {
                                viewModel.onEvent(CreatePostEvents.EnteredContent(
                                    value = contentState.text,
                                    imageUri = null
                                ))
                                Toast.makeText(context, "Image Removed", Toast.LENGTH_LONG).show()
                            }) {
                                Icon(imageVector = Icons.Default.Cancel, contentDescription = "cancell button", modifier = Modifier.size(32.dp))
                            }
                            AsyncImage(
                                model = uri,
                                modifier = Modifier
//                                    .padding(16.dp, 8.dp)
                                    .safeContentPadding()
                                    .fillMaxWidth()
                                    .height(300.dp)
                                ,
                                contentDescription = null)

                        }

                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(0.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(Modifier.weight(1f))
                        RowScopedHorizontalButtonWithText(
                            icon = Icons.Default.PhotoLibrary,
                            onClick = {
                                galleryUploadLauncher.launch("image/*")
                            },
                            text = "Gallery"
                        )
                        RowScopedHorizontalButtonWithText(
                            icon = Icons.Default.CameraAlt,
                            onClick = {
                                val permissionCheckResult =
                                    ContextCompat.checkSelfPermission(
                                        context,
                                        Manifest.permission.CAMERA
                                    )
                                if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                                    cameraLauncher.launch(cameraImageUri)
                                } else {
                                    // Request a permission
                                    permissionLauncher.launch(Manifest.permission.CAMERA)
                                }
                            },
                            text = "Camera"
                        )
                        if(viewModel.isPostValid()) {
                            RowScopedHorizontalButtonWithText(
                                icon = Icons.Default.Done,
                                onClick = {
                                    viewModel.onEvent(CreatePostEvents.SubmitPost)
                                },
                                text = "Post"
                            )
                        }
                    }
                }
            }


        }
    }

}


fun Context.createImageFile(): File {
    // Create an image file name
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"
    val image = File.createTempFile(
        imageFileName, /* prefix */
        ".jpg", /* suffix */
        externalCacheDir      /* directory */
    )
    return image
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CreatePostScreen()
}

@Composable
fun CreatePostScreenOlder(
    navController: NavController, viewModel: CreatePostViewModel = hiltViewModel()
) {

    val contentState = viewModel.postContent.value
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    //todo: Add Images and Video support also
    Surface {
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
                    viewModel.onEvent(CreatePostEvents.EnteredContent(it , contentState.imageUri))
                },
                onFocusChange = {
                    viewModel.onEvent(CreatePostEvents.ChangeContentFocus(it))
                },
                isHintVisible = contentState.isHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.headlineMedium
            )

            Button(shape = RoundedCornerShape(8.dp), onClick = {
                viewModel.onEvent(CreatePostEvents.SubmitPost)
            }) {
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


