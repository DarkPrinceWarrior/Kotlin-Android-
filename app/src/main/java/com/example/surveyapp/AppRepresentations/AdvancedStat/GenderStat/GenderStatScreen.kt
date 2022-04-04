package com.example.surveyapp.AppRepresentations.AdvancedStat.GenderStat

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.surveyapp.AppRepresentations.AdvancedStat.AdvancedStatModel
import com.example.surveyapp.AppRepresentations.AdvancedStat.AdvancedStatScreenBackground
import com.example.surveyapp.AppRepresentations.Drawer
import com.example.surveyapp.AppRepresentations.TopBar
import com.example.surveyapp.AppRepresentations.user_role
import com.example.surveyapp.Screen
import com.example.surveyapp.remote.responses.attempt.attemptsItem
import com.example.surveyapp.remote.responses.userResults.UserResults

@Composable
fun GenderStatScreen(
    navController: NavController,
    viewModel: AdvancedStatModel = hiltViewModel()
) {

    viewModel.Compute_Gender_General_Result()

    // Доработать в дальнейшем
    BackHandler(enabled = true, onBack = {
        navController.navigate(
            Screen.AdvancedStatScreen.route
        )

    })

    // Для drawer panel
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val scope = rememberCoroutineScope()


    val Men_GeneraBall = viewModel.MenResult
    val Women_GeneraBall = viewModel.WomenResult

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopBar(scope = scope, scaffoldState = scaffoldState)
        },
        drawerContent = {
            Drawer(navController, scope = scope, scaffoldState = scaffoldState)
        }
    ) {
        AdvancedStatScreenBackground()

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp)

        )
        {

            Spacer(modifier = Modifier.height(20.dp))
            Card(
                Modifier
                    .weight(2f)
                    .padding(10.dp),
                shape = RoundedCornerShape(32.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp)
                ) {
                    Text(
                        text = "Статистика по полу:",
                        fontSize = 40.sp,
                        textAlign = TextAlign.Center,
                        color=Color.Black,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Divider(modifier = Modifier.padding(bottom = 10.dp), color = Color.Red)
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Статистика по муж.:${Men_GeneraBall.value} \n" +
                                "Статистика по жен.:${Women_GeneraBall.value} ",
                        fontSize = 30.sp,
                        textAlign = TextAlign.Left,
                        color=Color.Black,
                        modifier = Modifier.fillMaxWidth()
                    )

                }

            }

            Spacer(modifier = Modifier.height(300.dp))


        }

    }

}
