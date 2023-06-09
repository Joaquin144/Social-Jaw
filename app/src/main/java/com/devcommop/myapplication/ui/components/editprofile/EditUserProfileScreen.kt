package com.devcommop.myapplication.ui.components.editprofile

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddLocation
import androidx.compose.material.icons.filled.Details
import androidx.compose.material.icons.filled.DoneOutline
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import com.devcommop.myapplication.ui.components.common.DividerWithSpace
import com.devcommop.myapplication.ui.components.common.ProfileHeaderSection
import com.devcommop.myapplication.ui.components.common.text.EditTextSection
import com.devcommop.myapplication.ui.components.createpost.createImageFile
import com.devcommop.myapplication.ui.components.editprofile.components.DateOfBirthSection
import com.devcommop.myapplication.ui.components.editprofile.components.ImageSelectionDialog
import java.util.Objects

const val TAG = "##@@EditUserProfileScreen"

@Preview(showBackground = true)
@Composable
fun EditUserProfileScreen() {
    val viewModel: EditUserProfileViewModel = hiltViewModel()
    val context = LocalContext.current
    val event = viewModel.event


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

            var showDialog by remember {
                mutableStateOf(false)
            }
            var file = context.createImageFile()
            var cameraImageUri = FileProvider.getUriForFile(
                Objects.requireNonNull(context),
//        BuildConfig.APPLICATION_ID + ".provider", file
                "com.devcommop.myapplication.provider", file
            )
            var coverPictureEdited by remember { mutableStateOf(false) }

            val galleryLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.GetContent(),
                onResult = { uri: Uri? ->
                    viewModel.onEvent(
                        if (coverPictureEdited) {
                            EditUserProfileEvent.EnteredCoverPictureUrl(uri?.toString())
                        } else {
                            EditUserProfileEvent.EnteredProfilePictureUrl(uri?.toString())
                        }
                    )
//                showDialog = false

                })

            val cameraLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.TakePicture(),
                onResult = {
                    file = context.createImageFile()
                    cameraImageUri = FileProvider.getUriForFile(
                        Objects.requireNonNull(context),
//        BuildConfig.APPLICATION_ID + ".provider", file
                        "com.devcommop.myapplication.provider", file
                    )
                    viewModel.onEvent(
                        if (coverPictureEdited) {
                            EditUserProfileEvent.EnteredCoverPictureUrl(cameraImageUri.toString())
                        } else {
                            EditUserProfileEvent.EnteredProfilePictureUrl(cameraImageUri.toString())
                        }
                    )
//                showDialog = false

                }
            )
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

            if (showDialog) {
                ImageSelectionDialog(
                    onHideDialog = {
                        showDialog = false
                    },
                    onGallerySelected = {
                        galleryLauncher.launch("image/*")
                    },
                    onCameraSelected = {
                        // launch camera
                        val permissionCheckResult =
                            ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.CAMERA
                            )
                        if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                            cameraLauncher.launch(cameraImageUri)
                        } else {
                            permissionLauncher.launch(Manifest.permission.CAMERA)
                        }
                    }
                )
            }

//        Button(
//            onClick = {
//                galleryLauncher.launch("image/*")
//            }
//        ) {
//            Text("My Button")
//        }


            if (viewModel.isValidUser()) {
                //contains profile pic ,cover and username and bio
                ProfileHeaderSection(
                    isEditButtonVisible = true,
                    coverPictureUrl = viewModel.coverPictureUrl,
                    onEditCoverPictureClicked = @Composable {
                        //TODO: show a dialog to select image from gallery / camera
                        coverPictureEdited = true
                        showDialog = true

                    },
                    profilePictureUrl = viewModel.profilePictureUrl,
                    onEditProfilePictureClicked = @Composable {
                        //TODO: show a dialog to select image from gallery / camera
                        coverPictureEdited = false
                        showDialog = true
                    },
                    userName = viewModel.userName
                )
                // Edit UserName
                EditTextSection(
                    modifier = Modifier,
                    leadingIcon = { Icons.Default.Details },
                    fieldLabel = "User Name",
                    oldFieldValue = viewModel.userName,
                    onDone = { newFieldValue ->
                        viewModel.onEvent(EditUserProfileEvent.EnteredUserName(newFieldValue))
                    }
                )
                EditableBioSection(
                    bio = viewModel.bio,
                    onUpdateBioDonePressed = { newUserBio ->
                        viewModel.onEvent(EditUserProfileEvent.EnteredBio(newUserBio))
                    }
                )
                DividerWithSpace()

                EditableGenderSection(
                    gender = viewModel.gender,
                    onGenderUpdateDonePressed = { newGender ->
                        viewModel.onEvent(EditUserProfileEvent.EnteredGender(newGender))
                    }
                )
                DateOfBirthSection(
                    viewModel.dob ?: "",
                    onDateSelected = { date ->
                        viewModel.onEvent(EditUserProfileEvent.EnteredDob(date))
                    }
                )
                DividerWithSpace()


                viewModel.apply {
                    EditableAddressCard(address,
                        city,
                        country,
                        onAddressUpdated = { newAddress ->
                            onEvent(EditUserProfileEvent.EnteredAddress(newAddress))
                        },
                        onCityUpdated = { newCity ->
                            onEvent(EditUserProfileEvent.EnteredCity(newCity))
                        },
                        onCountryUpdated = { newCountry ->
                            onEvent(EditUserProfileEvent.EnteredCountry(newCountry))
                        })
                    DividerWithSpace()

                    EditableEmploymentCard(
                        employmentStatus,
                        company,
                        yearsOfExperience,
                        onEmploymentStatusUpdated = { newEmploymentStatus ->
                            onEvent(EditUserProfileEvent.EnteredEmploymentStatus(newEmploymentStatus))
                        },
                        onCompanyUpdated = { newCompany ->
                            onEvent(EditUserProfileEvent.EnteredCompany(newCompany))
                        },
                        onYearsOfExperienceUpdated = { newYearsOfExperience ->
                            onEvent(
                                EditUserProfileEvent.EnteredYearsOfExperience(
                                    newYearsOfExperience.toInt()
                                )
                            )
                        }
                    )
                    DividerWithSpace()


                }


            } else {
                Log.d(TAG, "Edit ProfileScreen :Current user is null")
                //TODO: show snackbar
            }

        FloatingActionButton(
            modifier = Modifier
                .padding(all = 4.dp)
                .align(Alignment.End),
            onClick = {
                viewModel.onEvent(EditUserProfileEvent.SubmitDetails)

            }) {
            Icon(imageVector = Icons.Default.DoneOutline, contentDescription = null )
        }

    }
}


@Composable
fun EditableAddressCard(
    address: String? = null,
    city: String? = null,
    country: String? = null,
    onAddressUpdated: (String) -> Unit,
    onCityUpdated: (String) -> Unit,
    onCountryUpdated: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
        ) {
            Text("Address Section", modifier = Modifier.padding(all = 2.dp))
            EditTextSection(
                Modifier,
                @Composable { Icons.Default.AddLocation },
                "Address",
                address ?: "",
                onDone = { newAdress ->
                    onAddressUpdated(newAdress)
                },
                minLines = 1
            )

            EditTextSection(
                Modifier,
                @Composable { Icons.Default.LocationCity },
                "City",
                city ?: "",
                onDone = { newCity ->
                    onCityUpdated(newCity)
                })


            EditTextSection(
                Modifier,
                @Composable { Icons.Default.Flag },
                "Country",
                country ?: "",
                onDone = { newCountry ->
                    onCountryUpdated(newCountry)
                })

        }
    }
}

@Composable
fun EditableEmploymentCard(
    employmentStatus: String? = null,
    company: String? = null,
    yearsOfExperience: Int? = null,
    onEmploymentStatusUpdated: (String) -> Unit,
    onCompanyUpdated: (String) -> Unit,
    onYearsOfExperienceUpdated: (String) -> Unit
) {
    val employmentStatusOptions = listOf(
        "Full-time employment",
        "Part-time employment",
        "Self-employment or entrepreneurship",
        "Internship",
        "Contract or freelance work",
        "Zero-hours contract",
        "Unemployed",
        "Student",
        "Other"
    )
    // TODO: update with current user data
    var selectedStatus by remember { mutableStateOf(employmentStatus ?: "") }
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
        ) {
            Text("Employment Details", modifier = Modifier.padding(all = 2.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                contentAlignment = Alignment.TopEnd
            ) {
                OutlinedTextField(
                    value = selectedStatus,
                    onValueChange = { newValue ->
                        selectedStatus = newValue
                    },
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth(),
//                    leadingIcon = { },
                    trailingIcon = {
                        Icon(
                            imageVector = (if (expanded) (Icons.Default.ExpandLess) else (Icons.Default.ExpandMore)),
                            modifier = Modifier.clickable {
                                expanded = !expanded

                            },
                            contentDescription = null,
                        )

                    },
                    label = {
                        Text("Employment Status")
                    },
                    maxLines = 1
                )
                if (expanded) {
                    DropdownMenu(
                        expanded = true, // Change to true to show the dropdown initially
                        onDismissRequest = {
                            expanded = false
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        employmentStatusOptions.forEach { status ->
                            DropdownMenuItem(
                                text = { Text(text = status) },
                                onClick = {
                                    selectedStatus = status
                                    onEmploymentStatusUpdated(status)
                                    expanded = false
                                })
                        }
                    }
                }
            }
            if (selectedStatus == "Full-time employment" || selectedStatus == "Part-time employment" || selectedStatus == "Self-employment or entrepreneurship" || selectedStatus == "Internship") {
                EditTextSection(
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = @Composable { },
                    fieldLabel = "Company Name",
                    oldFieldValue = company ?: "",
                    onDone = { newCompany ->
                        onCompanyUpdated(newCompany)
                    }
                )
                EditTextSection(
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = @Composable { },
                    fieldLabel = "Duration of Work",
                    oldFieldValue = (yearsOfExperience ?: 0).toString(),
                    onDone = { newYearsOfExperience ->
                        onYearsOfExperienceUpdated(newYearsOfExperience)
                    },
                    minLines =  1 ,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }

        }
    }
}

@Composable
fun EditableGenderSection(gender: String? = null, onGenderUpdateDonePressed: (String) -> Unit) {
    val genderOptions = listOf(
        "Male",
        "Female",
        "Transgender",
        "Gender Neutral",
        "Non-Binary",
        "Prefer Not To Say"
    )
    var selectedGender by remember { mutableStateOf(gender ?: "Prefer Not To Say") }
    var expanded by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        contentAlignment = Alignment.TopEnd
    ) {
        OutlinedTextField(
            value = selectedGender,
            onValueChange = { newValue ->
                selectedGender = newValue
                onGenderUpdateDonePressed(newValue)
            },
            readOnly = true,
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = { },
            trailingIcon = {
                Icon(
                    imageVector = (if (expanded) (Icons.Default.ExpandLess) else (Icons.Default.ExpandMore)),
                    modifier = Modifier.clickable {
                        expanded = !expanded

                    },
                    contentDescription = null,
                )

            },
            label = {
                Text("Gender")
            },
            maxLines = 1
        )
        if (expanded) {
            DropdownMenu(
                expanded = true, // Change to true to show the dropdown initially
                onDismissRequest = {
                    expanded = false
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                genderOptions.forEach { gender_item ->
                    DropdownMenuItem(
                        text = { Text(text = gender_item) },
                        onClick = {
                            selectedGender = gender_item
                            onGenderUpdateDonePressed(gender_item)
                            expanded = false
                        })
                }
            }
        }
    }
}

@Composable
fun EditableBioSection(bio: String? = null, onUpdateBioDonePressed: (String) -> Unit) {
    EditTextSection(
        modifier = Modifier.fillMaxWidth(),
        leadingIcon = @Composable { Icons.Default.Details },
        fieldLabel = "Bio",
        oldFieldValue = bio ?: "",
        onDone = { newUserBio ->
            onUpdateBioDonePressed(newUserBio)
        },
        minLines = 1
    )
}
