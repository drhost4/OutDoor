package com.example.outdoor.models.api

import com.google.gson.annotations.SerializedName

data class IncludedData(
    @SerializedName("id") val includedId: String,
    @SerializedName("attributes") val includedAttributes: IncludedAttributes
)
