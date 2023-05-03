package com.example.educationsupport.model

data class QuestionModel(
    val question: String,
    val optionOne: String,
    val optionTwo: String,
    val optionThree: String,
    val optionFour: String,
    val correctAnswer: List<Int>
)
