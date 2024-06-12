package com.example.recipefinderapp.ai.data

import com.example.recipefinderapp.ai.model.AiModel
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object AiData {

    private val api_key = "AIzaSyCuI7YQJEZJznAd1gWu6hikrCA6tnu4Tlc"

    suspend fun getResponse(prompt: String): AiModel {
        val generativeModel = GenerativeModel(
            modelName = "gemini-pro", apiKey = api_key
        )

        try {
            val response = withContext(Dispatchers.IO) {
                generativeModel.generateContent(prompt)
            }

            return AiModel(
                prompt = response.text ?: "error",
                bitmap = null,
                isFromUser = false
            )

        } catch (e: Exception) {
            return AiModel(
                prompt = e.message ?: "error",
                bitmap = null,
                isFromUser = false
            )
        }

    }
}