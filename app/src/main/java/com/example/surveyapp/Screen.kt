package com.example.surveyapp

sealed class Screen(val route:String) {

    object PreLunchScreen: Screen("prelunch_screen")
    object MainScreen: Screen("main_screen")
    object RegisterScreen: Screen("register_screen")
    object QuestsScreen: Screen("quests_screen")
    object WelcomeScreen: Screen("welcome_screen")
    object ResetPasswordScreen: Screen("resetPassword_screen")
    object StartTestScreen: Screen("startTest_screen")
    object CheckUserStateAction: Screen("CheckUserState_action")
    object UserDetailsScreen: Screen("UserDetails_screen")
    object StatisticScreen: Screen("statistic_screen")
    object UserStatisticScreen: Screen("user_statistic_screen")
    object UserEditScreen: Screen("UserEdit_screen")
    object ResultTestScreen: Screen("resultTest_screen")
    object ChangeLanguageScreen: Screen("changeLanguage_screen")


    // screens for researcher
    object AdvancedStatScreen: Screen("advancedStat_screen")
    object UsersStatScreen: Screen("usersStatScreen_screen")
    object GenderStatScreen: Screen("genderStatScreen_screen")
    object AgeStatScreen: Screen("ageStatScreen_screen")



}