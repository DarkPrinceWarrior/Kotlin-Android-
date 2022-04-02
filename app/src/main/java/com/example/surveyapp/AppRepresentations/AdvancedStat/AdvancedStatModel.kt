package com.example.surveyapp.AppRepresentations.AdvancedStat

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.surveyapp.remote.responses.attempt.attemptsItem
import com.example.surveyapp.remote.responses.userResults.UserResults
import com.example.surveyapp.repository.Repository
import com.example.surveyapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AdvancedStatModel @Inject constructor(private val repository: Repository):
    ViewModel() {

    var attemptsList = mutableStateOf<List<attemptsItem>>(listOf())
    var resultsList = mutableStateOf<List<UserResults>>(listOf())

    var FirstRange = mutableStateOf(0)
    var SecondRange = mutableStateOf(0)
    var ThirdRange = mutableStateOf(0)
    var FourthRange = mutableStateOf(0)
    var FifthRange = mutableStateOf(0)

    var WomenResult = mutableStateOf(0)
    var MenResult = mutableStateOf(0)

    var ActionGender = true



    init {
        get_all_Attempts()
    }

    init {
        get_all_Results()
    }

    init {
        Compute_Age_General_Result()

    }


    fun Compute_Gender_General_Result() {
        if(ActionGender){
            Thread.sleep(100)
            viewModelScope.launch {

                // retrieve users
                val result = repository.getAllUsers()


                when (result) {
                    is Resource.Success -> {
                        result.data?.mapIndexed { _, entry ->

                            if (entry.gender == "Мужчина") {

                                for (attempt in attemptsList.value) {

                                    if (entry.id == attempt.userId) {
                                        for (results in resultsList.value) {

                                            if (attempt.id == results.attemptId) {
                                                MenResult.value += results.General_result
                                            }
                                        }
                                    }

                                }

                            } else if (entry.gender == "Женщина") {
                                for (attempt in attemptsList.value) {

                                    if (entry.id == attempt.userId) {
                                        for (results in resultsList.value) {

                                            if (attempt.id == results.attemptId) {
                                                MenResult.value += results.General_result
                                            }
                                        }
                                    }

                                }
                            }

                        }

                    }


                }

            }
            ActionGender = false
        }

    }


    fun Compute_Age_General_Result() {

        Thread.sleep(100)
        viewModelScope.launch {

            // retrieve users
            val result = repository.getAllUsers()


            when (result) {
                is Resource.Success -> {
                    result.data?.mapIndexed { _, entry ->


                        when (entry.age) {

                            in 1..20 -> {
                                for (attempt in attemptsList.value) {

                                    if (entry.id == attempt.userId) {
                                        for (results in resultsList.value) {

                                            if (attempt.id == results.attemptId) {
                                                FirstRange.value += results.General_result
                                            }
                                        }
                                    }

                                }

                            }
                            in 21..35 -> {
                                for (attempt in attemptsList.value) {

                                    if (entry.id == attempt.userId) {
                                        for (results in resultsList.value) {

                                            if (attempt.id == results.attemptId) {
                                                SecondRange.value += results.General_result
                                            }
                                        }
                                    }

                                }
                            }
                            in 36..45 -> {
                                for (attempt in attemptsList.value) {

                                    if (entry.id == attempt.userId) {
                                        for (results in resultsList.value) {

                                            if (attempt.id == results.attemptId) {
                                                ThirdRange.value += results.General_result
                                            }
                                        }
                                    }

                                }
                            }
                            in 46..55 -> {
                                for (attempt in attemptsList.value) {

                                    if (entry.id == attempt.userId) {
                                        for (results in resultsList.value) {

                                            if (attempt.id == results.attemptId) {
                                                FourthRange.value += results.General_result
                                            }
                                        }
                                    }

                                }
                            }
                            in 56..99 -> {
                                for (attempt in attemptsList.value) {

                                    if (entry.id == attempt.userId) {
                                        for (results in resultsList.value) {

                                            if (attempt.id == results.attemptId) {
                                                FifthRange.value += results.General_result
                                            }
                                        }
                                    }

                                }
                            }

                        }
                    }

                }

            }
        }
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



}