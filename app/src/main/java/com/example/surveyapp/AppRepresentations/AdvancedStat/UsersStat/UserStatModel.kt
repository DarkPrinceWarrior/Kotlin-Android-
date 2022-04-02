package com.example.surveyapp.AppRepresentations.AdvancedStat.UsersStat

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.surveyapp.remote.Constants.PAGE_SIZE
import com.example.surveyapp.remote.responses.Users.User
import com.example.surveyapp.remote.responses.attempt.attemptsItem
import com.example.surveyapp.remote.responses.userResults.UserResults
import com.example.surveyapp.repository.Repository
import com.example.surveyapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.util.*
import javax.inject.Inject



@HiltViewModel
class UserStatModel@Inject constructor(private val repository: Repository):
    ViewModel(){

    private var curPage = 0

    var userList = mutableStateOf<List<User>>(listOf())
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var endReached = mutableStateOf(false)

    var attemptsList = mutableStateOf<List<attemptsItem>>(listOf())
    var resultsList = mutableStateOf<List<UserResults>>(listOf())

    var Users_attempts_number_List = mutableStateOf<List<Int>>(listOf())
    var Users_AVEresults_List = mutableStateOf<List<Int>>(listOf())

    init {
        loadUserPaginated()
    }

    init {
        get_all_Attempts()
    }

    init {
        get_all_Results()
    }



    fun get_all_Attempts() {
        viewModelScope.launch {

            when(val result = repository.getUserAttempts()) {
                is Resource.Success -> {

                    val Users_Attempts = result.data!!.mapIndexed { _, entry ->

                        attemptsItem(
                            entry.id, entry.attempt_number,
                            entry.passing_time, entry.userId
                        )

                    }

                    attemptsList.value += Users_Attempts


                }
            }

        }

    }


    fun get_all_Results() {

        viewModelScope.launch {

            when (val result = repository.getAllResults()) {
                is Resource.Success -> {

                    val Users_Results = result.data!!.mapIndexed { _, entry ->
                        UserResults(
                            entry.id, entry.General_result,
                            entry.attemptId, entry.control, entry.involvement,
                            entry.risk_taking
                        )

                    }

                    resultsList.value += Users_Results


                }

            }

        }
    }


    fun loadUserPaginated() {
//        Thread.sleep(300)
        viewModelScope.launch {
            isLoading.value = true
            val result = repository.getAllUsers()

            when(result) {
                is Resource.Success -> {
                    endReached.value = curPage * PAGE_SIZE >= result.data!!.count()
                    result.data.mapIndexed { index, entry ->
                        userList.value+=User(entry.id, entry.email, entry.login,
                            entry.gender, entry.age, entry.role_id)
                        var attempt_number = 0
                        var average_general_result = 0
                        var counter = 0
                        for (attempt in attemptsList.value) {

                            if (entry.id == attempt.userId) {
                                counter+=1
                                attempt_number+=1
                                for (results in resultsList.value) {

                                    if (attempt.id == results.attemptId) {
                                        average_general_result+=results.General_result
                                    }
                                }
                            }
                        }
                        if(counter!=0){
                            average_general_result /= counter
                        }
                        Users_attempts_number_List.value+=attempt_number
                        Users_AVEresults_List.value+=average_general_result
                    }


                    curPage++
                    loadError.value = ""
                    isLoading.value = false

                }
                is Resource.Error -> {
                    loadError.value = result.message!!
                    isLoading.value = false
                }
            }
        }
    }

}

