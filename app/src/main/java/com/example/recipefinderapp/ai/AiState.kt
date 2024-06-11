package com.example.recipefinderapp.ai

import android.graphics.Bitmap

data class AiState(
    val aiList: MutableList<Ai> = mutableListOf(),
    val prompt: String = "",
    val bitmap: Bitmap? = null
)
