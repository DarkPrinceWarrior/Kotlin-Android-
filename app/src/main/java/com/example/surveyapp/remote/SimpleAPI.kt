package com.example.surveyapp.remote
import com.example.surveyapp.remote.responses.QuestBalls.QuestionBalls
import com.example.surveyapp.remote.responses.Quests.Quests
import com.example.surveyapp.remote.responses.Quests.QuestsItem
import com.example.surveyapp.remote.responses.Role.Role
import com.example.surveyapp.remote.responses.Role.Roles
import com.example.surveyapp.remote.responses.User_answer.user_answersItem
import com.example.surveyapp.remote.responses.User_result.user_resultsItem
import com.example.surveyapp.remote.responses.Users.User
import com.example.surveyapp.remote.responses.Users.Users
import com.example.surveyapp.remote.responses.attempt.attempts
import com.example.surveyapp.remote.responses.attempt.attemptsItem
import com.example.surveyapp.remote.responses.userResults.Results
import com.example.surveyapp.remote.responses.userResults.UserResults
import retrofit2.Response
import retrofit2.http.*


interface SimpleAPI {

    @GET("User/{email}")
    suspend fun getUser(
       @Path("email") email: String
    ): User

    @GET("Roles/{user_id}")
    suspend fun get_Role(
        @Path("user_id") user_id: Int
    ): Role

    @GET("Roles")
    suspend fun getAllRoles(
    ): Roles

    // get all users for stat page for researcher
    @GET("Users/Results")
    suspend fun getAllResults(
    ): Results

    @GET("Users")
    suspend fun getAllUsers(
    ): Users

    @GET("Quest/{id}")
    suspend fun getQuest(
        @Path("id") id: Int
    ): QuestsItem




    @GET("Quests")
    suspend fun getQuests(
    ): Quests

    @GET("Choices")
    suspend fun getQuestBalls(
    ): QuestionBalls

    @GET("Users/Attempts")
    suspend fun getUsersAttempts(
    ): attempts

    @GET("User/Result/{id}")
    suspend fun getUserResult(
        @Path("id") id: Int
    ): UserResults


    @FormUrlEncoded
    @POST("Users")
    suspend fun postNewUser(
        @Field("email") email: String,
        @Field("login") login: String,
        @Field("gender") gender: String,
        @Field("age") age: Int,
        @Field("role_id") role_id: Int
    ): Response<User>


    @FormUrlEncoded
    @PUT("User/{email}")
    suspend fun putUser(
        @Path("email") email: String,
        @Field("current_email") current_email: String,
        @Field("login") login: String,
        @Field("gender") gender: String,
        @Field("age") age: Int
    ): Response<User>



    @FormUrlEncoded
    @POST("Users/Attempts")
    suspend fun postNewUserAttempt(
        @Field("attempt_number") attempt_number: Int,
        @Field("userId") userId: Int,
        @Field("passing_time") passing_time: String
    ): Response<attemptsItem>

    @FormUrlEncoded
    @POST("Users/Results")
    suspend fun postNewUserResult(
        @Field("involvement") involvement: Int,
        @Field("control") control: Int,
        @Field("risk_taking") risk_taking: Int,
        @Field("attemptId") attemptId: Int
    ): Response<user_resultsItem>


    @FormUrlEncoded
    @POST("Users/Answers")
    suspend fun postNewUserAnswer(
        @Field("choiceId") choiceId: Int,
        @Field("attemptId") attemptId: Int,
    ): Response<user_answersItem>


    // Для attempt
    @DELETE("User/Attempt/{user_id}")
    suspend fun post_deleteAttempt(
        @Path("user_id") user_id: Int
    ): Response<attemptsItem>


    @PATCH ("User/Attempt/{user_id}")
    suspend fun post_UpdateAttempt(
        @Path("user_id") user_id: Int,
        @Body attempt: attemptsItem
    ): Response<attemptsItem>

    @PATCH ("User/Answer/{new_choice}")
    suspend fun post_UpdateAnswer(
        @Path("new_choice") new_choice: Int,
        @Body answer: user_answersItem
    ): Response<user_answersItem>

    @GET("User/{email}")
    suspend fun get_User(
        @Path("email") email: String
    ): User

    // Для attempt
    @DELETE("User/{email}")
    suspend fun post_deleteUser(
        @Path("email") email: String
    ): Response<User>

}


