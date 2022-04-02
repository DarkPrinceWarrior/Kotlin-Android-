package com.example.surveyapp.AppRepresentations.QuestsPage

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.surveyapp.AppRepresentations.*
import com.example.surveyapp.Screen

@Composable
fun StartTest(navController: NavController
){


    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val scope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            if(user_role.value!="anon"){
                TopBar(scope = scope, scaffoldState = scaffoldState)
            }else{
                TopAppBarCompose()
            }

        },
        drawerContent = {
            if(user_role.value!="anon"){
                Drawer(navController,scope = scope, scaffoldState = scaffoldState)
            }

        }

    ){
        QuestsBackground()

        Column(verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)

        )
        {
            Spacer(modifier = Modifier.height(50.dp))
            Card(
                Modifier
                    .weight(4f),
                shape = RoundedCornerShape(32.dp)
            )
            {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp)
                ) {


                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        "ИНСТРУКЦИЯ",
                        fontSize = 20.sp,
                        color = Color.Black,
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(15.dp)
                            .align(Alignment.CenterHorizontally)

                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Divider(modifier = Modifier.padding(bottom = 10.dp), color = Color.Black)
                    Text(
                        "Ответьте, пожалуйста, на несколько вопросов о себе.\n" +
                                "\n" +
                                "Выбирайте тот ответ, который наилучшим образом отражает ваше мнение.\n" +
                                "\n" +
                                "Здесь нет правильных или неправильных ответов, так как важно только ваше мнение.\n" +
                                "\n" +
                                "Просьба работать в темпе, подолгу не задумываясь над ответами.",
                        fontSize = 20.sp,
                        color = Color.Black,
                        fontStyle = FontStyle.Normal,
                        textAlign = TextAlign.Left,
                        modifier = Modifier
                            .padding(5.dp)
                            .align(Alignment.CenterHorizontally)

                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Divider(modifier = Modifier.padding(bottom = 15.dp),color = Color.Black)
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(onClick = {

                        navController.navigate(
                            Screen.QuestsScreen.route
                        )
                    },
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth()
                                .padding(15.dp)


                    ){
                        Text("Начать тестирование",
                            fontSize = 25.sp
                        )
                    }


                }



            }
            Spacer(modifier = Modifier.height(88.dp))

        }

    }


}