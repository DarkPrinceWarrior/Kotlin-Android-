package com.example.surveyapp.AppRepresentations.ForgotPassword

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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.surveyapp.AppRepresentations.RegisterPage.RegisterViewModel
import com.example.surveyapp.Screen
import com.google.firebase.auth.FirebaseAuth

@Composable
private fun ResetBackground() {
    val painter = painterResource(id = com.example.surveyapp.R.drawable.loginscreen)

    Image(painter = painter, contentDescription = null,
        contentScale = ContentScale.FillBounds,
        modifier = Modifier.fillMaxSize()
    )
}



@Composable
fun ResetScreen(navController: NavController,
                   viewModel: RegisterViewModel = hiltViewModel()
){

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val auth = FirebaseAuth.getInstance()



    var email by remember { mutableStateOf("") }
    var password by  remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    var confirmPasswordVisibility by remember { mutableStateOf(false) }

    val isFormValid by derivedStateOf {
          email.isNotBlank()

    }

    email="safaev.rus@gmail.com"
//    password="1205e8hurhg7h"
//    confirmPassword = password

    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier
            .fillMaxSize()
    )
    {
        ResetBackground()


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.70f)
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        )
        {

            Spacer(modifier = Modifier.padding(70.dp))
            Card(
                Modifier
                    .weight(2f)
                    .padding(8.dp),
                shape = RoundedCornerShape(32.dp)
            ){

                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp)
                ){
                    Text(
                        text = "Сбросить пароль",
                        fontSize = 40.sp,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,

                            )
                    )
                    Spacer(modifier = Modifier.padding(20.dp))
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = email,
                        onValueChange = { email = it },
                        label = { Text(text = "Email Address") },
                        singleLine = true,
                        trailingIcon = {
                            if (email.isNotBlank())
                                IconButton(onClick = { email = "" }) {
                                    Icon(imageVector = Icons.Filled.Clear, contentDescription = "")
                                }
                        }
                    )
//


                    Spacer(modifier = Modifier.padding(10.dp))

                    Button(onClick = {

                        auth.sendPasswordResetEmail(email)
                            .addOnCompleteListener() { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(
                                        context,
                                        "Проверьте письмо, отправленное на вашу почту!",
                                        Toast.LENGTH_SHORT

                                    ).show()

                                }
                                else{
                                    Toast.makeText(
                                        context,
                                        task.exception!!.message.toString(),
                                        Toast.LENGTH_SHORT

                                    ).show()

                                }

                            }

                        val current_screen = "reset_pass_screen"
                        navController.navigate(
                            Screen.WelcomeScreen.route + "/$current_screen"
                        )


                    },



                        enabled = isFormValid,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp)

                    ){


                        Text("Сбросить пароль",
                            fontSize = 25.sp
                        )
                    }

                }

            }
            Spacer(modifier = Modifier.padding(150.dp))



        }


    }
}
