package com.setser.testapp

import android.app.SearchManager
import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.view.Menu
import com.setser.testapp.R.id.app_bar_search
import com.setser.testapp.savedcourses.SavedCourseFragment
import com.setser.testapp.savedcourses.SavedCourses
import com.setser.testapp.search.SearchActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), SavedCourseFragment.OnListFragmentInteractionListener {
    override fun onListFragmentInteraction(item: SavedCourses.SavedCourse?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(app_bar_search).actionView as? SearchView
        searchView!!.setSearchableInfo(
                searchManager.getSearchableInfo(ComponentName(this, SearchActivity::class.java)))
        return true
    }
}
