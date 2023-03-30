package com.example.educationsupport.constants

import com.example.educationsupport.model.Course

object ViewCourseConstants {

    fun getCourse() : Course {
        return Course(
            1,
            "Mobile Android Development",
            "Get started now and become an Android app developer in just 6 weeks! You will learn all relevant Android App development techniques. I can tell you Android development is a lot of fun! This course will make your journey to becoming an Android developer fun as well.",
            QuizListConstants.getQuizList()
        )
    }
}