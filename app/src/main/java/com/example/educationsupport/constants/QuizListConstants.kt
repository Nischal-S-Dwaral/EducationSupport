package com.example.educationsupport.constants

import com.example.educationsupport.model.Quiz

object QuizListConstants {

    fun getQuizList() : ArrayList<Quiz> {
        val quiz1 = Quiz(
            1,
            "Quiz-1",
            "This is Quiz-1",
            true,
            80
        )

        val quiz2 = Quiz(
            2,
            "Quiz-2",
            "This is Quiz-2",
            true,
            45
        )

        val quiz3 = Quiz(
            1,
            "Quiz-3",
            "This is Quiz-3",
            true,
            68
        )

        val quiz4 = Quiz(
            4,
            "Quiz-4",
            "This is Quiz-4",
            false,
            0
        )

        val quiz5 = Quiz(
            1,
            "Quiz-5",
            "This is Quiz-5",
            false,
            0
        )

        val quiz6 = Quiz(
            1,
            "Quiz-6",
            "This is Quiz-6",
            false,
            0
        )

        val quizList = ArrayList<Quiz>()
        quizList.add(quiz1)
        quizList.add(quiz2)
        quizList.add(quiz3)
        quizList.add(quiz4)
        quizList.add(quiz5)
        quizList.add(quiz6)

        return quizList
    }
}