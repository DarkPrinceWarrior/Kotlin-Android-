package com.example.surveyapp.AppRepresentations.AdvancedStat

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.surveyapp.AppRepresentations.CheckUser.checkBefore_Stats_Profile
import com.example.surveyapp.AppRepresentations.Drawer
import com.example.surveyapp.AppRepresentations.MainPage.MainViewModel
import com.example.surveyapp.AppRepresentations.TopAppBarCompose
import com.example.surveyapp.AppRepresentations.TopBar
import com.example.surveyapp.R
import com.example.surveyapp.Screen
import com.example.surveyapp.remote.responses.attempt.attemptsItem
import com.example.surveyapp.remote.responses.userResults.UserResults
import com.google.firebase.auth.FirebaseAuth


@Composable
fun AdvancedStatScreenBackground() {
    val painter = painterResource(id = R.drawable.testscreen)

    Image(painter = painter, contentDescription = null,
        contentScale = ContentScale.FillBounds,
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun AdvancedStatScreen(navController: NavController){


    // Доработать в дальнейшем
    BackHandler(enabled = true, onBack = {

//        navController.popBackStack()

    })

    // Для drawer panel
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
        AdvancedStatScreenBackground()

        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp)

        )
        {

            Button(onClick = {

                 navController.navigate(
                     Screen.UsersStatScreen.route
                 )


            },

                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth(),
            ){
                Text("Статистика пользователей",
                    fontSize = 25.sp
                )
            }
            Spacer(modifier = Modifier.height(15.dp))


            Button(onClick = {

                navController.navigate(
                    Screen.AgeStatScreen.route
                )


            },shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth(),

                ){
                Text("Статистика по возрасту",
                    fontSize = 25.sp
                )
            }
            Spacer(modifier = Modifier.height(15.dp))

            Button(onClick = {

                navController.navigate(
                    Screen.GenderStatScreen.route
                )

            },shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth(),

                ){
                Text("Статистика по полу",
                    fontSize = 25.sp
                )
            }
            Spacer(modifier = Modifier.height(15.dp))



        }

    }

}
