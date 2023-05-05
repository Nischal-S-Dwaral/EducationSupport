package com.example.educationsupport.model

data class QuizModel(
    val id: String? = null,
    val name: String? = null,
    val courseId: String? = null,
    val educatorId: String? = null,
    val questionList: List<QuestionModel> = emptyList(),
    val scheduledQuiz: Boolean = false,
    val startDate: String? = null,
    val endDate: String? = null
)