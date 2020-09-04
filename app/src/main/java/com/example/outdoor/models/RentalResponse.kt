package com.example.outdoor.models

import com.google.gson.annotations.SerializedName

data class RentalResponse(
    @SerializedName("data") val rentalData: ArrayList<RentalData>,
    @SerializedName("included") val includedData: ArrayList<IncludedData>
)