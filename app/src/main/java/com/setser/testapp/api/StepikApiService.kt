package com.setser.testapp.api

import com.setser.testapp.model.Result
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface StepikApiService {

    @GET("/api/search-results")
    fun search(@Query("p") page: Int,
               @Query("q") query: String): Observable<Result>

    companion object Factory {
        fun create(): StepikApiService {
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://stepik.org/")
                    .build()

            return retrofit.create(StepikApiService::class.java)
        }
    }
}