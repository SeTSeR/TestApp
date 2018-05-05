package com.setser.testapp.search

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.setser.testapp.R


import com.setser.testapp.search.SearchResultFragment.OnListFragmentInteractionListener
import com.setser.testapp.dummy.DummyContent.DummyItem

import kotlinx.android.synthetic.main.fragment_searchresult.view.*

/**
 * [RecyclerView.Adapter] that can display a [DummyItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class SearchResultRecyclerViewAdapter(
        private val mValues: List<DummyItem>,
        private val mListener: OnListFragmentInteractionListener?)
    : RecyclerView.Adapter<SearchResultRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener
    private val elements: MutableList<DummyItem>

    private var allElementsLoaded: Boolean

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as DummyItem
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onListFragmentInteraction(item)
        }
        elements = ArrayList()
        allElementsLoaded = false
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_searchresult, parent, false)
        return ViewHolder(view)
    }

    fun addNewItems(items: List<DummyItem>) {
        if(items.isEmpty()) {
            allElementsLoaded = true
            return
        }
        elements.addAll(items)
    }

    override fun getItemId(position: Int): Long {
        return elements[position].id
    }

    override fun getItemCount(): Int = mValues.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        onBindTextHolder(holder, position)
    }

    fun onBindTextHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as ViewHolder
        viewHolder.mContentView.text = elements[position].content
        with(holder.mView) {
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
