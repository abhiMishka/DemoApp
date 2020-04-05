package com.develop.basicarchitecture.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

class ApiClient {

    companion object {
        const val BASE_URL = "https://developers.zomato.com/"
        const val USER_KEY = "user-key"

        private val loggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY

        }
        private val okHttpClient = OkHttpClient().newBuilder()
            .connectTimeout(2, TimeUnit.MINUTES)
            .readTimeout(2, TimeUnit.MINUTES)
            .writeTimeout(2, TimeUnit.MINUTES)
            .retryOnConnectionFailure(true)
            .addInterceptor(ResponseInterceptor())
            .addInterceptor(loggingInterceptor)
            .build()


        var retrofit: Retrofit? = null

        fun getClient(): Retrofit {
            when (retrofit) {
                null -> retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .client(okHttpClient)
                    .build()
            }
            return retrofit as Retrofit
        }
    }

    private class ResponseInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            val original = chain.request()
            val builder = original.newBuilder().method(original.method, original.body)
                .addHeader(USER_KEY,"93f0cea5b8e3324ba476b0ebb652c29d")
            val response = chain.proceed(builder.build())
            val contentType = response.body?.contentType()
            val responseBody = ResponseBody.create(contentType, response.body?.string() ?: "")
            return response.newBuilder().body(responseBody).build()

        }
    }
}