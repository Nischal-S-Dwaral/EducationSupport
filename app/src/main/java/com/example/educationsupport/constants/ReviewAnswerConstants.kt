package com.example.educationsupport.constants

import com.example.educationsupport.model.AnsweredQuestion
import com.example.educationsupport.model.ReviewAnswer

object ReviewAnswerConstants {

    fun getReviewAnswer() : ReviewAnswer {

        val answeredQuestionList = ArrayList<AnsweredQuestion>()
        answeredQuestionList.add(AnsweredQuestion(
            1,
            "Which is the most populated country in the world?",
            true,
            "India",
            "India"
        ))
        answeredQuestionList.add(AnsweredQuestion(
            2,
            "Which is the most populated country in the world?",
            false,
            "India",
            "China"
        ))
        answeredQuestionList.add(AnsweredQuestion(
            3,
            "Which is the most populated country in the world?",
            false,
            "India",
            "USA"
        ))
        answeredQuestionList.add(AnsweredQuestion(
            4,
            "Which is the most populated country in the world?",
            true,
            "India",
            "India"
        ))
        answeredQuestionList.add(AnsweredQuestion(
            5,
            "Which is the most populated country in the world?",
            false,
            "India",
            "Brazil"
        ))

        return ReviewAnswer(
            1,
            "Quiz Number 1",
            "Your Score: 2 out of 5",
            answeredQuestionList
        )
    }
}