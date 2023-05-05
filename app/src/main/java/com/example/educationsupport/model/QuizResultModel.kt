package com.example.educationsupport.model

data class QuizResultModel(
    val id: String? = null,
    val quizId: String? = null,
    val quizName: String? = null,
    val courseId: String? = null,
    val learnerId: String? = null,
    val correctAnswerScore: Int? = 0,
    val totalQuestionScore: Int? = 0,
    val answeredQuestionList: List<AnsweredQuestion> = emptyList(),
    val learnerName: String?= null
)
