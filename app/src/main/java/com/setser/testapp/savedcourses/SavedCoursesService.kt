package com.setser.testapp.savedcourses

import android.content.SharedPreferences

interface SavedCoursesService {
    fun addCourse(title: String)
    fun removeCourse(title: String)
    fun getCourses(): Collection<String>

    companion object Factory {
        fun create(sharedPreferences: SharedPreferences): SavedCoursesService {
            return PreferencesSavedCoursesService(sharedPreferences)
        }
    }
}

class PreferencesSavedCoursesService(private val sharedPreferences: SharedPreferences) : SavedCoursesService {
    private val saved = "saved_courses"

    override fun getCourses(): Collection<String> {
        return sharedPreferences.getStringSet(saved, LinkedHashSet<String>())
    }

    override fun addCourse(title: String) {
        val courses = sharedPreferences.getStringSet(saved, LinkedHashSet<String>())
        courses.add(title)
        sharedPreferences.edit().putStringSet(saved, courses).apply()
    }

    override fun removeCourse(title: String) {
        val courses = sharedPreferences.getStringSet(saved, LinkedHashSet<String>())
        courses.remove(title)
        sharedPreferences.edit().putStringSet(saved, courses).apply()
    }
}