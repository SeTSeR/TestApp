package com.setser.testapp.savedcourses

import android.content.Context
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.setser.testapp.R
import com.setser.testapp.savedcourses.SavedCourses.SavedCourse

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [SavedCourseFragment.OnListFragmentInteractionListener] interface.
 */
class SavedCourseFragment : Fragment() {

    private var listener: OnListFragmentInteractionListener? = null

    private lateinit var recyclerViewAdapter: SavedCourseRecyclerViewAdapter
    private lateinit var savedCourses: SavedCourses

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_savedcourse_list, container, false)
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity)
        savedCourses = SavedCourses(SavedCoursesService.create(sharedPreferences))

        // Set the adapter
        if (view is RecyclerView) {
            view.isSaveEnabled = true
            val linearLayoutManager = LinearLayoutManager(context)
            recyclerViewAdapter = SavedCourseRecyclerViewAdapter(
                    savedCourses.getItems().toMutableList(), listener)
            with(view) {
                layoutManager = linearLayoutManager
                adapter = recyclerViewAdapter
                addItemDecoration(DividerItemDecoration(context, linearLayoutManager.orientation))
            }
        }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        listener = null
        super.onDetach()
    }

    override fun onResume() {
        recyclerViewAdapter.updateItems(savedCourses.getItems())
        recyclerViewAdapter.notifyDataSetChanged()
        super.onResume()
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson
     * [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnListFragmentInteractionListener {
        fun onListFragmentInteraction(item: SavedCourse?)
    }
}
