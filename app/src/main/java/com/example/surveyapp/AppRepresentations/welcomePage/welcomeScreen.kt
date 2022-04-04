package com.example.surveyapp.AppRepresentations.welcomePage

import android.app.ProgressDialog
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.surveyapp.AppRepresentations.QuestsPage.QuestsViewModel
import com.example.surveyapp.AppRepresentations.user_role
import com.example.surveyapp.R
import com.example.surveyapp.Screen
import com.example.surveyapp.remote.responses.Users.User
import com.example.surveyapp.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch


@Composable
fun WelcomeScreen(navController: NavController,
                  viewModel: WelcomeViewModel= hiltViewModel()){

    BackHandler(enabled = true, onBack = {


    })

    val usersList = viewModel.UsersList
    val rolesList = viewModel.RolesList
    val context = LocalContext.current

    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var isPasswordVisible by remember {
        mutableStateOf(false)
    }
    val isFormValid by derivedStateOf {
        email.isNotBlank() && password.length >= 7
    }
//    email = "safaev.rus@gmail.com"
//    password = "ruslan12"
//    email = "safaev.rus@gmail.com"
    email = "ruslan.ussr@gmail.com"
    password = "1234567rus"

    // admin
//    email = "julia@gmail.com"

    // progress circle while log in
    val progressDialog = ProgressDialog(context)
    progressDialog.setTitle("Please wait")
    progressDialog.setMessage("Logging in...")
    progressDialog.setCanceledOnTouchOutside(false)


    Scaffold(backgroundColor = MaterialTheme.colors.primary)
    {
        WelcomeBackground()

        Column(verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)

        )
        {
            Image(
                painter = painterResource(id = R.drawable.ic_madi),
                contentDescription = "App Logo",
                modifier = Modifier
                    .weight(1f)
                    .size(270.dp),
                colorFilter = ColorFilter.tint(Color.Red)
            )
            Card(
                Modifier
                    .weight(2f)
                    .padding(16.dp),
                shape = RoundedCornerShape(32.dp)
            ){
                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(28.dp)
                ){
                    Text(text = "Добро пожаловать!",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 40.sp)
                    Spacer(modifier = Modifier.height(20.dp))
                    Column(Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center)
                    {
//                        Spacer(modifier = Modifier.weight(1f))

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
                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = password,
                            onValueChange = { password = it },
                            label = { Text(text = "Password") },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
                            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            trailingIcon = {
                                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                                    Icon(
                                        imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                        contentDescription = "Password Toggle"
                                    )
                                }
                            }
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(onClick = {
                            progressDialog.show()
                            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                                .addOnCompleteListener() { task ->
                                    if (task.isSuccessful) {
                                        val user = usersList.value.filter {
                                            it.email == email
                                        }
                                        val role = rolesList.value.filter {
                                            it.id == user[0].role_id
                                        }
                                        user_role.value = role[0].name
                                        progressDialog.dismiss()
                                        Toast.makeText(
                                            context,
                                            "Вы успешно вошли в качестве ${user_role.value}!",
                                            Toast.LENGTH_SHORT

                                        ).show()

                                        navController.popBackStack()
                                        val current_screen = "welcome_screen"
                                        navController.navigate(
                                            Screen.MainScreen.route + "/$current_screen"
                                        )

                                    }
                                    else{

                                        viewModel.post_deleteUser(email)

                                        progressDialog.dismiss()
                                        Toast.makeText(
                                            context,
                                            task.exception!!.message.toString(),
                                            Toast.LENGTH_SHORT

                                        ).show()


                                    }

                                }

                                         },

                            enabled = isFormValid,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp)

                        ){
                            Text("Войти",
                                fontSize = 25.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(15.dp))

                        TextButton(onClick = {


                            FirebaseAuth.getInstance().signInAnonymously()
                                .addOnCompleteListener() { task ->

                                    if (task.isSuccessful) {

                                        user_role.value = "anon"

                                        Toast.makeText(
                                            context,
                                            "Вы начнете тест как аноним!",
                                            Toast.LENGTH_SHORT

                                        ).show()

//                                        navController.popBackStack()
                                        navController.navigate(
                                            Screen.StartTestScreen.route
                                        )
                                    }
                                    else{
                                        Toast.makeText(
                                            context,
                                            task.exception!!.message.toString(),
                                            Toast.LENGTH_SHORT

                                        ).show()
                                    }

                                }




                                         },

                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp)

                        ){
                            Text(
                                "начать тест без входа",

                                )
                        }

                        Spacer(modifier = Modifier.weight(1f))



                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            TextButton(onClick = {
                                navController.navigate(
                                Screen.RegisterScreen.route
                            )}) {
                                Text(text = "Зарегистрироваться")
                            }
                            TextButton(onClick = {  navController.navigate(
                                Screen.ResetPasswordScreen.route
                            )}) {
                                Text(text = "Забыли пароль?", color = Color.Gray)
                            }
                        }

                    }
                }
            }


        }
    }

}



@Composable
private fun WelcomeBackground() {
    val painter = painterResource(id = com.example.surveyapp.R.drawable.loginscreen)

    Image(painter = painter, contentDescription = null,
        contentScale = ContentScale.FillBounds,
        modifier = Modifier.fillMaxSize()
    )
}
