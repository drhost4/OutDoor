package com.example.outdoor.models

import com.example.outdoor.models.IncludedAttributes
import com.google.gson.annotations.SerializedName

data class IncludedData(
    @SerializedName("id") val includedId: String,
    @SerializedName("attributes") val includedAttributes: IncludedAttributes
)
