package com.example.educationsupport.constants

import com.example.educationsupport.model.Quiz

object  QuizListConstants {

    fun getQuizList() : ArrayList<Quiz> {
        val quiz1 = Quiz(
            1,
            "1",
            "Quiz-1",
            "This is Quiz-1",
            true,
            80,
             LearnerConstants.getLearnerList()
        )

        val quiz2 = Quiz(
            2,
            "1",
            "Quiz-2",
            "This is Quiz-2",
            true,
            45,
            LearnerConstants.getLearnerList()
        )

        val quiz3 = Quiz(
            1,
            "1",
            "Quiz-3",
            "This is Quiz-3",
            true,
            68,
            LearnerConstants.getLearnerList()
        )

        val quiz4 = Quiz(
            4,
            "1",
            "Quiz-4",
            "This is Quiz-4",
            false,
            0,
            LearnerConstants.getLearnerList()
        )

        val quiz5 = Quiz(
            1,
            "1",
            "Quiz-5",
            "This is Quiz-5",
            false,
            0,
            LearnerConstants.getLearnerList()
        )

        val quiz6 = Quiz(
            1,
            "1",
            "Quiz-6",
            "This is Quiz-6",
            false,
            0,
            LearnerConstants.getLearnerList()
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

    fun getQuizListById(i: String): List<Quiz> {
        val quizList = this.getQuizList();
        val courseQuizList = ArrayList<Quiz>()
        for(ql in quizList){
            if(ql.courseId == i){
                courseQuizList.add(ql);
            }
        }
        return courseQuizList;
    }

    fun getQuizById(quizId: Int): Quiz? {
        val quizList = this.getQuizList();
        var quz: Quiz? = null
        for(q in quizList){
            if(q.id == quizId) {
                quz = q
            }
        }
        return quz;
    }
}