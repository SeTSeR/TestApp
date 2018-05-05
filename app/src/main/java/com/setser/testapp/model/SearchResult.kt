package com.setser.testapp.model

import com.google.gson.annotations.SerializedName

data class Page (
    val page: Int,
    val has_next: Boolean,
    val has_previous: Boolean
)

data class Course (
    val id: Long,
    val title: String,
    val course: Long,
    val course_owner: Long,
    val course_authors: List<Long>,
    val type: String,
    val score: Double
)

data class Result (val meta: Page,
                   @SerializedName("search-results")
                   val search_results: List<Course>)