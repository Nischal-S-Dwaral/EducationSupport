package com.example.educationsupport.model

data class Question(
    val id: Int,
    val quizId: Int,
    val question: String,
    val optionOne: String,
    val optionTwo: String,
    val optionThree: String,
    val optionFour: String,
    val correctAnswer: Int
)
