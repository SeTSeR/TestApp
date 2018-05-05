package com.setser.testapp.search

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.setser.testapp.BackgroundExecutor
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PaginationTool {
    val ITEMS_PER_PAGE = 20
    val MAX_ATTEMPTS = 3

    fun <T> paging(recyclerView: RecyclerView, pagingListener: PagingListener<T>): Observable<List<T>>? {
        return getScrollObservable(recyclerView)
                .subscribeOn(AndroidSchedulers.mainThread())
                .distinctUntilChanged()
                .observeOn(Schedulers.from(BackgroundExecutor.getSafeBackgroundExecutor()))
                .switchMap({
                    offset -> getPagingObservable(pagingListener,
                        pagingListener.onNextPage(offset), 0,
                        offset)
                })
    }

    private fun getScrollObservable(recyclerView: RecyclerView): Observable<Int> {
        return Observable.create({subscriber ->
            val onScrollListener = object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if(!subscriber.isDisposed) {
                        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                        val position = layoutManager.findLastVisibleItemPosition()
                        val updatePosition = recyclerView.adapter.itemCount - 1 - ITEMS_PER_PAGE/2
                        if(position >= updatePosition) {
                            subscriber.onNext(recyclerView.adapter.itemCount)
                        }
                    }
                }
            }
        })
    }

    fun <T> getPagingObservable(pagingListener: PagingListener<T>,
                                observable: Observable<List<T>>,
                                attemptToRetry: Int,
                                offset: Int): Observable<List<T>> {
        return observable.onErrorResumeNext({throwable: Throwable ->
            if(attemptToRetry < MAX_ATTEMPTS) {
                getPagingObservable(pagingListener,
                        pagingListener.onNextPage(offset),
                        attemptToRetry + 1,
                        offset)
            } else {
                Observable.empty<List<T>>()
            }
        })
    }
}

interface PagingListener<T> {
    fun onNextPage(offset: Int): Observable<List<T>>
}