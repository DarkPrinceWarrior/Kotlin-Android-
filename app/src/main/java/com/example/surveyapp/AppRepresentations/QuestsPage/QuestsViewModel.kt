package com.example.surveyapp.AppRepresentations.QuestsPage

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.surveyapp.remote.responses.QuestBalls.QuestionBallsItem
import com.example.surveyapp.remote.responses.Quests.QuestsItem
import com.example.surveyapp.remote.responses.User_result.user_resultsItem
import com.example.surveyapp.remote.responses.Users.User
import com.example.surveyapp.remote.responses.attempt.attemptsItem
import com.example.surveyapp.repository.Repository
import com.example.surveyapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class QuestsViewModel @Inject constructor(private val repository: Repository):
    ViewModel(){


    init {
        getAllQuest()
        getAllQuestBalls()
        getAllUserAttempts()
    }



    var QuestsList = mutableStateOf<List<QuestsItem>>(listOf())
    var userAttempts = mutableStateOf<List<attemptsItem>>(listOf())
    var QuestsBallsList = mutableStateOf<List<QuestionBallsItem>>(listOf())

    fun getQuestBalls(QuestsBallsList: List<QuestionBallsItem>): MutableList<Map<Int, Map<Int, Int>>> {

        val question_answer_dictFirst =
            mutableMapOf(1
                to mapOf(1 to 1))
        val question_answer_dictSecond =
            mutableMapOf(1
                to mapOf(2 to 46))
        val question_answer_dictThird =
            mutableMapOf(1
                to mapOf(3 to 91))
        val question_answer_dictFourth =
            mutableMapOf(1
                to mapOf(4 to 136))

        val question_answer_dictList = mutableListOf<Map<Int, Map<Int, Int>>>()

        for(item in QuestsBallsList) {

            when (item.id) {

                in 1..45 -> {
                    question_answer_dictFirst[item.questionId] = mapOf(item.answerId to item.id)

                    if(item.id==45){
                        question_answer_dictList.add(question_answer_dictFirst)
                    }

                }
                in 46..90 -> {
                    question_answer_dictSecond[item.questionId] = mapOf(item.answerId to item.id)

                    if(item.id==90){
                        question_answer_dictList.add(question_answer_dictSecond)
                    }
                }
                in 91..135 -> {
                    question_answer_dictThird[item.questionId] = mapOf(item.answerId to item.id)

                    if(item.id==135){
                        question_answer_dictList.add(question_answer_dictThird)
                    }
                }
                in 136..180 -> {
                    question_answer_dictFourth[item.questionId] = mapOf(item.answerId to item.id)

                    if(item.id==180){
                        question_answer_dictList.add(question_answer_dictFourth)
                    }
                }
            }

        }

        return question_answer_dictList

    }



    suspend fun postNewUserAttempt(attempt_number: Int,
                                   userId: Int,
                                   passing_time: String): Response<attemptsItem> {
        return repository.postNewUserAttempt(attempt_number,userId,passing_time)

    }

    suspend fun postNewUserResult(involvement: Int,
                                   control: Int,
                                   risk_taking: Int,
                                   attemptId: Int): Response<user_resultsItem> {
        return repository.postNewUserResult(involvement,control,risk_taking,attemptId)

    }

    fun postNewUserAnswer(choiceId: Int,
                      attemptId: Int){
        viewModelScope.launch {
            repository.postNewUserAnswer(choiceId,attemptId)

        }


    }

    fun post_UpdateAnswer(choiceId: Int, new_choice: Int, attemptId: Int){
        viewModelScope.launch {
            repository.post_UpdateAnswer(choiceId,new_choice,attemptId)

        }


    }

    suspend fun getUserInfo(email: String = repository.getUser()): Resource<User> {

        return if(email!="None"){

            repository.getUser(email)
        } else{
            val user = User(0,"None","None",
                "None", 0,0)

            Resource.Error("There is no user")
        }


    }

    fun getAllUserAttempts() {
        viewModelScope.launch {
            when (val result = repository.getUserAttempts()) {
                is Resource.Success -> {

                    val userAttempts_entry = result.data

                    if (userAttempts_entry != null) {
                        for (item in userAttempts_entry) {

                            userAttempts.value += attemptsItem(
                                item.id,item.attempt_number,
                                item.passing_time, item.userId)
                        }
                    }
                }else -> {
                println("Error when attempts") } }
        } }

    fun getAllQuest() {
        viewModelScope.launch {
            when (val result = repository.getQuests()) {
                is Resource.Success -> {

                    val quest_entry = result.data

                    if (quest_entry != null) {
                        for (item in quest_entry) {

                            QuestsList.value += QuestsItem(item.question_text,
                                item.question_type, item.statement_type)
                        }
                    }
                }else -> {
                    println("Error when quests") } }
        } }


    fun getAllQuestBalls() {
        viewModelScope.launch {
            when (val result = repository.getQuestBalls()) {
                is Resource.Success -> {

                    val questBalls_entry = result.data

                    if (questBalls_entry != null) {
                        for (item in questBalls_entry) {

                            QuestsBallsList.value += QuestionBallsItem(item.id,
                                item.answerId,
                            item.questionId, item.question_balls)
                        }
                    }
                }else -> {
                println("Error when quests") } }
        } }


    // Для attempt
    suspend fun post_Delete_Attempt(userId: Int): Response<attemptsItem> {
        return repository.post_deleteAttempt(userId)

    }
    suspend fun post_Update_Attempt(userId: Int,
                                    passing_time: String ): Response<attemptsItem> {
        return repository.post_UpdateAttempt(userId,passing_time)

    }




}