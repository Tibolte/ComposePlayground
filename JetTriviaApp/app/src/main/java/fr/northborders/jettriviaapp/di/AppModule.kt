package fr.northborders.jettriviaapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fr.northborders.jettriviaapp.network.QuestionApi
import fr.northborders.jettriviaapp.repository.QuestionRepository
import fr.northborders.jettriviaapp.util.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideQuestionApi(): QuestionApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuestionApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(questionApi: QuestionApi): QuestionRepository = QuestionRepository(questionApi)
}