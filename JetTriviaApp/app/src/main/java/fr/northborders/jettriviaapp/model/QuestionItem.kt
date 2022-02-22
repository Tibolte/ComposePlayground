package fr.northborders.jettriviaapp.data

data class QuestionItem(
    val question: String,
    val category: String,
    val answer: String,
    val choices: List<String>
)

class Question: ArrayList<QuestionItem>()
