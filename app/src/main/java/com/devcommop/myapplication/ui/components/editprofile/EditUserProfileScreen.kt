package com.devcommop.myapplication.ui.components.editprofile

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddLocation
import androidx.compose.material.icons.filled.Details
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devcommop.myapplication.R
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

        //contains profile pic ,cover and username and bio
        ProfileHeaderSection()
        NameSection()
        BioSection()
        DividerWithSpace()

        GenderSection()
        DateOfBirthSection(
            "01/01/2000",
            onDateSelected = { date ->
                // TODO: update user's date of birth
                Log.d(TAG , "Selected date: $date")
            }
        )
        DividerWithSpace()

        AddressCard()
        DividerWithSpace()

        EmploymentStatusCard()
        DividerWithSpace()

    }
}

@Composable
fun DividerWithSpace(dividerThickness: Int = 1, spaceGap: Int = 2) {
    Spacer(modifier = Modifier.height(spaceGap.dp))
    Divider(modifier = Modifier.height(dividerThickness.dp))
    Spacer(modifier = Modifier.height(spaceGap.dp))
}


@Composable
fun ProfileHeaderSection() {
    // TODO: when user change cover, show dialog to post it as well
    Box(modifier = Modifier) {
        Box(
            contentAlignment = Alignment.BottomEnd,
        ) {
            Image(
                painter = painterResource(R.drawable.dummy_cover_image),
                contentDescription = "profile_picture",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
                    .padding(horizontal = 2.dp)
                    .size(160.dp)
                    .safeContentPadding()
                    .clip(shape = RectangleShape),
                contentScale = ContentScale.Crop
            )
            IconButton(onClick = { /*TODO: Handle profile picture edit */ }) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "Edit Profile Picture",
                    modifier = Modifier.size(28.dp)
                )
            }
            //val width = LocalConfiguration.current.screenWidthDp - 180 - 10   -4
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.Center
            ) {

            }

        }


        Row(
            modifier = Modifier.padding(top = 81.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Box(
                contentAlignment = Alignment.BottomEnd,
                modifier = Modifier.padding(0.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.dummy_profile_picture),
                    contentDescription = "profile_picture",
                    modifier = Modifier
                        .padding(horizontal = 2.dp)
                        .size(180.dp)
                        .safeContentPadding()
                        .clip(shape = CircleShape),
                    contentScale = ContentScale.Crop
                )
                IconButton(onClick = { /*TODO: Handle profile picture edit */ }) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "Edit Profile Picture",
                        modifier = Modifier.size(28.dp)
                    )
                }
//
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
//    Text(
//        text = "Anime Girl",
//        style = MaterialTheme.typography.headlineMedium
//    )
}

@Preview(showBackground = true)
@Composable
fun NameSection() {
    EditTextSection(
        modifier = Modifier,
        leadingIcon = { Icons.Default.Details },
        fieldLabel = "Name",
        oldFieldValue = "Anime Girl",
        onDone = {
            //TODO: update user's name
            // once a name updated , it will be same for least next 15 days
        }
    )
}


@Preview(showBackground = true)
@Composable
fun AddressCard() {
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
                "123 Null Street", //TODO: replace this with User's address
                onDone = {
                    //update address into db
                },
                minLines = 1
            )

            EditTextSection(
                Modifier,
                @Composable { Icons.Default.LocationCity },
                "City",
                "New York",
                onDone = {
                    //TODO: update city in db
                })


            EditTextSection(
                Modifier,
                @Composable { Icons.Default.Flag },
                "Country",
                "United States",
                onDone = {
                    //TODO: update country in db
                })

        }
    }
}

@Preview(showBackground = true)
@Composable
fun EmploymentStatusCard() {
    val employmentStatusOptions = listOf(
        "Full-time employment", "Part-time employment", "Self-employment or entrepreneurship" , "Internship","Contract or freelance work", "Zero-hours contract", "Unemployed",
        "Student", "Other"
    )
    // TODO: update with current user data
    var selectedStatus by remember { mutableStateOf("Student") }
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
            if(selectedStatus ==   "Full-time employment" || selectedStatus ==  "Part-time employment" || selectedStatus ==  "Self-employment or entrepreneurship" || selectedStatus ==  "Internship"){
                EditTextSection(
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = @Composable { },
                    fieldLabel = "Company Name",
                    oldFieldValue = "ABC Corp",
                    onDone = {
                        //TODO: update company name
                    }
                )
                EditTextSection(
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = @Composable { },
                    fieldLabel = "Duration of Work",
                    oldFieldValue = "2 years",
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
fun GenderSection() {
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
    var selectedGender by remember { mutableStateOf("Prefer Not To Say") }
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
fun BioSection() {
    EditTextSection(
        modifier = Modifier.fillMaxWidth(),
        leadingIcon = @Composable { Icons.Default.Details },
        fieldLabel = "Bio",
        oldFieldValue = "I am a software engineer at ABC Corp",
        onDone = {
            // TODO: update userBio
        },
        minLines = 3
    )
}
