package com.example.surveyapp.remote.responses.attempt

data class attemptsItem(

    val id: Int,
    val attempt_number: Int,
    val passing_time: String,
    val userId: Int
)