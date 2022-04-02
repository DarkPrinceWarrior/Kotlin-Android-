package com.example.surveyapp.AppRepresentations.TestResults

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.surveyapp.AppRepresentations.QuestsPage.QuestsViewModel

import com.example.surveyapp.AppRepresentations.StatPage.definator
import com.example.surveyapp.AppRepresentations.TopAppBarCompose
import com.example.surveyapp.AppRepresentations.user_role
import com.example.surveyapp.R
import com.example.surveyapp.Screen
import com.example.surveyapp.remote.responses.QuestBalls.QuestionBallsItem
import com.example.surveyapp.remote.responses.Quests.QuestsItem

@Composable
private fun StatisticBackground() {
    val painter = painterResource(id = R.drawable.testscreen)

    Image(painter = painter, contentDescription = null,
        contentScale = ContentScale.FillBounds,
        modifier = Modifier.fillMaxSize()
    )
}


@Composable
fun ResultScreen(involvement:String, control:String,risk_taking:String,min:String,seconds:String,
    navController: NavController

) {


    Scaffold(
        topBar = {
            TopAppBarCompose()
        }
    ){
        StatisticBackground()

        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp)

        )
        {

            Card(
                Modifier
                    .weight(1f)
                    .padding(4.dp),
                shape = RoundedCornerShape(32.dp)
            ) {

                Column(

                    modifier = Modifier
                        .fillMaxSize()
                        .padding(5.dp)
                ) {


                        val generalResult = involvement.toInt()+control.toInt()+risk_taking.toInt()

                        Text(
                            text = "Результат теста",
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            fontSize = 30.sp,
                            modifier = Modifier
                                .padding(10.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                        Spacer(modifier = Modifier.height(15.dp))
                        Text(
                            text =  "Время прохождения",
                            textAlign = TextAlign.Left,
                            fontWeight = FontWeight.Bold,
                            fontSize = 30.sp,
                            modifier = Modifier
                                .padding(5.dp)

                        )
                        Text(
                            text = "  $min мин. и $seconds сек.",
                            textAlign = TextAlign.Left,
                            fontSize = 20.sp,
                            modifier = Modifier
                                .padding(10.dp)

                        )

                        Text(
                            text =  "Общий результат",
                            textAlign = TextAlign.Left,
                            fontWeight = FontWeight.Bold,
                            fontSize = 30.sp,
                            modifier = Modifier
                                .padding(5.dp)

                        )
                        Text(
                            text = "  $generalResult баллов (${definator("Общий результат",generalResult.toInt())})",
                            textAlign = TextAlign.Left,
                            fontSize = 20.sp,
                            modifier = Modifier
                                .padding(10.dp)

                        )




                        Text(
                            text =  "Вовлеченность",
                            textAlign = TextAlign.Left,
                            fontWeight = FontWeight.Bold,
                            fontSize = 30.sp,
                            modifier = Modifier
                                .padding(5.dp)

                        )
                        Text(
                            text = "  ${involvement} баллов (${definator("Вовлеченность",involvement.toInt())})",
                            textAlign = TextAlign.Left,
                            fontSize = 20.sp,
                            modifier = Modifier
                                .padding(10.dp)

                        )




                        Text(
                            text =  "Контроль",
                            textAlign = TextAlign.Left,
                            fontWeight = FontWeight.Bold,
                            fontSize = 30.sp,
                            modifier = Modifier
                                .padding(5.dp)

                        )
                        Text(
                            text = "  ${control} баллов (${definator("Контроль",control.toInt())})",
                            textAlign = TextAlign.Left,
                            fontSize = 20.sp,
                            modifier = Modifier
                                .padding(10.dp)

                        )



                        Text(
                            text =  "Принятие риска",
                            textAlign = TextAlign.Left,
                            fontWeight = FontWeight.Bold,
                            fontSize = 30.sp,
                            modifier = Modifier
                                .padding(5.dp)

                        )
                        Text(
                            text = "  ${risk_taking} баллов (${definator("Принятие риска",risk_taking.toInt())})",
                            textAlign = TextAlign.Left,
                            fontSize = 20.sp,
                            modifier = Modifier
                                .padding(10.dp)

                        )


                    Spacer(modifier = Modifier.height(15.dp))
                    Divider(color = Color.Red, thickness = 3.dp)
                    Spacer(modifier = Modifier.height(15.dp))

                    Button(
                        onClick = {
                            if(user_role.value!="anon"){
                                val current_screen = "test_result_screen"
                                navController.navigate(
                                    Screen.MainScreen.route + "/$current_screen"
                                )
                            }
                            else{
                                val current_screen = "test_result_screen"
                                navController.navigate(
                                    Screen.WelcomeScreen.route + "/$current_screen"
                                )
                            }

                        },
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.height(50.dp)
                            .width(200.dp)
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Text(
                            "В меню",
                            fontSize = 20.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }


            }



        }

    }




}






