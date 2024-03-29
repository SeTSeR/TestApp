package com.setser.testapp.savedcourses

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.setser.testapp.R


import com.setser.testapp.savedcourses.SavedCourseFragment.OnListFragmentInteractionListener
import com.setser.testapp.savedcourses.SavedCourses.SavedCourse

import kotlinx.android.synthetic.main.fragment_savedcourse.view.*

/**
 * [RecyclerView.Adapter] that can display a [SavedCourse] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class SavedCourseRecyclerViewAdapter(
        private val mValues: MutableList<SavedCourse>,
        private val mListener: OnListFragmentInteractionListener?)
    : RecyclerView.Adapter<SavedCourseRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as SavedCourse
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_savedcourse, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mContentView.text = item.title

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }


    override fun getItemCount(): Int = mValues.size

    fun updateItems(items: List<SavedCourse>) {
        mValues.clear()
        mValues.addAll(items)
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mContentView: TextView = mView.content

        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }
    }
}
