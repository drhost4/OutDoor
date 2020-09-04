package com.example.outdoor.api

import com.example.outdoor.models.api.RentalResponse
import okhttp3.OkHttpClient
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RentalAPI {
    private val apiInterface: RentalAPIInterface

    companion object {
        const val BASE_URL = "http://search.outdoorsy.co"
    }

    init {
        val okHttpClient = OkHttpClient().newBuilder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiInterface = retrofit.create(RentalAPIInterface::class.java)
    }

    fun getRentals(searchString: String, callback: Callback<RentalResponse>) {
        val call = apiInterface.retriveRentals(searchString)
        call.enqueue(callback)
    }
}