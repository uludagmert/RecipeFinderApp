package com.example.recipefinderapp.ai.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipefinderapp.ai.model.AiModel
import com.example.recipefinderapp.ai.data.AiData
import com.example.recipefinderapp.ai.state.AiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class AiViewModel : ViewModel () {

    private val _chatState = MutableStateFlow(AiState())
    val chatState = _chatState.asStateFlow()
    val isLoading = MutableStateFlow(false)

    fun onEvent(event: ChatUiEvent) {
        when (event) {
            is ChatUiEvent.SendPrompt -> {
                if (event.prompt.isNotEmpty()) {
                    addPrompt(event.prompt, event.bitmap)
                    if (event.bitmap != null) {
                        getResponseWithImage(event.prompt, event.bitmap)
                    } else {
                        getResponse(event.prompt)
                    }
                }
            }

            is ChatUiEvent.UpdatePrompt -> {
                _chatState.update {
                    it.copy(
                        prompt = event.newPrompt
                    )
                }
            }

        }
    }

    private fun addPrompt(prompt: String, bitmap: Bitmap?) {
        _chatState.update {
            it.copy(
                aiModelList = it.aiModelList.toMutableList().apply {
                    add(0, AiModel(prompt, bitmap, isFromUser = true))
                },
                prompt = "",
                bitmap = null

            )
        }

    }

    private fun getResponse(prompt: String) {
        viewModelScope.launch {
            isLoading.value = true
            val aiModel = AiModel("", null, isFromUser = false)
            _chatState.update {
                it.copy(
                    aiModelList = it.aiModelList.toMutableList().apply {
                        add(0, aiModel)
                    }
                )
            }
            val response = AiData.getResponse(prompt)
            _chatState.update {
                it.copy(
                    aiModelList = it.aiModelList.toMutableList().apply {
                        this[0] = response
                    }
                )
            }
            isLoading.value = false
        }
    }

    private fun getResponseWithImage(prompt: String, bitmap: Bitmap) {
        viewModelScope.launch {
            isLoading.value = true
            val aiModel = AiModel("", null, isFromUser = false)
            _chatState.update {
                it.copy(
                    aiModelList = it.aiModelList.toMutableList().apply {
                        add(0, aiModel)
                    }
                )
            }

        }
    }

    fun clearMessages() {
        // Mesajları temizle
        _chatState.update { it.copy(aiModelList = emptyList<AiModel>().toMutableList()) }
    }
    sealed class ChatUiEvent {
        data class UpdatePrompt(val newPrompt: String) : ChatUiEvent()
        data class SendPrompt(
            val prompt: String,
            val bitmap: Bitmap?
        ) : ChatUiEvent()
    }
}