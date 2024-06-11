package com.example.recipefinderapp.ai

import android.graphics.Bitmap

data class Ai(
    val prompt: String,
    val bitmap: Bitmap?,
    val isFromUser: Boolean
)
