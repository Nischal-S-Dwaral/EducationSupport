package com.example.educationsupport.constants

import com.example.educationsupport.model.CourseSearch

object LearnerListConstants {

    fun getLearnerList() : ArrayList<CourseSearch> {

        val courseList = ArrayList<CourseSearch>()
        courseList.add(CourseSearch(1, "Nischal", "Lname"))
        courseList.add(CourseSearch(2, "Sid", "Lname"))
        courseList.add(CourseSearch(3, "Snehal", "Lname"))
        courseList.add(CourseSearch(4, "Adeeba", "Lname"))
        courseList.add(CourseSearch(5, "Android Fragments", "Udemy"))
        courseList.add(CourseSearch(6, "Java Basics", "Byjus"))
        courseList.add(CourseSearch(7, "Java Spring Boot", "Byjus"))
        courseList.add(CourseSearch(8, "Java EE", "UoS"))
        courseList.add(CourseSearch(9, "Java Hibernate", "UoS"))
        courseList.add(CourseSearch(10, "JavaScript Basics", "Oxford"))
        courseList.add(CourseSearch(11, "JavaScript Advanced", "Cambridge"))
        courseList.add(CourseSearch(12, "React", "UoS"))
        courseList.add(CourseSearch(13, "React Redux", "UoS"))

        return courseList
    }
}