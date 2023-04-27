package com.example.educationsupport.constants

import com.example.educationsupport.model.Learner

object LearnerConstants {

    fun getLearnerList() : ArrayList<Learner> {

        val learner1 = Learner(
            1,
            "Learner-1",
            "1",
            80,
            8,
            10
        )

        val learner2 = Learner(
            2,
            "Learner-2",
            "1",
            45,
            5,
            10
        )

        val learner3 = Learner(
            3,
            "Learner-3",
            "1",
            65,
            6,
            10
        )

        val learner4 = Learner(
            4,
            "Learner-4",
            "1",
            25,
            7,
            10
        )

        val learner5 = Learner(
            5,
            "Learner-5",
            "1",
            45,
            9,
            10
        )

        val learnlist = ArrayList<Learner>()
        learnlist.add(learner1)
        learnlist.add(learner2)
        learnlist.add(learner3)
        learnlist.add(learner4)
        learnlist.add(learner5)

        return learnlist
    }
}