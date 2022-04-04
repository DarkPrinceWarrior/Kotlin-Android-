package com.example.surveyapp.repository

import com.example.surveyapp.remote.responses.Role.Role
import javax.inject.Inject
import com.example.surveyapp.remote.SimpleAPI
import com.example.surveyapp.remote.responses.QuestBalls.QuestionBalls
import com.example.surveyapp.remote.responses.Quests.Quests
import com.example.surveyapp.remote.responses.Role.Roles
import com.example.surveyapp.remote.responses.User_answer.user_answersItem
import com.example.surveyapp.remote.responses.User_result.user_resultsItem
import com.example.surveyapp.remote.responses.Users.User
import com.example.surveyapp.remote.responses.Users.Users
import com.example.surveyapp.remote.responses.attempt.attempts
import com.example.surveyapp.remote.responses.attempt.attemptsItem
import com.example.surveyapp.remote.responses.userResults.Results
import com.example.surveyapp.remote.responses.userResults.UserResults
import com.example.surveyapp.util.Resource
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.scopes.ActivityScoped
import retrofit2.Response

@ActivityScoped
class Repository @Inject constructor(
    private val api: SimpleAPI
){

    // check user in firebase
    fun getUser(): String {

        val auth = FirebaseAuth.getInstance()
        val currentuser = auth.currentUser
        var email = ""
        if (currentuser != null) {

            if(!currentuser.isAnonymous){

                email = auth.currentUser?.email.toString()


                return email
            }
            else{

                return "None"

            }

        }
        return "None"
    }



    suspend fun getUser(email: String): Resource<User> {


        val response = try{
            api.getUser(email)
        }catch(e:Exception){
            return Resource.Error("Error occured")
        }
        return Resource.Success(response)
    }


    suspend fun getAllRoles(): Resource<Roles> {

        val response = try {
            api.getAllRoles()
        } catch(e: Exception) {
            return Resource.Error("An unknown error occured.")
        }
        return Resource.Success(response)

    }

        suspend fun getUserAttempts(): Resource<attempts> {

            val response = try{
                api.getUsersAttempts()
            }catch(e:Exception){
                return Resource.Error("Error occured")
            }
            return Resource.Success(response)
        }


    suspend fun getQuests(): Resource<Quests> {


        val response = try{
            api.getQuests()
        }catch(e:Exception){
            return Resource.Error("Error occured")
        }
        return Resource.Success(response)
    }

    suspend fun getUserResult(id:Int): Resource<UserResults> {
        val response = try{
            api.getUserResult(id)
        }catch(e:Exception){
            return Resource.Error("Error occured")
        }
        return Resource.Success(response)
    }





    suspend fun getQuestBalls(): Resource<QuestionBalls> {

        val response = try{
            api.getQuestBalls()
        }catch(e:Exception){
            return Resource.Error("Error occured")
        }
        return Resource.Success(response)
    }

    suspend fun postNewUser(email: String,
                            login: String,
                            gender: String,
                            age: Int,
                            role_id: Int): Response<User> {

       return api.postNewUser(email,login,gender,age,role_id)
    }

    suspend fun updateUser(
        email: String,
        current_email:String,
        login: String,
        gender: String,
        age: Int,
    ): Response<User> {

        return api.putUser(email,current_email,login,gender,age)
    }


    suspend fun postNewUserAttempt(attempt_number: Int,
                                   userId: Int,
                                   passing_time: String): Response<attemptsItem> {

        return api.postNewUserAttempt(attempt_number,userId,passing_time)
    }


    suspend fun postNewUserResult(involvement: Int,
                                  control: Int,
                                  risk_taking: Int,
                                  attemptId: Int): Response<user_resultsItem> {

        return api.postNewUserResult(involvement,control,risk_taking,attemptId)
    }

    suspend fun postNewUserAnswer(choiceId: Int,
                                  attemptId: Int): Response<user_answersItem> {

        return api.postNewUserAnswer(choiceId,attemptId)
    }



    // Для attempt
    suspend fun post_deleteAttempt(user_id: Int): Response<attemptsItem> {


        return api.post_deleteAttempt(user_id)
    }
    suspend fun post_UpdateAttempt(user_id: Int,
                                   passing_time: String): Response<attemptsItem> {

        val attempt = attemptsItem(0,0,passing_time,user_id)
        return api.post_UpdateAttempt(user_id,attempt)
    }

    suspend fun post_UpdateAnswer(choiceId: Int, new_choice: Int, attemptId: Int): Response<user_answersItem> {
        val answer = user_answersItem(choiceId,attemptId)
        return api.post_UpdateAnswer(new_choice,answer)
    }


    suspend fun post_deleteUser(email: String): Response<User> {


        return api.post_deleteUser(email)
    }


    suspend fun getAllUsers(): Resource<Users> {
        val response = try {
            api.getAllUsers()
        } catch(e: Exception) {
            return Resource.Error("An unknown error occured.")
        }
        return Resource.Success(response)
    }

    suspend fun getAllResults(): Resource<Results> {
        val response = try {
            api.getAllResults()
        } catch(e: Exception) {
            return Resource.Error("An unknown error occured.")
        }
        return Resource.Success(response)
    }


}