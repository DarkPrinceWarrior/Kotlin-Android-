package com.example.surveyapp.AppRepresentations.AdvancedStat.UsersStat

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.Coil
import coil.request.ImageRequest
import com.example.surveyapp.AppRepresentations.AdvancedStat.AdvancedStatScreenBackground
import com.example.surveyapp.AppRepresentations.Drawer
import com.example.surveyapp.AppRepresentations.MainPage.MainViewModel
import com.example.surveyapp.AppRepresentations.TopBar
import com.example.surveyapp.R
import com.example.surveyapp.Screen
import com.example.surveyapp.remote.responses.Users.User
import com.google.accompanist.coil.CoilImage


@Composable
fun UsersStatScreen(navController: NavController,
                    viewModel: UserStatModel = hiltViewModel()){



    // Доработать в дальнейшем
    BackHandler(enabled = true, onBack = {

//        navController.popBackStack()

    })

    val usersList = viewModel.userList
    val loadError = viewModel.loadError
    val isLoading = viewModel.isLoading
    val endReached = viewModel.endReached
    val Users_attempts_number_List = viewModel.Users_attempts_number_List
    val Users_AVEresults_List = viewModel.Users_AVEresults_List

    // Для drawer panel
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
        AdvancedStatScreenBackground()

        LazyColumn(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp)

        )
        {

            val itemCount = if(usersList.value.size % 2 == 0) {
                usersList.value.size / 2
            } else {
                usersList.value.size / 2 + 1
            }
            items(itemCount) {
                if(it >= itemCount - 1 && !endReached.value) {
                    viewModel.loadUserPaginated()
                }
                UserRow(rowIndex = it, entries = usersList.value,Users_attempts_number_List.value,Users_AVEresults_List.value, navController = navController)
            }


        }

        Box(
            contentAlignment = Center,
            modifier = Modifier.fillMaxSize()
        ) {
            if(isLoading.value) {
                CircularProgressIndicator(color = MaterialTheme.colors.primary)
            }
            if(loadError.value.isNotEmpty()) {
                RetrySection(error = loadError.value) {
                    viewModel.loadUserPaginated()
                }
            }
        }

    }

}



@Composable
fun UserEntry(
    entry: User,
    attempt: Int,
    result: Int,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: UserStatModel = hiltViewModel()
) {


    Box(
        contentAlignment = Center,
        modifier = modifier
            .background(Color.White)
            .shadow(5.dp, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .aspectRatio(1f)
    ) {
        Column {

            Text(
                text = "Пол: ${entry.gender}\nВозраст: ${entry.age}\n" +
                        "Кол-во попыток: ${attempt}\n" +
                        "Ср. результат: $result ",
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }

    }

}



@Composable
fun UserRow(
    rowIndex: Int,
    entries: List<User>,
    attempts: List<Int>,
    results: List<Int>,
    navController: NavController
) {
    Column {
        Row {
            UserEntry(
                entry = entries[rowIndex * 2],
                attempt = attempts[rowIndex * 2],
                result = results[rowIndex * 2],
                navController = navController,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            if(entries.size >= rowIndex * 2 + 2) {
                UserEntry(
                    entry = entries[rowIndex * 2 + 1],
                    attempt = attempts[rowIndex * 2 + 1],
                    result = results[rowIndex * 2 + 1],
                    navController = navController,
                    modifier = Modifier.weight(1f)
                )
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}



@Composable
fun RetrySection(
    error: String,
    onRetry: () -> Unit
) {
    Column {
        Text(error, color = Color.Red, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { onRetry() },
            modifier = Modifier.align(CenterHorizontally)
        ) {
            Text(text = "Retry")
        }
    }
}
