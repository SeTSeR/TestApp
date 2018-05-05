package com.setser.testapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.setser.testapp.R.id.app_bar_search
import com.setser.testapp.savedcourses.SavedCourseFragment
import com.setser.testapp.savedcourses.SavedCourses

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
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            app_bar_search -> false
            else -> super.onOptionsItemSelected(item)
        }
    }
}
