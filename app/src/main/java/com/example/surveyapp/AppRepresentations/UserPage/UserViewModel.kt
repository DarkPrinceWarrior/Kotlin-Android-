package com.example.surveyapp.AppRepresentations.UserPage

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.surveyapp.remote.responses.Quests.QuestsItem
import com.example.surveyapp.remote.responses.Users.User
import com.example.surveyapp.repository.Repository
import com.example.surveyapp.util.Resource

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor(private val repository: Repository):
    ViewModel(){


    suspend fun getUserInfo(email: String): Resource<User> {

        if(email!="None"){

            return repository.getUser(email)
        }
        else{
            val user = User(0,"None","None",
                "None",0,0)

            return Resource.Error("There is no user")
        }


    }

}