package com.example.surveyapp.remote.responses.Role

import com.example.surveyapp.remote.responses.attempt.attemptsItem

data class Role(
    val id:Int,
    val name: String
)

class Roles : ArrayList<Role>()
