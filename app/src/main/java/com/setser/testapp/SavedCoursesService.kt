package com.setser.testapp

import io.reactivex.Observable

interface SavedCoursesService {
    fun addCourse(title: String)
    fun getCourses(): Observable<String>
}