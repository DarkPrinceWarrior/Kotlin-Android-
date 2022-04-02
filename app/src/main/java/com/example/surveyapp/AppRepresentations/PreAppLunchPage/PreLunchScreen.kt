package com.example.surveyapp.AppRepresentations.PreAppLunchPage

import android.app.Activity
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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.surveyapp.R
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.withStyle
import androidx.core.app.ActivityCompat.finishAffinity
import com.example.surveyapp.AppRepresentations.MainPage.MainScreen1
import com.example.surveyapp.Screen
import com.google.firebase.auth.FirebaseAuth
import kotlin.system.exitProcess


@Composable
fun PreLunchScreen(navController: NavController){

    BackHandler(enabled = true, onBack = {


    })


    val context = LocalContext.current



    Scaffold(backgroundColor = MaterialTheme.colors.primary)
    {
        PreLuncgBackground()

        Column(verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)

        )
        {
            Spacer(modifier = Modifier.padding(100.dp))
            Text(
                buildAnnotatedString {
                    withStyle(style = ParagraphStyle(lineHeight = 30.sp)) {
                        withStyle(style = SpanStyle(color = Color.Red,
                            fontSize = 25.sp,
                            fontStyle = FontStyle.Italic,
                            fontWeight = FontWeight.Bold,)) {
                            append("Тест жизнестойкости ")
                        }
                        withStyle(
                            style = SpanStyle(
                                fontStyle = FontStyle.Italic,
                                fontWeight = FontWeight.Bold,
                                fontSize = 25.sp
                            )
                        ){
                            append("разработан в рамках изучения факторов, способ ствующих успешно му совладанию со стрессом и снижению внутреннего напряжения")
                        }

                    }
                }
            )

            Spacer(modifier = Modifier.padding(160.dp))

            Button(onClick = {


                            navController.navigate(
                                Screen.WelcomeScreen.route + "/nothing"
                            )

            },


                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)

            ){
                Text("Продолжить",
                    fontSize = 25.sp
                )
            }


        }
    }

}



@Composable
private fun PreLuncgBackground() {
    val painter = painterResource(id = com.example.surveyapp.R.drawable.prelunch)

    Image(painter = painter, contentDescription = null,
        contentScale = ContentScale.FillBounds,
        modifier = Modifier.fillMaxSize()
    )
}
