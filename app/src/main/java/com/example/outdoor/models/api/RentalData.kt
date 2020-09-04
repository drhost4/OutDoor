package com.example.outdoor.models.api

import com.google.gson.annotations.SerializedName

data class RentalData(
    @SerializedName("attributes") val attributes: RentalAttributes,
    @SerializedName("relationships") val relationships: RentalRelationships
)
