package com.example.surveyapp.remote

import com.example.surveyapp.remote.HttpRoutes.BASE_URL
import com.example.surveyapp.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RetrofitInstance {

    @Singleton
    @Provides
    fun providePokemonRepository(
        api: SimpleAPI
    ) = Repository(api)

    @Singleton
    @Provides
    fun providePokeApi(): SimpleAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(SimpleAPI::class.java)
    }

}