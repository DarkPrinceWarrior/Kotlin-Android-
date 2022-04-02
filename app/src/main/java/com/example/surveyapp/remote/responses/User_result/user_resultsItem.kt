package com.example.surveyapp.remote.responses.User_result

data class user_resultsItem(

    val General_result: Int,
    val attemptId: Int,
    val control: Int,
    val involvement: Int,
    val risk_taking: Int
)