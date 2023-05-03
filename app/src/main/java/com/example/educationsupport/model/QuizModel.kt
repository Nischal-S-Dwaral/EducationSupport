package com.example.educationsupport.model

data class QuizModel(
    val id: String,
    val name: String,
    val courseId: String,
    val educatorId: String,
    val questionList: ArrayList<QuestionModel>
)
