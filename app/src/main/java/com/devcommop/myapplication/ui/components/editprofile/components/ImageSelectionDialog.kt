package com.devcommop.myapplication.ui.components.editprofile.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.devcommop.myapplication.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageSelectionDialog(
    onHideDialog: () -> Unit,
    onGallerySelected: () -> Unit,
    onCameraSelected: () -> Unit
) {

    AlertDialog(
        onDismissRequest = {
            onHideDialog()
        }

    ) {
        Surface(
            modifier = Modifier.fillMaxWidth()
                .padding(all = 32.dp),
            shape = RectangleShape,
            color = MaterialTheme.colorScheme.background,
            border = BorderStroke(2.dp, MaterialTheme.colorScheme.outline),
            tonalElevation = 8.dp
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 32.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 8.dp),
                    text = "Select an Image Source: ",
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    fontWeight = FontWeight.Bold
                )
                Row(

                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = stringResource(id = R.string.upload_from_gallery),
                        modifier = Modifier.padding(horizontal = 4.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Icon(
                        imageVector = Icons.Default.PhotoLibrary,
                        modifier = Modifier.clickable {
                            onGallerySelected()
                            onHideDialog()
                        },
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.weight(1f))

                }
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = stringResource(id = R.string.capture_from_camera),
                        modifier = Modifier.padding(horizontal = 4.dp),
                        style = MaterialTheme.typography.bodyMedium

                    )
                    Icon(
                        imageVector = Icons.Default.Camera,
                        modifier = Modifier.clickable {
                            onCameraSelected()
                            onHideDialog()
                        },
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.weight(1f))

                }
            }
        }

    }

}
