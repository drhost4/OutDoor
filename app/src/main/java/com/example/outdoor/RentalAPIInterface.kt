package com.example.outdoor

import com.example.outdoor.models.RentalResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RentalAPIInterface {
    @GET("/rentals?")
    fun retriveRentals(
        @Query("filter[keywords]") filter: String,
        @Query("page[limit]") pageLimit: Int? = null,
        @Query("page[offset]") pageOffset: Int? = null
    ): Call<RentalResponse>
}

//http://search.outdoorsy.co/rentals?filter[keywords]=Trailer&page=4