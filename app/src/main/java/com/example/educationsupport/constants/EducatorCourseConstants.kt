package com.example.educationsupport.constants

import com.example.educationsupport.model.Course
import com.example.educationsupport.model.Quiz

object EducatorCourseConstants {

    fun educatorCourseList() : ArrayList<Course> {
        val course1 = Course(
            1,
            "Mobile Android Development",
            "Get started now and become an Android app developer in just 6 weeks! You will learn all relevant Android App development techniques. I can tell you Android development is a lot of fun! This course will make your journey to becoming an Android developer fun as well.",
            ArrayList<Quiz>()
        )

        val course2 = Course(
            2,
            "Java Development",
            "The course is a whopping 100 hours long.  Perhaps you have looked at the size of the course and are feeling a little overwhelmed at the prospect of finding time to complete it.   Maybe you are wondering if you need to go through it all?",
            ArrayList<Quiz>()
        )

        val course3 = Course(
            3,
            "Software Security",
            "Welcome this comprehensive Ethical Hacking course! This course assumes you have NO prior knowledge and by the end of it you'll be able to hack systems like black-hat hackers and secure them like security experts!",
            ArrayList<Quiz>()
        )

        val course4 = Course(
            4,
            "Advanced Database",
            "This course was just completely redone and rebuilt from the ground up, with over 325 brand new videos recorded. The course now uses MySQL 8.x and covers new topics including: Window Functions, Views, and SQL modes.",
            ArrayList<Quiz>()
        )

        val course5 = Course(
            5,
            "Open Data",
            "Get started now and become an Android app developer in just 6 weeks! You will learn all relevant Android App development techniques. I can tell you Android development is a lot of fun! This course will make your journey to becoming an Android developer fun as well.",
            ArrayList<Quiz>()
        )


        val educatorCourseList = ArrayList<Course>()
        educatorCourseList.add(course1)
        educatorCourseList.add(course2)
        educatorCourseList.add(course3)
        educatorCourseList.add(course4)
        educatorCourseList.add(course5)

        return educatorCourseList;
    }

//    fun getEducatorCourse() : ArrayList<Course> {
//        return Course([{
//            1,
//            "Mobile Android Development",
//            "Get started now and become an Android app developer in just 6 weeks! You will learn all relevant Android App development techniques. I can tell you Android development is a lot of fun! This course will make your journey to becoming an Android developer fun as well.",
//            QuizListConstants.getQuizListById("1")
//        }]
//        )
//    }

    fun getEducatorCourseById(courseId: Int): Course? {
        val courses = this.educatorCourseList();
        var crs: Course? = null ;
        for(c in courses){
            if(c.id == courseId) {
                crs = c
            }
        }
    return crs;
    }
}