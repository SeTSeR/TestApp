package com.setser.testapp.api

import com.setser.testapp.search.Result
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface StepikApiService {

    @GET("/api/search-results")
    fun search(@Query("query") query: String,
               @Query("page") page: Int): Observable<Result>

    companion object Factory {
        fun create(): StepikApiService {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor(logging)
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://stepik.org/")
                    .client(httpClient.build())
                    .build()

            return retrofit.create(StepikApiService::class.java)
        }
    }
}