package com.example.surveyapp.AppRepresentations.StatPage

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
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
import com.example.surveyapp.AppRepresentations.Drawer
import com.example.surveyapp.AppRepresentations.TopAppBarCompose
import com.example.surveyapp.AppRepresentations.TopBar
import com.example.surveyapp.R
import com.example.surveyapp.Screen
import com.example.surveyapp.remote.responses.Users.User
import com.example.surveyapp.remote.responses.attempt.attemptsItem
import com.example.surveyapp.remote.responses.userResults.UserResults
import com.example.surveyapp.util.Resource
import com.google.firebase.auth.FirebaseAuth



@Composable
private fun StatisticBackground() {
    val painter = painterResource(id = R.drawable.testscreen)

    Image(painter = painter, contentDescription = null,
        contentScale = ContentScale.FillBounds,
        modifier = Modifier.fillMaxSize()
    )
}


@Composable
fun UserStatistic(attemptId:String, passTime:String,navController: NavController, viewModel: StatViewModel = hiltViewModel()) {


    val UserResult = produceState<Resource<UserResults>>(initialValue = Resource.Loading()) {
        value = viewModel.getUserResult(attemptId.toInt())
    }.value


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
                    UserResult.data?.let { it1 ->

                        val generalResult = it1.involvement+it1.control+it1.risk_taking
                        val colon_pos = passTime.indexOf(':')
                        val min = passTime.substring(0,colon_pos+1)
                        val seconds = passTime.substring(colon_pos+1)


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
                            text = "  $generalResult баллов (${definator("Общий результат",generalResult)})",
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
                            text = "  ${it1.involvement} баллов (${definator("Вовлеченность",it1.involvement)})",
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
                            text = "  ${it1.control} баллов (${definator("Контроль",it1.control)})",
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
                            text = "  ${it1.risk_taking} баллов (${definator("Принятие риска",it1.risk_taking)})",
                            textAlign = TextAlign.Left,
                            fontSize = 20.sp,
                            modifier = Modifier
                                .padding(10.dp)

                        )

                    }
                    Spacer(modifier = Modifier.height(15.dp))
                    Divider(color = Color.Red, thickness = 3.dp)
                    Spacer(modifier = Modifier.height(15.dp))

                    Button(
                        onClick = {
                            navController.navigate(
                                Screen.StatisticScreen.route
                            )
                        },
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.height(50.dp)
                            .width(200.dp)
                            .align(CenterHorizontally)
                    ) {
                        Text(
                            "Назад",
                            fontSize = 20.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }


            }



        }

    }

}


fun definator(title:String, balls_sum:Int): String {


    if(title == "Общий результат"){

        if(balls_sum<62){
            return "Низкий результат"
        }
        if(balls_sum in 62..99){
            return "Средний результат"
        }
        else{
            return "Высокий результат"
        }

    }
    if(title == "Вовлечённость"){
        if(balls_sum<30){
            return "Низкий результат"
        }
        if(balls_sum in 30..46){
            return "Средний результат"
        }
        else{
            return "Высокий результат"
        }
    }
    if(title == "Контроль"){
        if(balls_sum<21){
            return "Низкий результат"
        }
        if(balls_sum in 21..38){
            return "Средний результат"
        }
        else{
            return "Высокий результат"
        }
    }
    else{
        if(balls_sum<9){
            return "Низкий результат"
        }
        if(balls_sum in 9..18){
            return "Средний результат"
        }
        else{
            return "Высокий результат"
        }
    }


}

