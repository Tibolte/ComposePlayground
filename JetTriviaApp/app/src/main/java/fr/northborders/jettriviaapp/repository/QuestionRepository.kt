package fr.northborders.jettriviaapp.repository

import android.util.Log
import fr.northborders.jettriviaapp.data.DataOrException
import fr.northborders.jettriviaapp.data.QuestionItem
import fr.northborders.jettriviaapp.network.QuestionApi
import java.lang.Exception
import javax.inject.Inject

class QuestionRepository @Inject constructor(private val questionApi: QuestionApi) {

    private val dataOrException = DataOrException<ArrayList<QuestionItem>, Boolean, Exception>()

    suspend fun getAllQuestions(): DataOrException<ArrayList<QuestionItem>, Boolean, Exception> {
        try {
            dataOrException.loading = true
            dataOrException.data = questionApi.getAllQuestions()
            if (dataOrException.data.toString().isNotEmpty()) dataOrException.loading = false
        } catch (exception: Exception) {
            dataOrException.e = exception
            dataOrException.loading = false
            Log.e("QuestionRepository", "Error: ${dataOrException.e!!.localizedMessage}")
        }
        return dataOrException
    }
}