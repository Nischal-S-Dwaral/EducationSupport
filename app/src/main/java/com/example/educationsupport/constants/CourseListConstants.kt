package com.example.educationsupport.constants

import com.example.educationsupport.model.CourseSearch

object CourseListConstants {

    fun getCourseList() : ArrayList<CourseSearch> {

        val courseList = ArrayList<CourseSearch>()
        courseList.add(CourseSearch(1, "Android Part-1", "Udemy"))
        courseList.add(CourseSearch(2, "Android Part-2", "Udemy"))
        courseList.add(CourseSearch(3, "Android Scroll view", "Udemy"))
        courseList.add(CourseSearch(4, "Android Navigation", "Udemy"))
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