package com.example.surveyapp.AppRepresentations.QuestsPage

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.surveyapp.AppRepresentations.*
import com.example.surveyapp.Screen
import com.example.surveyapp.remote.responses.QuestBalls.QuestionBallsItem
import com.example.surveyapp.remote.responses.Quests.QuestsItem
import com.example.surveyapp.remote.responses.Users.User
import com.example.surveyapp.util.Resource
import kotlinx.coroutines.launch


@Composable
fun QuestsScreen(
    navController: NavController,
    questEntries: List<QuestsItem>,
    QuestsBallsList: List<QuestionBallsItem>,
    viewModel: QuestsViewModel= hiltViewModel()
) {



    var userId: Int = 0
    var attemptNumber = 0
    var attemptId = 0

    // Словарь, чтобы по ответу и вопросу сразу найти id в таблице answer_question,
    // чтобы потом закинуть id в таблицу user_answer в поле choice_id
    val question_answer_dictList = viewModel.getQuestBalls(QuestsBallsList)

    if(user_role.value!="anon") {

        // Получаем id юзера
        val userInfo = produceState<Resource<User>>(initialValue = Resource.Loading()) {
            value = viewModel.getUserInfo()
        }.value


        userId = 0
        attemptNumber = 0

        val UserAttemptsEntry1 by remember { viewModel.userAttempts }

        if (userInfo is Resource.Success) {

            userInfo.data?.let { it1 ->
                userId = it1.id

            }

            for (item in UserAttemptsEntry1) {

                // Когда в таблице Попыток вообще нет записей
                if (item.userId != 0) {

                    // Когда есть записи
                    if (item.userId == userId) {
                        attemptNumber = kotlin.math.max(attemptNumber, item.attempt_number)
                    }
                }
            }
            // Когда нет записей для юзера номер попытки будет №1
            // И еще когда есть записи, но записей именно для этого юзера нет
            // Тогда тоже номер пыпытки №1

            attemptNumber += 1


            // Первый пост запрос на попытку, когда юзер только начал тест
            produceState<Resource<User>>(initialValue = Resource.Loading()) {
                viewModel.postNewUserAttempt(attemptNumber, userId, "-1:-1")


            }

        }

        // Заносим id новой попытки в таблице как кол-во записей в таблице + 1
        attemptId = UserAttemptsEntry1.size + 1

    }


    var IsBackPressed = false

    val scope = rememberCoroutineScope()

    var timeOver = true


    var questId by remember {
        mutableStateOf(0)
    }
    val numberOfquests = questEntries.size


    var startORend by remember {
        mutableStateOf(false)
    }

    var startORend2 by remember {
        mutableStateOf(true)
    }

    var min by remember {
        mutableStateOf(0)
    }
    var seconds by remember {
        mutableStateOf(0)
    }


    var buttonPressed by remember {
        mutableStateOf(false) }
    var finishTestPressed by remember {
        mutableStateOf(false)
    }

    val questNumbers = arrayListOf(1)
    val AnswerNumber = arrayListOf(0)

    // just stupid initialization to get the list of 45 elements
    for (i in 0 until 44) {

        questNumbers.add(i + 2)
        AnswerNumber.add(i)

    }



    val attempt_time = remember {
        mutableStateOf(1L)
    }
    // Для того, чтобы активировать событие выхода из теста
    val DialogState = remember { mutableStateOf(false) }
    val DialogStateExitTest = remember { mutableStateOf(false) }
    val state = remember { mutableStateOf(true) }
    BackHandler(enabled = true, onBack =
    {
        if(!finishTestPressed){
            DialogState.value = true
        }
        else{
            val current_screen = "quest_page_finished"

            if(user_role.value!="anon") {
                navController.navigate(
                    Screen.MainScreen.route + "/$current_screen")
            }
            else{
                navController.navigate(
                    Screen.WelcomeScreen.route + "/$current_screen")
            }
        }



    })

    if(DialogState.value){

        val current_screen = "question_screen"
        CommonDialog("exitTest","Вы хотите выйти из теста?",state,navController,current_screen,DialogState,userId)
    }




        // Для drawer panel
        val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))


        Scaffold(
            scaffoldState = scaffoldState,

            topBar = {
                TopAppBarCompose()
            },

        ) {

            QuestsBackground()

            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp)
            )
            {
                Card(
                    Modifier
                        .weight(1f),
                    shape = RoundedCornerShape(32.dp)
                )
                {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp)
                    ) {


                        // Когда время выйдет,и тест еще не завершен,
                        // то диалог и выбрасывает на главный экран
                        DialogStateExitTest.value = attempt_time.value.toInt() == 0
                        if(DialogStateExitTest.value){
                            CommonDialog("timeOut","Время вышло!",
                                state,navController,"question_screen",DialogStateExitTest,userId)
                        }



//                      Счет в секундах
                        attempt_time.value = timer(
                            totalTime = 420L * 1000L,timeOver

                        )


                        val minutes = (attempt_time.value % 3600) / 60;
                        seconds = (attempt_time.value % 60).toInt();

                        val timeString = String.format("%02d:%02d", minutes, seconds)
                        if (questId <= numberOfquests - 1) {


                            val question_number = questId + 1



                            val message = "The question is $question_number/45 \n" +
                                    timeString
                            val size: TextUnit = 25.sp
                            Text(
                                text = message,
                                fontSize = size,
                                color = Color.Black,
                                fontStyle = FontStyle.Normal,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .padding(10.dp)

                            )

                            startORend2 = true


                            // for back button
                            if (questId > 0) {
                                startORend = true
                            } else if(questId == 0){
                                startORend = false
                            }


                            val question_text = questEntries[questId].question_text
                            Text(
                                text = question_text,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                modifier = Modifier
                                    .padding(7.dp)
                                    .align(Alignment.CenterHorizontally)
                            )
                        } else {


                            val message = timeString
                            val size: TextUnit = 25.sp
                            Text(
                                text = message,
                                fontSize = size,
                                color = Color.Black,
                                fontStyle = FontStyle.Normal,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .padding(10.dp)

                            )


                            val choices_id = mutableListOf<Int>()
                            choices_id.clear()

                            // balls for each category
                            val invole_balls = arrayListOf(0)
                            val control_balls = arrayListOf(0)
                            val risk_balls = arrayListOf(0)
                            var involvement = 0
                            var control = 0
                            var risk_taking = 0

                            var k = 1
                            for (question in questEntries) {

                                if (question.question_type == "Контроль") {
                                    control_balls.add(k)
                                } else if (question.question_type == "Вовлечённость") {
                                    invole_balls.add(k)
                                } else {
                                    risk_balls.add(k)

                                }
                                k += 1

                            }


                            var ball_sum = 0

                            for (i in 0 until 45) {

                                for (item in QuestsBallsList) {


                                    if (item.answerId == AnswerNumber[i]
                                        && item.questionId == questNumbers[i]
                                    ) {

                                        choices_id.add(item.id)
                                        if (invole_balls.contains(item.questionId)) {
                                            involvement += item.question_balls
                                        } else if (control_balls.contains(item.questionId)) {
                                            control += item.question_balls
                                        } else {
                                            risk_taking += item.question_balls
                                        }

//
                                        ball_sum += item.question_balls
                                    }


                                }
                            }


                            Button(
                                onClick = {

                                    timeOver = false
                                    startORend = false

                                    val total_time = 420
                                    val user_time = total_time - attempt_time.value

                                    val check_min = user_time.toInt() % 60

                                    if (check_min == 0) {
                                        min = (user_time / 60).toInt()

                                    } else if (user_time > 60) {
                                        seconds = check_min

                                        min = (user_time / 60).toInt()


                                    } else {
                                        min = 0
                                        seconds = check_min

                                    }

                                    if(user_role.value!="anon") {
                                        scope.launch {



                                            viewModel.post_Update_Attempt(
                                                userId, "$min:$seconds"
                                            )

                                            viewModel.postNewUserResult(
                                                involvement,
                                                control, risk_taking, attemptId
                                            )
                                        }
                                    }


                                    buttonPressed = true
                                    finishTestPressed = true


                                },
                                shape = RoundedCornerShape(16.dp),
                                modifier = Modifier.fillMaxWidth(),
                                enabled = !buttonPressed,


                                ) {
                                Text(
                                    "Закончить тест:",

                                    fontSize = 25.sp
                                )
                            }


                            Spacer(modifier = Modifier.height(16.dp))
                            Spacer(modifier = Modifier.height(16.dp))


                            if(finishTestPressed){
                                Button(
                                    onClick = {
//
                                        navController.navigate(
                                            Screen.ResultTestScreen.route + "/${involvement}/${control}/${risk_taking}" +
                                                    "/$min/${seconds}"
                                        )



                                    },
                                    shape = RoundedCornerShape(16.dp),
                                    modifier = Modifier.fillMaxWidth(),
                                    enabled = finishTestPressed,


                                    ) {
                                    Text(
                                        "Перейти к результатам:",

                                        fontSize = 25.sp
                                    )


                                }
                            }




                            startORend2 = false
                        }

                    }
                }


                Spacer(modifier = Modifier.height(16.dp))


                if (startORend2) {

                    Button(
                        onClick = {

                            // new answer or just and answer
                                val choice = question_answer_dictList[0][questId+1]?.get(1)

                                if(IsBackPressed){
                                    // old answer
                                    val old_Choice = question_answer_dictList[AnswerNumber[questId]-1][questId+1]?.
                                    get(AnswerNumber[questId])
                                    if (old_Choice != null && choice!=null) {
                                        viewModel.post_UpdateAnswer(old_Choice,choice,attemptId)
                                    }

                                }
                                else{
                                    if (choice != null) {
                                        viewModel.postNewUserAnswer(
                                            choice,attemptId
                                        )
                                    }
                                }

                            AnswerNumber[questId] = 1
                            questId += 1
                            IsBackPressed = false
                        },
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth()

                    ) {
                        Text(
                            "Нет",
                            fontSize = 20.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {

                            if(user_role.value!="anon") {
                                // new answer or just and answer
                                val choice = question_answer_dictList[1][questId+1]?.get(2)

                                if(IsBackPressed){
                                    // old answer
                                    val old_Choice = question_answer_dictList[AnswerNumber[questId]-1][questId+1]?.
                                    get(AnswerNumber[questId])
                                    if (old_Choice != null && choice!=null) {
                                        viewModel.post_UpdateAnswer(old_Choice, choice, attemptId)
                                    }

                                }
                                else{
                                    if (choice != null) {
                                        viewModel.postNewUserAnswer(
                                            choice,attemptId
                                        )
                                    }
                                }
                            }


                            AnswerNumber[questId] = 2
                            questId += 1
                            IsBackPressed = false

                        },
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            "Скорее нет",
                            fontSize = 20.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {

                            if(user_role.value!="anon") {
                                // new answer or just and answer
                                val choice = question_answer_dictList[2][questId+1]?.get(3)

                                if(IsBackPressed){
                                    // old answer
                                    val old_Choice = question_answer_dictList[AnswerNumber[questId]-1][questId+1]?.
                                    get(AnswerNumber[questId])
                                    if (old_Choice != null && choice!=null) {
                                        viewModel.post_UpdateAnswer(old_Choice, choice, attemptId)
                                    }

                                }
                                else{
                                    if (choice != null) {
                                        viewModel.postNewUserAnswer(
                                            choice,attemptId
                                        )
                                    }
                                }
                            }


                            AnswerNumber[questId] = 3
                            questId += 1
                            IsBackPressed = false

                        },
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            "Скорее да",
                            fontSize = 20.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {

                            if(user_role.value!="anon") {
                                // new answer or just and answer
                                val choice = question_answer_dictList[3][questId+1]?.get(4)

                                if(IsBackPressed){
                                    // old answer
                                    val old_Choice = question_answer_dictList[AnswerNumber[questId]-1][questId+1]?.
                                    get(AnswerNumber[questId])
                                    if (old_Choice != null && choice!=null) {
                                        viewModel.post_UpdateAnswer(old_Choice, choice, attemptId)
                                    }

                                }
                                else{
                                    if (choice != null) {
                                        viewModel.postNewUserAnswer(
                                            choice,attemptId
                                        )
                                    }
                                }
                            }



                            AnswerNumber[questId] = 4
                            questId += 1
                            IsBackPressed = false

                        },
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            "да",
                            fontSize = 20.sp
                        )
                    }

                }

                Spacer(modifier = Modifier.height(48.dp))

                if (startORend) {

                    if(!finishTestPressed){
                        Button(
                            onClick = {
                                IsBackPressed = true
                                questId -= 1

                            },
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                "назад",
                                fontSize = 20.sp
                            )
                        }
                    }

                }


            }
        }


    }