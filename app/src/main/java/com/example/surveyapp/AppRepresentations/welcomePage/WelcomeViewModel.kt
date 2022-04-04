package com.example.surveyapp.AppRepresentations.welcomePage

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.surveyapp.remote.responses.Role.Role
import com.example.surveyapp.remote.responses.Users.User
import com.example.surveyapp.repository.Repository
import com.example.surveyapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class WelcomeViewModel @Inject constructor(private val repository: Repository):
    ViewModel(){


    var UsersList = mutableStateOf<List<User>>(listOf())
    var RolesList = mutableStateOf<List<Role>>(listOf())

    init{
        getAllUsers()
        getAllRoles()
    }

    fun getAllUsers() {
//        Thread.sleep(100)
        viewModelScope.launch {

            val result = repository.getAllUsers()

            when(result) {
                is Resource.Success -> {

                    val Users_Entries = result.data!!.mapIndexed { index, entry ->
                        User(entry.id, entry.email, entry.login,
                            entry.gender, entry.age, entry.role_id)
                    }


                    UsersList.value +=Users_Entries
                }
                is Resource.Error -> {

                }
            }
        }


    }

    fun getAllRoles() {
//        Thread.sleep(100)
        viewModelScope.launch {

            val result = repository.getAllRoles()

            when(result) {
                is Resource.Success -> {

                    val Roles_Entries = result.data!!.mapIndexed { index, entry ->
                        Role(entry.id, entry.name)
                    }


                    RolesList.value +=Roles_Entries
                }
                is Resource.Error -> {

                }
            }
        }


    }


    fun post_deleteUser(email:String){
        viewModelScope.launch {
            repository.post_deleteUser(email)

        }


    }
}