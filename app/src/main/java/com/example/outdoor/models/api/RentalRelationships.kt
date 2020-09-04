package com.example.outdoor.models.api

import com.google.gson.annotations.SerializedName

data class RentalRelationships(
    @SerializedName("primary_image") val primaryImage: PrimaryImageData
)
