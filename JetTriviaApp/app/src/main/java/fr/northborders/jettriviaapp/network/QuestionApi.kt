package fr.northborders.jettriviaapp.network

import fr.northborders.jettriviaapp.data.Question
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface QuestionApi {
    @GET("world.json")
    suspend fun getAllQuestions(): Question
}