package com.example.surveyapp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.surveyapp.ui.theme.SurveyAppTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent{
            SurveyAppTheme {
                Navigation()
            }
        }



    }

    override fun onDestroy() {
        super.onDestroy()
//        val auth = FirebaseAuth.getInstance()
//        Firebase.auth.signOut()
    }



}

