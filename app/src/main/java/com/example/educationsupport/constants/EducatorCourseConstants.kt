package com.example.educationsupport.constants

import com.example.educationsupport.model.Course1
import com.example.educationsupport.model.Quiz

object EducatorCourseConstants {

    fun educatorCourseList() : ArrayList<Course1> {
        val course1 = Course1(
            1,
            "Mobile Android Development",
            "Get started now and become an Android app developer in just 6 weeks! You will learn all relevant Android App development techniques. I can tell you Android development is a lot of fun! This course will make your journey to becoming an Android developer fun as well.",
            ArrayList<Quiz>()
        )

        val course12 = Course1(
            2,
            "Java Development",
            "The course is a whopping 100 hours long.  Perhaps you have looked at the size of the course and are feeling a little overwhelmed at the prospect of finding time to complete it.   Maybe you are wondering if you need to go through it all?",
            ArrayList<Quiz>()
        )

        val course13 = Course1(
            3,
            "Software Security",
            "Welcome this comprehensive Ethical Hacking course! This course assumes you have NO prior knowledge and by the end of it you'll be able to hack systems like black-hat hackers and secure them like security experts!",
            ArrayList<Quiz>()
        )

        val course14 = Course1(
            4,
            "Advanced Database",
            "This course was just completely redone and rebuilt from the ground up, with over 325 brand new videos recorded. The course now uses MySQL 8.x and covers new topics including: Window Functions, Views, and SQL modes.",
            ArrayList<Quiz>()
        )

        val course15 = Course1(
            5,
            "Open Data",
            "Get started now and become an Android app developer in just 6 weeks! You will learn all relevant Android App development techniques. I can tell you Android development is a lot of fun! This course will make your journey to becoming an Android developer fun as well.",
            ArrayList<Quiz>()
        )


        val educatorCourseList1 = ArrayList<Course1>()
        educatorCourseList1.add(course1)
        educatorCourseList1.add(course12)
        educatorCourseList1.add(course13)
        educatorCourseList1.add(course14)
        educatorCourseList1.add(course15)

        return educatorCourseList1;
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

    fun getEducatorCourseById(courseId: Int): Course1? {
        val courses = this.educatorCourseList();
        var crs: Course1? = null ;
        for(c in courses){
            if(c.id == courseId) {
                crs = c
            }
        }
    return crs;
    }
}