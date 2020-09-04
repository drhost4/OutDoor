package com.example.outdoor.models

import com.example.outdoor.models.PrimaryImageData
import com.google.gson.annotations.SerializedName

data class RentalRelationships(
    @SerializedName("primary_image") val primaryImage: PrimaryImageData
)
