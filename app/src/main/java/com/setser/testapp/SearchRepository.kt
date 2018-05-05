package com.setser.testapp

import com.setser.testapp.api.StepikApiService
import com.setser.testapp.model.Result
import io.reactivex.Observable

object SearchRepositoryProvider {
    fun provideSearchRepository(): SearchRepository {
        return SearchRepository(StepikApiService.create())
    }
}

class SearchRepository(val apiService: StepikApiService) {
    fun searchCourses(page: Int, query: String): Observable<Result> {
        return apiService.search(page, query)
    }
}