package com.devcommop.myapplication.data.local

import com.devcommop.myapplication.data.model.User

class RuntimeQueries {
    companion object {
        var user: User? = null

        val fakeUser = User(
            uid = "I_am_fake_but_my_heart_is_real",
            userName = "jesse_pinkman",
            fullName = "Jesse Bruce Pinkman",
            gender = "Male",
            relationshipStatus = "dating",
            address = "Albuquerque, New Mexico, USA",
            city = "Albuquerque",
            country = "USA",
            interestedIn = "Women",
            dob = "September 24, 1984",
            bio = "[M] manufacturer & distributor (former); Student at J. P. Wynne High School (former)",
            profilePictureUrl = "https://firebasestorage.googleapis.com/v0/b/socialmediaapp-bca56.appspot.com/o/Profile_Pics%2Fjesse_pinkman_profile_pic.webp?alt=media&token=d5974103-97fb-4003-9de9-455c02962f3d",
            company = "Gustavo Fring",
            employmentStatus = "Employed",
            createdTimestamp = System.currentTimeMillis().toString(),
        )
    }
}