package com.devcommop.myapplication.ui.components.mainscreen.createpost


import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.devcommop.myapplication.R
import com.devcommop.myapplication.ui.components.mainscreen.common.TransparentHintTextField
import kotlinx.coroutines.flow.collectLatest
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Objects

@Composable
fun CreatePostScreen(navController: NavController) {

    val context = LocalContext.current
    val file = context.createImageFile()
    val uri = FileProvider.getUriForFile(
        Objects.requireNonNull(context),
//        BuildConfig.APPLICATION_ID + ".provider", file
        "com.devcommop.myapplication.provider", file
    )

    var capturedImageUri by remember {
        mutableStateOf<Uri>(Uri.EMPTY)
    }

    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
        capturedImageUri = uri
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
            cameraLauncher.launch(uri)
        } else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }
    var postContent by remember { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // User profile icon
            Image(
                painter = painterResource(id = R.drawable.dummy_profile_picture), // Replace with your own image resource
                contentDescription = "User Profile",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(40.dp)
                    .padding(horizontal = 4.dp)
                    .clip(shape = MaterialTheme.shapes.small)
            )
            Column() {
                TextField(value = postContent,
                    onValueChange = { postContent = it },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.Black),
//                colors = TextFieldDefaults.textFieldColors(
//                    backgroundColor = Color.LightGray,
//                    focusedIndicatorColor = Color.Transparent,
//                    unfocusedIndicatorColor = Color.Transparent
//                ),
                    singleLine = true,
                    placeholder = { Text(text = "Write a post...") })
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    UploadIcon(Icons.Default.PhotoLibrary, onClick = {
                        Toast.makeText(
                            context,
                            "Upload from Gallery Pressed",
                            Toast.LENGTH_LONG
                        ).show()
                    }, text = "Gallery")
                    UploadIcon(Icons.Default.CameraAlt, onClick = {
                        val permissionCheckResult =
                            ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                        if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                            cameraLauncher.launch(uri)
                        } else {
                            // Request a permission
                            permissionLauncher.launch(Manifest.permission.CAMERA)
                        }
                    }, text = "Camera")
                }
            }
        }

        if (capturedImageUri.path?.isNotEmpty() == true) {
            Image(
                modifier = Modifier.padding(16.dp, 8.dp),
                painter = rememberAsyncImagePainter(capturedImageUri),
                contentDescription = null
            )
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RowScope.UploadIcon(icon: ImageVector, onClick: () -> Unit, text: String) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .weight(1f) ,
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
    ) {
        Row(
            modifier = Modifier
                .height(32.dp)
                .padding(vertical = 2.dp, horizontal = 8.dp)
                .clickable(
                    onClick = { onClick() }
                ),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
        ) {
//            Spacer(modifier = Modifier.weight(1f))
            Icon(
                modifier = Modifier.padding(horizontal = 4.dp),
                imageVector = icon,
                contentDescription = "Upload Image",
                tint = Color.Black
            )
            Text(
                text = text,
                color = Color.Black
            )
//            Spacer(modifier = Modifier.weight(1f))

        }
    }
    Spacer(
        modifier = Modifier
            .width(2.dp)
            .background(Color.DarkGray)
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CreatePostScreen(navController = rememberNavController())
}

@Composable
fun CreatePostScreenOlder(
    navController: NavController, viewModel: CreatePostViewModel = hiltViewModel()
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


