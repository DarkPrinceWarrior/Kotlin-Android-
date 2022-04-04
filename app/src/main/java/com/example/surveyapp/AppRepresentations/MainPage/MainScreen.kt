package com.example.surveyapp.AppRepresentations.MainPage

import android.widget.ImageView
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.surveyapp.AppRepresentations.QuestsPage.StartTest
import com.example.surveyapp.Screen
import com.example.surveyapp.remote.responses.Users.User
import com.example.surveyapp.ui.theme.SurveyAppTheme
import com.example.surveyapp.util.Resource
import com.example.surveyapp.AppRepresentations.TopAppBarCompose
import com.example.surveyapp.AppRepresentations.user_role
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

@Composable
private fun MainBackground() {
    val painter = painterResource(id = com.example.surveyapp.R.drawable.testscreen)

    Image(painter = painter, contentDescription = null,
        contentScale = ContentScale.FillBounds,
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun MainScreen1(navController: NavController,

                viewModel: MainViewModel = hiltViewModel()

){

    val auth = FirebaseAuth.getInstance()


    // Доработать в дальнейшем
    BackHandler(enabled = true, onBack = {


//        navController.popBackStack()



    })


        Scaffold(
            topBar = {
                TopAppBarCompose()
            }
        ){
            MainBackground()

            Column(horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp)

            )
            {

                    if(user_role.value!="scientist"){

                        Button(onClick = {
                            navController.navigate(
                                Screen.StartTestScreen.route
                            )
                        },

                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.fillMaxWidth(),
                        ){
                            Text("Начать тест",
                                fontSize = 25.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(15.dp))
                    }

                    Button(onClick = {


                            navController.navigate(
                                Screen.UserDetailsScreen.route
                            )



                    },shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth(),

                        ){
                        Text("Профиль",
                            fontSize = 25.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(15.dp))

                    if(user_role.value!="scientist"){
                        Button(onClick = {


                                navController.navigate(
                                    Screen.StatisticScreen.route
                                )



                        },shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.fillMaxWidth(),

                            ){
                            Text("Статистика",
                                fontSize = 25.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(15.dp))
                    }


                if(user_role.value=="scientist"){
                    Button(onClick = {

                        navController.navigate(
                            Screen.AdvancedStatScreen.route
                        )


                    },shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth(),

                        ){
                        Text("Продвинутая статистика",
                            fontSize = 25.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                }



                    Button(onClick = {

                        auth.signOut()

                        navController.popBackStack()
                        val prev_screen_name = "main screen"
                        navController.navigate(
                            Screen.WelcomeScreen.route + "/$prev_screen_name"
                        )


                    },shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth(),

                        ){
                        Text("Выйти",
                            fontSize = 25.sp
                        )
                    }

//                Spacer(modifier = Modifier.height(15.dp))
//                Button(onClick = {
//
//
//                    navController.navigate(
//                        Screen.ChangeLanguageScreen.route
//                    )
//
//                },shape = RoundedCornerShape(16.dp),
//                    modifier = Modifier.fillMaxWidth(),
//
//                    ){
//                    Text("Поменять язык",
//                        fontSize = 25.sp
//                    )
//                }

            }

        }

    }



