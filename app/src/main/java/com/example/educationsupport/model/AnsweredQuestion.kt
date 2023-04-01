package com.example.educationsupport.model

data class AnsweredQuestion(
    val questionNumber: Int,
    val question: String,
    val isCorrect: Boolean,
    val selectedAnswer: String,
    val correctAnswer: String
)
