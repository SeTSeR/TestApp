package com.setser.testapp.search

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Toast
import com.setser.testapp.R
import com.setser.testapp.savedcourses.SavedCoursesService

class SearchActivity : AppCompatActivity(), SearchResultFragment.OnListFragmentInteractionListener {

    lateinit var query: String
    private set

    override fun onListFragmentInteraction(item: Course?) {
        if(item != null) {
            val preferences = PreferenceManager.getDefaultSharedPreferences(this)
            val savedCoursesService = SavedCoursesService.create(preferences)
            savedCoursesService.addCourse(item.course_title)
            Toast.makeText(this, "Course successfully added! :)",
                    Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent) {
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        if(Intent.ACTION_SEARCH == intent.action) {
            query = intent.getStringExtra(SearchManager.QUERY)
        }
    }
}
