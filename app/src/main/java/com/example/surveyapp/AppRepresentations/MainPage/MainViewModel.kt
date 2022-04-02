package com.example.surveyapp.AppRepresentations.MainPage

import androidx.lifecycle.ViewModel
import com.example.surveyapp.remote.responses.Users.User
import com.example.surveyapp.repository.Repository
import com.example.surveyapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject



@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository):ViewModel() {


}