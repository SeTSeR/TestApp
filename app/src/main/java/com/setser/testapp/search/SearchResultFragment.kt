package com.setser.testapp.search

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.setser.testapp.R

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [SearchResultFragment.OnListFragmentInteractionListener] interface.
 */
class SearchResultFragment : Fragment() {

    private var listener: OnListFragmentInteractionListener? = null

    private lateinit var pagingSubscription: Disposable
    private lateinit var recyclerViewAdapter: SearchResultRecyclerViewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_searchresult_list, container, false)
        retainInstance = true

        // Set the adapter
        if (view is RecyclerView) {
            val linearLayoutManager = LinearLayoutManager(context)
            with(view) {
                layoutManager = linearLayoutManager
                layoutManager.supportsPredictiveItemAnimations()
                adapter = recyclerViewAdapter
                isSaveEnabled = true
                addItemDecoration(DividerItemDecoration(context, linearLayoutManager.orientation))
            }
            if (!recyclerViewAdapter.allElementsLoaded) {
                var fetchedPageNum = 0
                pagingSubscription = PaginationTool
                        .paging(view, {
                            val searchRepository = SearchRepositoryProvider.provideSearchRepository()
                            val parentActivity = activity as? SearchActivity
                            val query = parentActivity?.query
                            if (query == null) Observable.empty<List<Course>>()
                            else {
                                fetchedPageNum += 1
                                searchRepository.searchCourses(fetchedPageNum - 1, query)
                                        .map { result ->
                                            result.search_results.filter { item ->
                                                item.target_type == "course"
                                            }
                                        }
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ items ->
                            Log.d("Arrived", "${items.size} items")
                            recyclerViewAdapter.addNewItems(items)
                            recyclerViewAdapter.notifyItemInserted(
                                    recyclerViewAdapter.itemCount - items.size
                            )
                        }, { error ->
                            Toast.makeText(activity, "Could not download list of courses :(",
                                    Toast.LENGTH_SHORT).show()
                            error.printStackTrace()
                        })
            }
        }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            listener = context
            if (!this::recyclerViewAdapter.isInitialized) {
                recyclerViewAdapter = SearchResultRecyclerViewAdapter(listener)
                recyclerViewAdapter.setHasStableIds(true)
            }
            recyclerViewAdapter.setOnListFragmentInteractionListener(listener)
        } else {
            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
        recyclerViewAdapter.setOnListFragmentInteractionListener(null)
    }

    override fun onDestroyView() {
        if (!pagingSubscription.isDisposed) {
            pagingSubscription.dispose()
        }
        super.onDestroyView()
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
        fun onListFragmentInteraction(item: Course?)
    }
}
