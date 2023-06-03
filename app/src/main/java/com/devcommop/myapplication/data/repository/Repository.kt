package com.devcommop.myapplication.data.repository

import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

private const val TAG = "##@@Repository"

class Repository @Inject constructor(
    private val auth: FirebaseAuth
) {
}