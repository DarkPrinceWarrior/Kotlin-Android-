package com.example.surveyapp.AppRepresentations.UserPage.UserEditPage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.surveyapp.remote.responses.Users.User
import com.example.surveyapp.repository.Repository
import com.example.surveyapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject



@HiltViewModel
class UserEditViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    fun UpdateUserData(email: String,
                               current_email: String,
                                login: String,
                                gender: String,
                                age: Int) {

        viewModelScope.launch {
            repository.updateUser(email,current_email,login,gender,age)
        }

    }

}