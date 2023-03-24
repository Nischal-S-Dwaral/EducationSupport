package com.example.educationsupport.model

data class Quiz(
    val id: Int,
    val title: String,
    val description: String,
    val isCompleted: Boolean,
    val score: Int
)