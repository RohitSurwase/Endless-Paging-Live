package com.rohitss.newsmvvm.api

import android.util.Log
import com.rohitss.newsmvvm.model.NewsApiResult
import com.rohitss.newsmvvm.utils.API_KEY
import com.rohitss.newsmvvm.utils.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.io.IOException

interface NyNewsApi {
    @Throws(IOException::class)
    @GET("{section}.json?api-key=$API_KEY")
    suspend fun getAllNews(@Path(value = "section") section: String): NewsApiResult


    companion object {
        fun create(): NyNewsApi {
            val logger = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
                override fun log(message: String) {
                    Log.d("NyNewsApi", message)
                }
            })

            logger.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            // Alternate way to add API KEY
            /*val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor(object : Interceptor {
                @Throws(IOException::class)
                override fun intercept(chain: Interceptor.Chain): Response {
                    val original: Request = chain.request()
                    val originalHttpUrl: HttpUrl = original.url
                    val url = originalHttpUrl.newBuilder()
                        .addQueryParameter("api-key", API_KEY)
                        .build()
                    val requestBuilder: Request.Builder = original.newBuilder().url(url)
                    val request: Request = requestBuilder.build()
                    return chain.proceed(request)
                }
            })*/

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NyNewsApi::class.java)
        }
    }
}