package com.example.outdoor.models

import com.example.outdoor.models.ImageData
import com.google.gson.annotations.SerializedName

data class PrimaryImageData(@SerializedName("data") val imageData: ImageData)