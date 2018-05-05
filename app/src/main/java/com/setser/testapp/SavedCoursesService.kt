package com.setser.testapp

import android.content.SharedPreferences

interface SavedCoursesService {
    fun addCourse(title: String)
    fun getCourses(): Collection<String>

    companion object Factory {
        fun create(sharedPreferences: SharedPreferences): SavedCoursesService {
            return PreferencesSavedCoursesService(sharedPreferences)
        }
    }
}

class PreferencesSavedCoursesService(val sharedPreferences: SharedPreferences) : SavedCoursesService {
    val REC_SAVED = "savedcourses"

    override fun getCourses(): Collection<String> {
        return sharedPreferences.getStringSet(REC_SAVED, LinkedHashSet<String>())
    }

    override fun addCourse(title: String) {
        val courses = sharedPreferences.getStringSet(REC_SAVED, LinkedHashSet<String>())
        courses.add(title)
        val editor = sharedPreferences.edit()
        editor.putStringSet(REC_SAVED, courses)
        editor.apply()
    }
}