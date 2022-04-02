package com.example.surveyapp

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.surveyapp.AppRepresentations.RegisterPage.RegisterViewModel
import com.example.surveyapp.AppRepresentations.RoleIdent
import com.example.surveyapp.AppRepresentations.count_fullyears
import com.example.surveyapp.AppRepresentations.genderDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.time.format.DateTimeFormatter
import java.util.*
import java.time.LocalDateTime


object Gender{

   const val male = "Муж."
    const val female = "Жен."
}


@Composable
fun RegisterScreen(navController: NavController,
                   viewModel: RegisterViewModel = hiltViewModel()
){

    val context = LocalContext.current

    val auth_firestore = FirebaseAuth.getInstance()

    val state = remember { mutableStateOf(true) }
    var login by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by  remember { mutableStateOf("") }
    var gender = remember { mutableStateOf("...") }
    var age by  remember { mutableStateOf(0) }
    var role_id by  remember { mutableStateOf(0) }


    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    var confirmPasswordVisibility by remember { mutableStateOf(false) }

    val isFormValid by derivedStateOf {
        login.isNotBlank() && password.length >= 7
                && email.isNotBlank() && confirmPassword == password

    }

    password = "1234567rus"
    confirmPassword = password

    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier
            .fillMaxSize()
    )
    {
        LoginBackground()


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.70f)
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        )
        {

            Spacer(modifier = Modifier.padding(50.dp))
            Card(
                Modifier
                    .weight(2f)
                    .padding(8.dp),
                shape = RoundedCornerShape(32.dp)
            ){

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp)
                ){
                    Text(
                        text = "Создать аккаунт",
                        fontSize = 40.sp,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,

                            )
                    )
                    Spacer(modifier = Modifier.padding(10.dp))
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
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text(text = "Password") },
                        placeholder = { Text(text = "Password") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        trailingIcon = {
                            IconButton(onClick = {
                                passwordVisibility = !passwordVisibility
                            }) {
                                Icon(
                                    imageVector = if (passwordVisibility) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                    contentDescription = "Password Toggle"
                                )
                            }
                        },
                        visualTransformation = if (passwordVisibility) VisualTransformation.None
                        else PasswordVisualTransformation()
                    )
                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        label = { Text(text = "Confirm Password") },
                        placeholder = { Text(text = "Confirm Password") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        trailingIcon = {
                            IconButton(onClick = {
                                confirmPasswordVisibility = !confirmPasswordVisibility
                            }) {
                                Icon(
                                    imageVector = if (confirmPasswordVisibility) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                    contentDescription = "Password Toggle"
                                )
                            }
                        },
                        visualTransformation = if (confirmPasswordVisibility) VisualTransformation.None
                        else PasswordVisualTransformation()
                    )

                    Spacer(modifier = Modifier.padding(10.dp))
                    //Age
                    age = ShowDatePicker(context, age)

//                    // Gender

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





                    Spacer(modifier = Modifier.padding(20.dp))

                    Button(onClick = {

                        role_id = RoleIdent(login)

                        auth_firestore.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener() { task ->
                                if (task.isSuccessful) {

                                    viewModel.postNewUserData(
                                            email,
                                            login,
                                            gender.value,
                                            age,
                                            role_id)

                                    Toast.makeText(
                                        context,
                                        "Регистрация прошла успешно!",
                                        Toast.LENGTH_SHORT

                                    ).show()

                                    navController.popBackStack()
                                    val prev_screen_name = "sign up"
                                    navController.navigate(
                                        Screen.WelcomeScreen.route + "/$prev_screen_name"
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



                        enabled = isFormValid,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp)

                    ){


                        Text("Зарегистрироваться",
                            fontSize = 25.sp
                        )
                    }

                }

            }




        }


        }
    }





@Composable
private fun LoginBackground() {
    val painter = painterResource(id = R.drawable.loginscreen)

    Image(painter = painter, contentDescription = null,
        contentScale = ContentScale.FillBounds,
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun ShowDatePicker(context: Context, age1:Int): Int{

    val year: Int
    val month: Int
    val day: Int
    var user_age: Int by remember { mutableStateOf(0) }

    val calendar = Calendar.getInstance()
    year = calendar.get(Calendar.YEAR)
    month = calendar.get(Calendar.MONTH)
    day = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time = Date()

    val date = remember { mutableStateOf("") }
    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            date.value = year.toString()
        }, year, month, day
    )
    Spacer(modifier = Modifier.size(16.dp))
    Button(onClick = {
        datePickerDialog.show()
    },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        if(age1>0){
            if(user_age>0){
                Text(text = "Ваш возраст: ${user_age}",fontSize = 20.sp)
            }
            else{
                Text(text = "Ваш возраст: ${age1}",fontSize = 20.sp)
            }

        }
        else{
            if(user_age>0){
                Text(text = "Ваш возраст: ${user_age}",fontSize = 20.sp)
            }
            else{
                Text(text = "Дата рождения",fontSize = 20.sp)
            }

        }

    }
    Spacer(modifier = Modifier.size(20.dp))
    val current = LocalDateTime.now()
    val newFomat = DateTimeFormatter.ofPattern("yyyy")
    val onlyDateWithoutTime = current.format(newFomat)
    user_age = count_fullyears(date, onlyDateWithoutTime.toInt() )
    return user_age
}




