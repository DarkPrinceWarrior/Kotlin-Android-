package com.example.surveyapp.AppRepresentations.StatPage

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.surveyapp.remote.responses.Users.User
import com.example.surveyapp.remote.responses.attempt.attemptsItem
import com.example.surveyapp.remote.responses.userResults.UserResults
import com.example.surveyapp.repository.Repository
import com.example.surveyapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class StatViewModel @Inject constructor(private val repository: Repository): ViewModel() {


    var userAttempts = mutableStateOf<List<attemptsItem>>(listOf())



    init {
        getAllUserAttempts()
    }

    suspend fun getUserInfo(email: String): Resource<User> {

        if(email!="None"){
            // поменять
            return repository.getUser(email)
        }
        else{
            val user = User(0,"None","None",
                "None", 0,0)

            return Resource.Error("There is no user")
        }

    }


    suspend fun getUserResult(id: Int): Resource<UserResults> {

        return if(id!=0){

            repository.getUserResult(id)
        } else{
            val userResult = UserResults(0,0,
                0,0,0,0)
            Resource.Error("There is no userResult")
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


}
