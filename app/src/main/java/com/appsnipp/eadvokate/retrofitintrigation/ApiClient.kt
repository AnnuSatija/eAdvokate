package com.appsnipp.eadvokate.retrofitintrigation

import com.appsnipp.eadvokate.utils.Constant
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient() {

    companion object{

        fun getApiClient(username: String, authpassword: String): Retrofit {

            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            val client = OkHttpClient.Builder()
                .readTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .addInterceptor(BasicAuthInterceptor(username, authpassword))
                .build()

            return Retrofit.Builder()
                  .baseUrl(Constant.BaseUrl)
                  .addConverterFactory(GsonConverterFactory.create())
                  .client(client)
                  .build()
        }
    }
}