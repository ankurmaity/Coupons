package com.ankur.mobiuscoupons.request

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 *
 * @author ankur
 *
 */
class RetrofitClient {
    private val BASE_URL = "https://run.mocky.io/"

    private val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()

    private val logging: HttpLoggingInterceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    private val retrofitBuilder: Retrofit.Builder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())

    private var retrofit: Retrofit = retrofitBuilder.build()

    private var retrofitApi: RetrofitApi? = null

    fun getRetrofitApi(): RetrofitApi? {
        if (!httpClient.interceptors().contains(logging)) {
            httpClient.addInterceptor(logging)
            retrofitBuilder.client(httpClient.build())
            retrofit = retrofitBuilder.build()
        }
        if (retrofitApi == null) {
            retrofitApi = retrofit.create(RetrofitApi::class.java)
        }
        return retrofitApi
    }
}