package com.example.outdoor.models

data class DialogInfo(
    val title: Int,
    val notification: Int,
    val positiveText: Int,
    val displayNegativeButton: Boolean = false,
    val negativeText: Int = 0
)