package com.setser.testapp.savedcourses

class SavedCourses(val savedCoursesService: SavedCoursesService) {

    fun getItems(): List<SavedCourse> {
        return savedCoursesService.getCourses().toMutableList()
                .map { title -> SavedCourse(title) }
    }

    data class SavedCourse(val title: String) {
        override fun toString(): String = title
    }
}
