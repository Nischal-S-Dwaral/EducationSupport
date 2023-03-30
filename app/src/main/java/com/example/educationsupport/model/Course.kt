package com.example.educationsupport.model

data class Course(
    val id: Int,
    val name: String,
    val description: String,
    val quizList: List<Quiz>
)
