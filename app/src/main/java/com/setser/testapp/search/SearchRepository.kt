package com.setser.testapp.search

import com.setser.testapp.api.StepikApiService
import io.reactivex.Observable

object SearchRepositoryProvider {
    fun provideSearchRepository(): SearchRepository {
        return SearchRepository(StepikApiService.create())
    }
}

class SearchRepository(private val apiService: StepikApiService) {
    fun searchCourses(page: Int, query: String): Observable<Result> {
        return apiService.search(query, page)
    }
}