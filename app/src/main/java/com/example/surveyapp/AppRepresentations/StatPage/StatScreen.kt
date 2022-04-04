package com.example.surveyapp.AppRepresentations.StatPage

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.surveyapp.AppRepresentations.Drawer
import com.example.surveyapp.AppRepresentations.MainPage.MainScreen1
import com.example.surveyapp.AppRepresentations.TopAppBarCompose
import com.example.surveyapp.AppRepresentations.TopBar
import com.example.surveyapp.AppRepresentations.UserPage.UserDetails
import com.example.surveyapp.AppRepresentations.UserPage.UserViewModel
import com.example.surveyapp.AppRepresentations.welcomePage.WelcomeScreen
import com.example.surveyapp.Screen
import com.example.surveyapp.remote.responses.Users.User
import com.example.surveyapp.remote.responses.attempt.attemptsItem
import com.example.surveyapp.util.Resource
import com.google.firebase.auth.FirebaseAuth




@Composable
private fun StatisticBackground() {
    val painter = painterResource(id = com.example.surveyapp.R.drawable.testscreen)

    Image(painter = painter, contentDescription = null,
        contentScale = ContentScale.FillBounds,
        modifier = Modifier.fillMaxSize()
    )
}


@Composable
fun Statistic(navController: NavController, viewModel: StatViewModel = hiltViewModel()) {

    val auth = FirebaseAuth.getInstance()
    val email = auth.currentUser?.email.toString()
//    println(email)
    val UserEntry = produceState<Resource<User>>(initialValue = Resource.Loading()) {
        value = viewModel.getUserInfo(email)
    }.value


    // all attempts of all users
    val UserAttemptsEntry by remember { viewModel.userAttempts }

    // put all user attempts here

    val userAttempts = mutableListOf<attemptsItem>()
    userAttempts.clear()

    UserEntry.data?.let { it1 ->

        for(attempt in UserAttemptsEntry){

            if(attempt.userId == it1.id){
                userAttempts.add(attempt)
            }
        }


    }
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val scope = rememberCoroutineScope()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopBar(scope = scope, scaffoldState = scaffoldState)
        },
        drawerContent = {
            Drawer(navController,scope = scope, scaffoldState = scaffoldState)
        }
    ){
        StatisticBackground()

        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp)

        )
        {

            ButtonPanel(navController,userAttempts)

        }

    }

}


@Composable
fun ButtonPanel(navController: NavController, userAttempts: MutableList<attemptsItem>) {

    // Доработать в дальнейшем
    BackHandler(enabled = true, onBack = {
        val prev_screen_name = "user_stat_screen"
        navController.navigate(
            Screen.MainScreen.route + "/$prev_screen_name"
        )

    })


    for(attempt in userAttempts){

        Button(onClick = {

            navController.navigate(
                Screen.UserStatisticScreen.route + "/${attempt.id}/${attempt.passing_time}"
            )

        },

            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth(),
        ){
            Text("Попытка №${attempt.attempt_number}",
                fontSize = 25.sp
            )
        }
        Spacer(modifier = Modifier.height(15.dp))
    }



}







