package com.example.surveyapp.AppRepresentations.RegisterPage

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
class RegisterViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    fun postNewUserData(email: String,
                                login: String,
                                gender: String,
                                age: Int,
                                role_id: Int){
        viewModelScope.launch{
            repository.postNewUser(email,login,gender,age,role_id)
        }

    }
}