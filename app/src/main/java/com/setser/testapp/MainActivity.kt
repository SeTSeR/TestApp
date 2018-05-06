package com.setser.testapp

import android.app.SearchManager
import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.Menu
import android.widget.Toast
import com.setser.testapp.R.id.app_bar_search
import com.setser.testapp.savedcourses.SavedCourseFragment
import com.setser.testapp.savedcourses.SavedCourses
import com.setser.testapp.savedcourses.SavedCoursesService
import com.setser.testapp.search.SearchActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), SavedCourseFragment.OnListFragmentInteractionListener {
    override fun onListFragmentInteraction(item: SavedCourses.SavedCourse?) {
        if(item != null) {
            val preferences = PreferenceManager.getDefaultSharedPreferences(this)
            val savedCoursesService = SavedCoursesService.create(preferences)
            savedCoursesService.removeCourse(item.title)
            Toast.makeText(this,
                    "Course removed successfully :)", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(app_bar_search).actionView as? SearchView
        searchView!!.setSearchableInfo(
                searchManager.getSearchableInfo(ComponentName(this, SearchActivity::class.java)))
        return true
    }
}
