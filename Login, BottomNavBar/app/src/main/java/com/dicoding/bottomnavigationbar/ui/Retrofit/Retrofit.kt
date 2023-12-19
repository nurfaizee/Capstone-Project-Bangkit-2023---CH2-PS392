package com.dicoding.bottomnavigationbar.ui.Retrofit

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Retro {
    private val API_KEY = "1c0bceec225a018355a7adcec73356800aa2e2855ec014efc163fea621c6137d8a768745046a25b8" // Replace with your actual API key

    fun getRetroClientInstance(): Retrofit {
        val gson = GsonBuilder().setLenient().create()

        return Retrofit.Builder()
            .baseUrl("http://104.155.145.243:3001/auth/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(createOkHttpClient()) // Optionally, you can customize the OkHttpClient
            .build()
    }
    fun getRetroClientInstanceStunting(): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl("https://dentingmodel1-asuptnnvoa-as.a.run.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

//    private fun createOkHttpClients(): OkHttpClient {
//        return OkHttpClient.Builder()
//            .addInterceptor { chain ->
//                val originalRequest = chain.request()
//                val newUrl = originalRequest.url().newBuilder()
//                    .build()
//
//                val newRequest = originalRequest.newBuilder()
//                    .url(newUrl)
//                    .build()
//
//                chain.proceed(newRequest)
//            }
//            .build()
//    }
    private fun createOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                val newUrl = originalRequest.url.newBuilder()
                    .addQueryParameter("api_key", API_KEY)
                    .build()

                val newRequest = originalRequest.newBuilder()
                    .url(newUrl)
                    .build()

                chain.proceed(newRequest)
            }
            .build()
    }

}