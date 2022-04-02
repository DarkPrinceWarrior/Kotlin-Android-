package com.example.surveyapp.AppRepresentations.CheckUser

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.surveyapp.AppRepresentations.MainPage.MainScreen1
import com.example.surveyapp.AppRepresentations.StatPage.Statistic
import com.example.surveyapp.AppRepresentations.UserPage.UserDetails
import com.example.surveyapp.AppRepresentations.UserPage.UserViewModel
import com.example.surveyapp.AppRepresentations.welcomePage.WelcomeScreen
import com.google.firebase.auth.FirebaseAuth


fun checkBefore_Stats_Profile(): Boolean {

    val auth = FirebaseAuth.getInstance()

    var isApproved = false

    val currentuser = auth.currentUser

    if (currentuser != null) {

        if(!currentuser.isAnonymous){

            isApproved = true


        }

    }
    return isApproved
}

