package com.example.surveyapp.remote.responses.userResults

data class UserResults(
    var id: Int,
    var General_result: Int,
    var attemptId: Int,
    var control: Int,
    var involvement: Int,
    var risk_taking: Int
)