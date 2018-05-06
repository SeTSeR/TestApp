package com.setser.testapp.search

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.setser.testapp.R


import com.setser.testapp.search.SearchResultFragment.OnListFragmentInteractionListener

import kotlinx.android.synthetic.main.fragment_searchresult.view.*

class SearchResultRecyclerViewAdapter(
        private val mListener: OnListFragmentInteractionListener?)
    : RecyclerView.Adapter<SearchResultRecyclerViewAdapter.ViewHolder>() {

    private val mValues: MutableList<Course>
    private var mOnClickListener: View.OnClickListener

    var allElementsLoaded: Boolean
        private set

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Course
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onListFragmentInteraction(item)
        }
        mValues = ArrayList()
        allElementsLoaded = false
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_searchresult, parent, false)
        return ViewHolder(view)
    }

    fun setOnListFragmentInteractionListener(mListener: OnListFragmentInteractionListener?) {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as? Course
            mListener?.onListFragmentInteraction(item)
        }
    }

    fun addNewItems(items: List<Course>) {
        if (items.isEmpty()) {
            allElementsLoaded = true
            return
        }
        mValues.addAll(items)
    }

    fun isEmpty(): Boolean {
        return itemCount == 0
    }

    override fun getItemId(position: Int): Long {
        return mValues[position].id
    }

    override fun getItemCount(): Int = mValues.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mContentView.text = mValues[position].course_title
        with(holder.mView) {
            tag = mValues[position]
            setOnClickListener(mOnClickListener)
        }
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mContentView: TextView = mView.content

        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }
    }
}
