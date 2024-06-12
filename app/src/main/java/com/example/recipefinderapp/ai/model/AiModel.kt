package com.example.recipefinderapp.ai.model

import android.graphics.Bitmap

data class AiModel(
    val prompt: String,
    val bitmap: Bitmap?,
    val isFromUser: Boolean
)
