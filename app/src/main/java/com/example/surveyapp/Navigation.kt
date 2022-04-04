package com.example.surveyapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.surveyapp.AppRepresentations.AdvancedStat.AdvancedStatScreen
import com.example.surveyapp.AppRepresentations.AdvancedStat.AgeStat.AgeStatScreen
import com.example.surveyapp.AppRepresentations.AdvancedStat.GenderStat.GenderStatScreen
import com.example.surveyapp.AppRepresentations.AdvancedStat.UsersStat.UsersStatScreen

import com.example.surveyapp.AppRepresentations.ForgotPassword.ResetScreen
import com.example.surveyapp.AppRepresentations.MainPage.MainScreen1
import com.example.surveyapp.AppRepresentations.PreAppLunchPage.PreLunchScreen
import com.example.surveyapp.AppRepresentations.QuestsPage.QuestsScreen
import com.example.surveyapp.AppRepresentations.QuestsPage.QuestsViewModel
import com.example.surveyapp.AppRepresentations.QuestsPage.StartTest
import com.example.surveyapp.AppRepresentations.StatPage.Statistic
import com.example.surveyapp.AppRepresentations.StatPage.UserStatistic
import com.example.surveyapp.AppRepresentations.TestResults.ResultScreen

import com.example.surveyapp.AppRepresentations.UserPage.UserDetails
import com.example.surveyapp.AppRepresentations.UserPage.UserEditPage.UserEditProfile
import com.example.surveyapp.AppRepresentations.user_role
//import com.example.surveyapp.AppRepresentations.welcomePage.CheckUserState
import com.example.surveyapp.AppRepresentations.welcomePage.WelcomeScreen
import com.example.surveyapp.AppRepresentations.welcomePage.WelcomeViewModel
import com.example.surveyapp.remote.responses.attempt.attemptsItem
import com.example.surveyapp.remote.responses.userResults.UserResults
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


@Composable
fun Navigation(viewModel: QuestsViewModel = hiltViewModel()){


    val navController = rememberNavController()

    // load the questions
    val QuestsList by remember { viewModel.QuestsList }

    // load the questions balls
    val QuestsBallsList by remember { viewModel.QuestsBallsList }

    NavHost(navController = navController,
        startDestination = Screen.PreLunchScreen.route){

//        Screen.PreLunchScreen.route + "/{prev_screen_name}"
        composable(route = Screen.PreLunchScreen.route){
            PreLunchScreen(navController = navController)
        }


        composable(route = Screen.WelcomeScreen.route + "/{prev_screen_name}"){ navBackStack ->
            val prev_screen_name = navBackStack.arguments?.getString("prev_screen_name")

            val currentuser = Firebase.auth.currentUser
            if(prev_screen_name == "sign up"){
                WelcomeScreen(navController = navController)
            }
            else{
                if(currentuser != null) {
                    if(currentuser.isAnonymous){
                        WelcomeScreen(navController = navController)
                    }
                    else{
                        MainScreen1(navController = navController)
                    }

                }
                else{
                    WelcomeScreen(navController = navController)
                }
            }


        }


        composable(route = Screen.UserEditScreen.route + "/{login}/{email}/{gender}/{age}"){
                navBackStack ->
            val login = navBackStack.arguments?.getString("login")
            val email = navBackStack.arguments?.getString("email")
            val gender = navBackStack.arguments?.getString("gender")
            val age = navBackStack.arguments?.getString("age")
            if (login != null && email!=null && gender!=null && age!=null) {
                UserEditProfile(login,email,gender,age,navController = navController)
            }
        }

        composable(route = Screen.UserDetailsScreen.route){
            UserDetails(navController = navController,auth = FirebaseAuth.getInstance())
        }

        composable(route = Screen.StatisticScreen.route){
            Statistic(navController = navController)
        }

        composable(route = Screen.AdvancedStatScreen.route){
            AdvancedStatScreen(navController = navController)
        }

        composable(route = Screen.UsersStatScreen.route){
            UsersStatScreen(navController = navController)
        }

        composable(route = Screen.GenderStatScreen.route){
            GenderStatScreen(navController = navController)
        }

        composable(route = Screen.AgeStatScreen.route){
            AgeStatScreen(navController = navController)
        }


        composable(route = Screen.UserStatisticScreen.route + "/{id}/{time}"){
                navBackStack ->
            val attemptId = navBackStack.arguments?.getString("id")
            val passTime = navBackStack.arguments?.getString("time")

            if (passTime != null ) {
                if (attemptId != null) {
                    UserStatistic(attemptId,passTime,navController = navController)
                }
            }

        }


        composable(route = Screen.RegisterScreen.route){
            RegisterScreen(navController = navController)
        }

        composable(route = Screen.MainScreen.route + "/{prev_screen_name}"){ navBackStack ->
            val prev_screen_name = navBackStack.arguments?.getString("prev_screen_name")
//
            MainScreen1(navController = navController)


        }

//        composable(route = Screen.MainScreen.route){
////
//            MainScreen1(navController = navController)
//
//        }
        composable(route = Screen.StartTestScreen.route){
            StartTest(navController = navController)
        }
        composable(route = Screen.QuestsScreen.route){
            QuestsScreen(navController = navController,QuestsList,QuestsBallsList)
        }
        composable(route = Screen.ResetPasswordScreen.route){
            ResetScreen(navController = navController)
        }

        composable(route = Screen.ResultTestScreen.route + "/{involvement}/{control}/{risk_taking}/{min}/{seconds}"){
                navBackStack ->
            val involvement = navBackStack.arguments?.getString("involvement")
            val control = navBackStack.arguments?.getString("control")
            val risk_taking = navBackStack.arguments?.getString("risk_taking")
            val min = navBackStack.arguments?.getString("min")
            val seconds = navBackStack.arguments?.getString("seconds")

            if (involvement != null ) {
                if (control != null) {
                    if (risk_taking != null) {
                        if (min != null) {
                            if (seconds != null) {
                                ResultScreen(involvement,control,risk_taking,min,seconds,navController = navController)
                            }
                        }
                    }

                }
            }

        }



    }
}



