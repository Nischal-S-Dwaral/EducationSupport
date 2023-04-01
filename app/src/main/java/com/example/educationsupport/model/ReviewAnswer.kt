package com.example.educationsupport.model

data class ReviewAnswer(

    val id: Int,
    val quizTitle: String,
    val quizScore: String,
    val answeredQuestion: List<AnsweredQuestion>
)
