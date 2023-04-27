package com.example.educationsupport.model

data class Learner (
    val id: Int,
    val name: String,
    val quizId: String,
    val score: Int,
    val correctAnswer: Int,
    val totalQuestion: Int
)

