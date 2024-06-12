package com.example.recipefinderapp.ai.state

import android.graphics.Bitmap
import com.example.recipefinderapp.ai.model.AiModel

data class AiState(
    val aiModelList: MutableList<AiModel> = mutableListOf(),
    val prompt: String = "",
    val bitmap: Bitmap? = null
)
