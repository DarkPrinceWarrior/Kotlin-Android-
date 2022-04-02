package com.example.surveyapp.AppRepresentations

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.surveyapp.AppRepresentations.QuestsPage.QuestsViewModel
import com.example.surveyapp.R
import com.example.surveyapp.Screen
import com.example.surveyapp.remote.responses.Users.User
import com.example.surveyapp.util.Resource
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


// remember role during user session
public var user_role = mutableStateOf("")

fun count_fullyears(dateOfBirth: MutableState<String>, currentYear: Int):Int{

    try {
        val parsedInt = (dateOfBirth.value).toInt()
        println(parsedInt)
        println(currentYear)
        return currentYear - parsedInt

    } catch (nfe: NumberFormatException) {
        println("Wrong")
        return 0
    }


}

fun isNumber(str: String) = try {
    str.toDouble()
    true
} catch (e: NumberFormatException) {
    false
}


fun RoleIdent(login: String):Int{
    var role_id = 0
    val statesList = mutableListOf("rus","usa","fra","eng")
    // should be dictionary
    val townsList = mutableListOf("vla","mos","spb","ekt")
    val string = login.substringAfter('_')
    if(string.length==16 || string.length==20)
    {

        if(string.substring(0..4)=="admin")
        {
            if(string[5]=='@'){
                val string1 = string.substringAfter('@')
                println(string1)
                if(statesList.contains(string1.substring(0..2))){

                    if(string1.substring(3..5) in townsList){

                        if(isNumber(string1.substring(6..9))){
                            role_id = 1
                        }
                    }
                }
            }
        }
        else if(string.substring(0..8)=="scientist"){

            if(string[9]=='@'){
                val string1 = string.substringAfter('@')
                println(string1)
                if(statesList.contains(string1.substring(0..2))){

                    if(string1.substring(3..5) in townsList){

                        if(isNumber(string1.substring(6..9))){
                            role_id = 3
                        }
                    }
                }
            }
        }
        else{
            role_id = 2
        }
    }
    else{
        role_id = 2
    }
    return role_id
}


// with icon references to drawer
@Composable
fun TopBar(scope: CoroutineScope, scaffoldState: ScaffoldState) {

    TopAppBar(
        modifier =
        Modifier
            .height(70.dp),


        title = {
            Box(
                modifier =
                Modifier
                    .fillMaxWidth()
            ) {

                Image(
                    painter = painterResource(id = R.drawable.ic_maddi3),
                    contentDescription = "App Logo",
                    modifier = Modifier
                        .size(400.dp)
                        .align(Alignment.Center),
                    colorFilter = ColorFilter.tint(Color.White)
                )
            }

        },
        backgroundColor = Color.Black,
        contentColor = Color.White,

        navigationIcon = {
            IconButton(onClick = {
                scope.launch {
                    scaffoldState.drawerState.open()
                }
            }) {
                Icon(Icons.Filled.Menu, "")
            }
        },

        )

}

// with image on top without icon leads to drawer
@Composable
fun TopAppBarCompose(){

    TopAppBar(
        modifier =
        Modifier
            .height(70.dp),


        title = {
            Box(modifier =
            Modifier
                .fillMaxWidth()){

                Image(
                    painter = painterResource(id = R.drawable.ic_maddi3),
                    contentDescription = "App Logo",
                    modifier = Modifier
                        .size(400.dp)
                        .align(Alignment.Center),
                    colorFilter = ColorFilter.tint(Color.White)
                )
            }

        },
        backgroundColor = Color.Black

    )


}



@Composable
fun DrawerItem(navController: NavController, title:String, scaffoldState: ScaffoldState) {
    val auth = FirebaseAuth.getInstance()
    Text(
        text = title,
        style = TextStyle(fontSize = 30.sp, fontWeight = FontWeight.Bold),

        modifier = Modifier
            .clickable(onClick = {

                if (title == " • Главное меню") {
                    val prev_screen_name = "another_one"
                    navController.navigate(
                        Screen.MainScreen.route + "/$prev_screen_name"
                    )
                }
                if (title == " • Профиль") {
                    navController.navigate(
                        Screen.UserDetailsScreen.route
                    )
                }
                if (title == " • Статистика") {
                    navController.navigate(
                        Screen.StatisticScreen.route
                    )
                }
                if (title == " • Выйти из профиля") {
                    auth.signOut()
                    navController.popBackStack()
                    val prev_screen_name = "question_screen"
                    navController.navigate(
                        Screen.WelcomeScreen.route + "/$prev_screen_name"
                    )
                }

            })
            .fillMaxWidth()
    )

}


@Composable
fun Drawer(navController: NavController, scope: CoroutineScope, scaffoldState: ScaffoldState) {

    Column(
        modifier = Modifier
            .background(color = Color.White)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(Color.Black),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            Text(
                text = "Мадди",
                color = Color.White,
                fontSize = 45.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(12.dp)

            )

        }
        Divider(color = Color.Red, thickness = 10.dp )


        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp)
        )
        {
            DrawerItem(navController,title=" • Главное меню",scaffoldState = scaffoldState)
            Spacer(modifier = Modifier.height(10.dp))
            if(user_role.value!="scientist"){
                DrawerItem(navController,title=" • Статистика",scaffoldState = scaffoldState)
                Spacer(modifier = Modifier.height(10.dp))
            }
            DrawerItem(navController,title=" • Профиль",scaffoldState = scaffoldState)
            Spacer(modifier = Modifier.height(10.dp))
            DrawerItem(navController,title=" • Выйти из профиля",scaffoldState = scaffoldState)
        }





    }
}




@Composable
fun CommonDialog(
    caseEvent: String,
    title: String?,
    state: MutableState<Boolean>,
    navController: NavController,
    current_screen: String,
    DialogState: MutableState<Boolean>,
    userId: Int,
    content: @Composable() (() -> Unit)? = null,
    viewModel: QuestsViewModel = hiltViewModel()
) {
    val user_id = userId
    val scope = rememberCoroutineScope()
    if(caseEvent!="timeOut"){
        AlertDialog(
            onDismissRequest = {
                state.value = false
            },
            title = title?.let {
                {
                    Column(
                        Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(text = title)
                        Divider(modifier = Modifier.padding(bottom = 8.dp))
                    }
                }
            },
            text = content,

            dismissButton = {

                Button(onClick = {

                    state.value = false
                    DialogState.value = false



                }) {
                    Text("Нет")
                }
            },
            confirmButton = {
                Button(onClick = {
                    state.value = false
                    DialogState.value = false
                    if(user_role.value!="anon") {
                        scope.launch {
                            viewModel.post_Delete_Attempt(user_id)
                        }
                        navController.navigate(Screen.MainScreen.route + "/$current_screen")
                    }
                   else{
                        navController.navigate(Screen.WelcomeScreen.route + "/$current_screen")
                    }
                }) {
                    Text("Да")
                }
            }, modifier = Modifier.padding(vertical = 8.dp)
        )
    }
    else{
        AlertDialog(
            onDismissRequest = {
                state.value = false
            },
            title = title?.let {
                {
                    Column(
                        Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(text = title)
                        Divider(modifier = Modifier.padding(bottom = 8.dp))
                    }
                }
            },
            text = content,

            confirmButton = {
                Button(onClick = {
                    state.value = false
                    DialogState.value = false
                    if(user_role.value!="anon") {
                        scope.launch {
                            viewModel.post_Delete_Attempt(user_id)
                        }
                        navController.navigate(Screen.MainScreen.route + "/$current_screen")
                    }
                    else{
                        navController.navigate(Screen.WelcomeScreen.route + "/$current_screen")
                    }
                }) {
                    Text("Ок")
                }
            }, modifier = Modifier.padding(vertical = 8.dp)
        )
    }

}




@Composable
fun timer(
    totalTime: Long,
    timeOver: Boolean
): Long {

    var currentTime by remember {
        mutableStateOf(totalTime)
    }

    LaunchedEffect(key1 = currentTime, key2 = timeOver) {
        if (currentTime > 0 && timeOver) {
            delay(100L)
            currentTime -= 100L

        }
    }

        return currentTime / 1000


}


@Composable
fun QuestsBackground() {

    val painter = painterResource(id = R.drawable.testscreen)

    Image(
        painter = painter, contentDescription = null,
        contentScale = ContentScale.FillBounds,
        modifier = Modifier.fillMaxSize()
    )
}


@Composable
fun genderDialog(
    state: MutableState<Boolean>,
    DialogState: MutableState<Boolean>,
    gender: MutableState<String>,
    content: @Composable() (() -> Unit)? = null,
) {
    val title = "Выберите ваш пол"
    val scope = rememberCoroutineScope()
    AlertDialog(
        onDismissRequest = {
            state.value = false
        },
        title = title.let {
            {
                Column(
                    Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
//                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = title,
                        Modifier.padding(bottom = 10.dp),
                        style = TextStyle(
                            fontWeight = FontWeight.Bold, fontSize = 20.sp

                        )
                    )
                    Divider(modifier = Modifier.padding(bottom = 10.dp))
                }
            }
        },
        text = content,

        dismissButton = {

            Button(
                onClick = {

                    state.value = false
                    DialogState.value = false
                    gender.value = "Мужчина"

                },
            ) {
                Text("Мужчина")
            }
        },
        confirmButton = {
            Button(onClick = {
                state.value = false
                DialogState.value = false
                gender.value = "Женщина"

            }) {
                Text("Женщина")
            }
        },

        modifier = Modifier.padding(vertical = 8.dp)
    )

}





