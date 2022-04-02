package com.example.surveyapp.AppRepresentations.UserPage.UserEditPage

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.surveyapp.AppRepresentations.Drawer
import com.example.surveyapp.AppRepresentations.RegisterPage.RegisterViewModel
import com.example.surveyapp.AppRepresentations.TopBar
import com.example.surveyapp.AppRepresentations.genderDialog
import com.example.surveyapp.Gender
import com.example.surveyapp.R
import com.example.surveyapp.Screen
import com.example.surveyapp.ShowDatePicker
import com.example.surveyapp.remote.responses.Users.User
import com.example.surveyapp.util.Resource
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

@Composable
private fun UserBackground() {
    val painter = painterResource(id = R.drawable.testscreen)

    Image(painter = painter, contentDescription = null,
        contentScale = ContentScale.FillBounds,
        modifier = Modifier.fillMaxSize()
    )
}


@Composable
fun UserEditProfile(login1:String,email1:String,gender1:String,age1:String,
                    navController: NavHostController,
                    viewModel: UserEditViewModel = hiltViewModel()) {

    var login by remember { mutableStateOf(login1) }
    var gender = remember { mutableStateOf(gender1) }
    var age by  remember { mutableStateOf(0) }
    var email by  remember { mutableStateOf(email1) }

    val state = remember { mutableStateOf(true) }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val isFormValid by derivedStateOf {
        email.isNotBlank() && login.isNotBlank()
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
            Spacer(modifier = Modifier.padding(20.dp))
            Card(
                Modifier
                    .weight(2f)
                    .padding(20.dp),
                shape = RoundedCornerShape(32.dp)
            ) {
                Column(
//                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp)
                ) {

                    Text(text="Введите логин при регистрации")
                    Spacer(modifier = Modifier.padding(3.dp))
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = login,
                        onValueChange = { login = it },
                        label = { Text(text = "Login") },
                        singleLine = true,
                        trailingIcon = {
                            if (login.isNotBlank())
                                IconButton(onClick = { login = "" }) {
                                    Icon(imageVector = Icons.Filled.Clear, contentDescription = "")
                                }
                        }
                    )
                    Spacer(modifier = Modifier.padding(5.dp))
                    Text(text="Введите email при регистрации")
                    Spacer(modifier = Modifier.padding(3.dp))
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = email,
                        onValueChange = { email = it },
                        label = { Text(text = "Email") },
                        singleLine = true,
                        trailingIcon = {
                            if (email.isNotBlank())
                                IconButton(onClick = { email = "" }) {
                                    Icon(imageVector = Icons.Filled.Clear, contentDescription = "")
                                }
                        }
                    )
                    Spacer(modifier = Modifier.padding(10.dp))
                    //Age
                    age = ShowDatePicker(context, age1.toInt())


                    val DialogState = remember { mutableStateOf(false) }
                    if(DialogState.value){
                        genderDialog(state,DialogState,gender)
                    }
                    Button(onClick = {
                        DialogState.value = true
                    },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp))
                    {
                        if(gender.value=="..."){
                            Text("Указать пол",
                                fontSize = 20.sp
                            )
                        }
                        else{
                            Text("Ваш пол: ${gender.value}",
                                fontSize = 20.sp
                            )
                        }

                    }

                    Spacer(modifier = Modifier.padding(40.dp))

                    Button(onClick = {

                        val user = Firebase.auth.currentUser
                        user!!.updateEmail(email)

                                viewModel.UpdateUserData(
                                    email,
                                    email1,
                                    login,
                                    gender.value,
                                    age
                                )

                        Firebase.auth.signOut()
                        val cur_screen = "userEditScreen"
                        navController.navigate(
                            Screen.WelcomeScreen.route + "/$cur_screen"
                        )


                    },

                        enabled = isFormValid,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp)

                    ){


                        Text("Применить",
                            fontSize = 20.sp
                        )
                    }


                }


                }
            Spacer(modifier = Modifier.padding(50.dp))
            }
    }

}