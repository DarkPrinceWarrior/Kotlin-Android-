package com.example.surveyapp.AppRepresentations.UserPage

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.surveyapp.AppRepresentations.Drawer
import com.example.surveyapp.AppRepresentations.MainPage.MainScreen1
import com.example.surveyapp.AppRepresentations.TopAppBarCompose
import com.example.surveyapp.AppRepresentations.TopBar
import com.example.surveyapp.AppRepresentations.welcomePage.WelcomeScreen
import com.example.surveyapp.Screen
import com.example.surveyapp.remote.responses.Users.User
import com.example.surveyapp.util.Resource
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.coroutineScope



@Composable
fun UserDetails(
    navController: NavController,    auth: FirebaseAuth,
    viewModel: UserViewModel = hiltViewModel(),

) {

    val scope = rememberCoroutineScope()

    val email = auth.currentUser?.email.toString()

    val UserEntry = produceState<Resource<User>>(initialValue = Resource.Loading()) {
        value = viewModel.getUserInfo(email)
    }.value

    var login by remember { mutableStateOf("") }
    var gender = remember { mutableStateOf("...") }
    var age by  remember { mutableStateOf(1) }

    // initialize user object
    UserEntry.data?.let { it1 ->

        login = it1.login
        gender.value =  it1.gender
        age = it1.age

    }

    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopBar(scope = scope, scaffoldState = scaffoldState)
    },
        drawerContent = {
            Drawer(navController,scope = scope, scaffoldState = scaffoldState)
        }
    )
    {
        UserBackground()

        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
        )
        {
            Spacer(modifier = Modifier.height(70.dp))
            Card(
                Modifier
                    .weight(2f)
                    .padding(20.dp),
                shape = RoundedCornerShape(32.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp)
                ) {

                    if(UserEntry is Resource.Success) {

                        UserEntry.data?.let { it1 ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp)
                            ){
                                RoundImage(
                                    it1.gender,
                                    modifier = Modifier
                                        .size(250.dp)
                                        .weight(3f)
                                )
                            }
                            Spacer(modifier = Modifier.height(25.dp))
                            Text(text = it1.login,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                fontSize = 25.sp)

                        }

                    }
                    Spacer(modifier = Modifier.height(30.dp))
                    Button(onClick = {


                        auth.signOut()

                        navController.popBackStack()
                        val prev_screen_name = "log out"
                        navController.navigate(
                            Screen.WelcomeScreen.route + "/$prev_screen_name"
                        )


                    },

                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp)

                    ){
                        Text("Выйти",
                            fontSize = 22.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(onClick = {

                        navController.navigate(
                            Screen.UserEditScreen.route + "/${login}/${email}/${gender.value}/${age}"
                        )



                    },

                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp)

                    ){
                        Text("Редактировать профиль",
                            fontSize = 22.sp
                        )
                    }


                }
            }
            Spacer(modifier = Modifier.height(85.dp))
        }

    }

}


@Composable
fun RoundImage(
    modifier1: String,
    modifier: Modifier = Modifier
) {
//
    var gender_image = painterResource(id = com.example.surveyapp.R.drawable.man)

    if (modifier1=="Женщина"){
        gender_image = painterResource(id = com.example.surveyapp.R.drawable.woman)
    }

    Image(
        painter =  gender_image,
        contentDescription = null,
        modifier = modifier
            .aspectRatio(1f, matchHeightConstraintsFirst = true)
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = CircleShape
            )
            .padding(3.dp)
            .clip(CircleShape)
    )
}

@Composable
private fun UserBackground() {
    val painter = painterResource(id = com.example.surveyapp.R.drawable.testscreen)

    Image(painter = painter, contentDescription = null,
        contentScale = ContentScale.FillBounds,
        modifier = Modifier.fillMaxSize()
    )
}


