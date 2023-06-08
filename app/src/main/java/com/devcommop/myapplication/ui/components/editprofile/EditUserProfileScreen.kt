package com.devcommop.myapplication.ui.components.editprofile

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddLocation
import androidx.compose.material.icons.filled.Details
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devcommop.myapplication.data.local.RuntimeQueries
import com.devcommop.myapplication.ui.components.common.DividerWithSpace
import com.devcommop.myapplication.ui.components.common.ProfileHeaderSection
import com.devcommop.myapplication.ui.components.common.text.EditTextSection

@Preview(showBackground = true)
@Composable
fun EditUserProfileScreen() {
    val TAG = "##@@EditUserProfileScreen"
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        var currentUser = RuntimeQueries.currentUser
        //contains profile pic ,cover and username and bio
        ProfileHeaderSection(isEditButtonVisible = true)
        if (currentUser != null) {
            EditableNameSection(
                currentUser.userName
            )
            EditableBioSection(currentUser.bio)
            DividerWithSpace()

            EditableGenderSection(currentUser.gender)
            DateOfBirthSection(
                currentUser.dob ?: "",
                onDateSelected = { date ->
                    // TODO: update user's date of birth
                    Log.d(TAG, "Selected date: $date")
                }
            )
            DividerWithSpace()

            currentUser.apply {
                EditableAddressCard(address, city, country)
                DividerWithSpace()

                EditableEmploymentCard(employmentStatus, company, yearsOfExperience)
                DividerWithSpace()
            }

        } else {
            Log.d(TAG, "Edit ProfileScreen :Current user is null")
        }


    }
}


@Preview(showBackground = true)
@Composable
fun EditableNameSection(userName: String = "") {
    EditTextSection(
        modifier = Modifier,
        leadingIcon = { Icons.Default.Details },
        fieldLabel = "Name",
        oldFieldValue = userName,
        onDone = {
            //TODO: update user's name
            // once a name updated , it will be same for least next 15 days
        }
    )
}


@Preview(showBackground = true)
@Composable
fun EditableAddressCard(address: String? = null, city: String? = null, country: String? = null) {
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
                address ?: "", //TODO: replace this with User's address
                onDone = {
                    //update address into db
                },
                minLines = 1
            )

            EditTextSection(
                Modifier,
                @Composable { Icons.Default.LocationCity },
                "City",
                city ?: "",
                onDone = {
                    //TODO: update city in db
                })


            EditTextSection(
                Modifier,
                @Composable { Icons.Default.Flag },
                "Country",
                country ?: "",
                onDone = {
                    //TODO: update country in db
                })

        }
    }
}

@Preview(showBackground = true)
@Composable
fun EditableEmploymentCard(
    employmentStatus: String? = null,
    company: String? = null,
    yearsOfExperience: Int? = null
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
                        // TODO: update to database
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
                                    expanded = false
                                    // TODO: update status in db
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
                    onDone = {
                        //TODO: update company name
                    }
                )
                EditTextSection(
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = @Composable { },
                    fieldLabel = "Duration of Work",
                    oldFieldValue = (yearsOfExperience ?: 0).toString(),
                    onDone = {
                        //TODO: update duration
                    }
                )
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun EditableGenderSection(gender: String? = null ) {
    val genderOptions = listOf(
        "Male",
        "Female",
        "Transgender",
        "Gender Neutral",
        "Non-Binary",
        "Prefer Not To Say",
//        "Agender",
//        "Pangender",
//        "Genderqueer",
//        "Two-Spirit",
//        "Third Gender"
    )
    //TODO: selected Gender should be replaced with Current User's Gender present in db
    var selectedGender by remember { mutableStateOf(gender?:"Prefer Not To Say") }
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
                // TODO: update to database
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
                genderOptions.forEach { gender ->
                    DropdownMenuItem(
                        text = { Text(text = gender) },
                        onClick = {
                            selectedGender = gender
                            expanded = false
                            // TODO: update gender in db
                        })
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EditableBioSection(bio: String? = null ) {
    EditTextSection(
        modifier = Modifier.fillMaxWidth(),
        leadingIcon = @Composable { Icons.Default.Details },
        fieldLabel = "Bio",
        oldFieldValue = bio ?: "",
        onDone = {
            // TODO: update userBio
        },
        minLines = 1
    )
}
